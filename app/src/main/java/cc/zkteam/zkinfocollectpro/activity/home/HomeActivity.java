package cc.zkteam.zkinfocollectpro.activity.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.TestFragment;
import cc.zkteam.zkinfocollectpro.view.ZKViewPager;

/**
 * HomeActivity
 * Created by WangQing on 2017/12/15.
 */

public class HomeActivity extends BaseActivity {

    // 首页
    public static final int NAV_TYPE_MAIN = 0;
    // 数据采集
    public static final int NAV_TYPE_DATA_COLLECT = 1;
    // 问题上报
    public static final int NAV_TYPE_QUESTION_UPLOAD = 2;

    public static int [] NAV_TYPE = new int[]{NAV_TYPE_MAIN, NAV_TYPE_DATA_COLLECT, NAV_TYPE_QUESTION_UPLOAD};


    @BindView(R.id.container_view_pager)
    ZKViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;


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
                    break;
                case NAV_TYPE_DATA_COLLECT:
                    itemId = R.id.navigation_data_collect;
                    break;
                case NAV_TYPE_QUESTION_UPLOAD:
                    itemId = R.id.navigation_question_upload;
                    break;
            }

            navigation.setSelectedItemId(itemId);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    mViewPager.setCurrentItem(NAV_TYPE_MAIN);
                    return true;
                case R.id.navigation_data_collect:
                    mViewPager.setCurrentItem(NAV_TYPE_DATA_COLLECT);
                    return true;
                case R.id.navigation_question_upload:
                    mViewPager.setCurrentItem(NAV_TYPE_QUESTION_UPLOAD);
                    return true;
                default:
            }
            return false;
        }
    };



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

    }

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
                    return TestFragment.newInstance("One");
                case NAV_TYPE_DATA_COLLECT:
                    return TestFragment.newInstance("Two");
                case NAV_TYPE_QUESTION_UPLOAD:
                    return TestFragment.newInstance("Three");
            }

            return null;
        }

        @Override
        public int getCount() {
            return NAV_TYPE.length;
        }
    }
}
