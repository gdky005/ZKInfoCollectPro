package cc.zkteam.zkinfocollectpro.activity.home;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MyBean;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.SignInFragment;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.DataCollectFragment;
import cc.zkteam.zkinfocollectpro.fragment.problem.ProblemReportFragment;
import cc.zkteam.zkinfocollectpro.localserver.JMHttpServer;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.ZKViewPager;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import okhttp3.OkHttpClient;

/**
 * HomeActivity
 * Created by WangQing on 2017/12/15.
 */

public class HomeActivity extends BaseActivity implements HasSupportFragmentInjector {

    private static final String TAG = "HomeActivity";

    // 首页
    public static final int NAV_TYPE_MAIN = 0;
    // 数据采集
    public static final int NAV_TYPE_DATA_COLLECT = 1;
    // 问题上报
    public static final int NAV_TYPE_QUESTION_UPLOAD = 2;

    /**
     * 如果是主页面并且打开了设置界面，则处理返回键点击时需要隐藏该设置界面
     */
    private boolean isNeedHandleBack = true;

    /**
     * 首页三个 table 页面对应的 Fragment.
     */
    private static Fragment[] TABLE_FRAGMENT = new Fragment[]{
            SignInFragment.newInstance(),
            DataCollectFragment.newInstance(),
            ProblemReportFragment.newInstance(true)};

    public static int[] NAV_TYPE = new int[]{NAV_TYPE_MAIN, NAV_TYPE_DATA_COLLECT, NAV_TYPE_QUESTION_UPLOAD};


    /**
     * 添加 app 需要的动态权限
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA};

    @BindView(R.id.container_view_pager)
    ZKViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int itemId = R.id.navigation_main;
            switch (position) {
                case NAV_TYPE_MAIN:
                    itemId = R.id.navigation_main;
                    isNeedHandleBack = true;
                    break;
                case NAV_TYPE_DATA_COLLECT:
                    itemId = R.id.navigation_data_collect;
                    isNeedHandleBack = false;
                    break;
                case NAV_TYPE_QUESTION_UPLOAD:
                    isNeedHandleBack = false;
                    itemId = R.id.navigation_question_upload;
                    break;
            }

            navigation.setSelectedItemId(itemId);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SignInFragment signInFragment = (SignInFragment) TABLE_FRAGMENT[0];
            if (isNeedHandleBack && signInFragment != null && signInFragment.isSetIsShow()) {
                signInFragment.hideSetPage();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    mViewPager.setCurrentItem(NAV_TYPE_MAIN);
                    isNeedHandleBack = true;
                    return true;
                case R.id.navigation_data_collect:
                    mViewPager.setCurrentItem(NAV_TYPE_DATA_COLLECT);
                    isNeedHandleBack = false;
                    return true;
                case R.id.navigation_question_upload:
                    mViewPager.setCurrentItem(NAV_TYPE_QUESTION_UPLOAD);
                    isNeedHandleBack = false;
                    return true;
                default:
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        mViewPager.setLifecycle(getLifecycle());
    }

    @Override
    protected void initListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager.setViewPager(onPageChangeListener, new SectionsPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initData() {
//        testDI();
        verifyStoragePermissions(this);

        JMHttpServer.getInstance().startServer(this, 8888, new File(SDCardUtils.getSDCardPaths(false).get(0)));

        String address = JMHttpServer.getInstance().getLocalHostUrl(this);

        L.d("当前的本地服务器路径是：" + address);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMHttpServer.getInstance().stopServer();
    }

    /**
     * ********************************测试 Dagger2 的代码 start**********************************************************
     */
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    MyBean myBean;

    private void testDI() {
        Log.d(TAG, "initData: " + okHttpClient.toString());
        Log.d(TAG, "initData: " + myBean.getName());
        Log.d(TAG, "initData: " + myBean.getAge());
        myBean.setName("QQ");
        myBean.setAge("555");
        Log.d(TAG, "initData: " + myBean.getName());
        Log.d(TAG, "initData: " + myBean.getAge());
    }
    /**
     ************************************** end *********************************************************************************
     */

    /**
     * MainActivity 中的四大底标签页面
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 首页
        public static final int NAV_TYPE_MAIN = 0;
        // 数据采集
        public static final int NAV_TYPE_DATA_COLLECT = 1;
        // 问题上报
        public static final int NAV_TYPE_QUESTION_UPLOAD = 2;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case NAV_TYPE_MAIN:
                    return TABLE_FRAGMENT[0];
                case NAV_TYPE_DATA_COLLECT:
                    return TABLE_FRAGMENT[1];
                case NAV_TYPE_QUESTION_UPLOAD:
                    return TABLE_FRAGMENT[2];
            }

            return null;
        }

        @Override
        public int getCount() {
            return NAV_TYPE.length;
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }


    public void verifyStoragePermissions(Activity activity) {
        PermissionUtils.requestPermissions(activity, 200, PERMISSIONS_STORAGE, new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                ToastUtils.showShort("您禁用了以下权限，app 可能无法正常运行：\n" + deniedPermissions.toString());

            }
        });
    }

}
