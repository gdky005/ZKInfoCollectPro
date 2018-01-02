package cc.zkteam.zkinfocollectpro.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.eventbusbean.BasicItemClick;
import cc.zkteam.zkinfocollectpro.fragment.New31InfoFragment;
import cc.zkteam.zkinfocollectpro.view.ViewpagerSpeedScroller;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;

public class BasicInfoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.title)
    ZKTitleView title;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Subscribe
    public void OnitemClick(BasicItemClick basicItemClick) {
        drawerLayout.closeDrawer(Gravity.END);
        viewpager.setCurrentItem(basicItemClick.getI());
        ToastUtils.showShort("-"+basicItemClick.getI());

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basicnfo;
    }

    @Override
    protected void initViews() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.END);
        title.setLeftIVSrc(R.drawable.icon_back);
        title.setRightIVSrc(R.mipmap.right_menu);

    }

    @Override
    protected void initListener() {
        title.rightIV.setOnClickListener(view -> drawerLayout.openDrawer(Gravity.END));
        title.leftIV.setOnClickListener(view -> finish());

    }

    public void setTitle(String titleName) {
        title.setCenterTVText(titleName);
    }

    @Override
    protected void initData() {

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new New31InfoFragment());
//        fragments.add(new BasicinfoFragment2());
//        fragments.add(new BasicinfoFragment2());
//        fragments.add(new BasicinfoFragment2());
//        fragments.add(new BasicinfoFragment29());
//        fragments.add(new BasicinfoFragment30());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment31());
//        fragments.add(new BasicinfoFragment29());
//        fragments.add(new BasicinfoFragment30());
//        fragments.add(new BasicinfoFragment31());

        viewpager.setOffscreenPageLimit(1);
        setViewPagerScrollSpeed();
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


    }

    //这里的方法是去掉了viewpager的切换动画（时间设置为0）但是目前不太清楚切换跨度非常大时的效果
    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewpagerSpeedScroller scroller = new ViewpagerSpeedScroller( viewpager.getContext( ) );
            mScroller.set( viewpager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sexedittext:
                break;
        }

//    @OnClick({R.id.sexedittext, R.id.bornedittext, R.id.edittext23, R.id.edittext26, R.id.savecommit, R.id.right_icon})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.sexedittext:
//
//                OptionPicker picker = new OptionPicker(this, new String[]{
//                        "男", "女"
//                });
//                picker.setCanceledOnTouchOutside(false);
//                picker.setDividerRatio(WheelView.DividerConfig.FILL);
//                picker.setShadowColor(Color.BLUE, 40);
//                picker.setSelectedIndex(1);
//                picker.setCycleDisable(true);
//                picker.setTextSize(20);
//                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//                    @Override
//                    public void onOptionPicked(int index, String item) {
//                        sexedittext.setText(item);
//                    }
//                });
//                picker.show();
//                break;
//            case R.id.bornedittext:
//
//                final DatePicker picker2 = new DatePicker(this);
//                picker2.setCanceledOnTouchOutside(true);
//                picker2.setUseWeight(true);
//                picker2.setTopPadding(ConvertUtils.toPx(this, 10));
//                picker2.setRangeEnd(2111, 1, 11);
//                picker2.setRangeStart(2016, 8, 29);
//                picker2.setSelectedItem(2050, 10, 14);
//                picker2.setContentPadding(15, 0);
//
//                picker2.setResetWhileWheel(false);
//                picker2.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
//                    @Override
//                    public void onDatePicked(String year, String month, String day) {
//
//                        bornedittext.setText(year + "年" + month + "月" + day + "日");
//                    }
//                });
//                picker2.setOnWheelListener(new DatePicker.OnWheelListener() {
//                    @Override
//                    public void onYearWheeled(int index, String year) {
//                        picker2.setTitleText(year + "-" + picker2.getSelectedMonth() + "-" + picker2.getSelectedDay());
//                    }
//
//                    @Override
//                    public void onMonthWheeled(int index, String month) {
//                        picker2.setTitleText(picker2.getSelectedYear() + "-" + month + "-" + picker2.getSelectedDay());
//                    }
//
//                    @Override
//                    public void onDayWheeled(int index, String day) {
//                        picker2.setTitleText(picker2.getSelectedYear() + "-" + picker2.getSelectedMonth() + "-" + day);
//                    }
//                });
//                picker2.show();
//                break;
//            case R.id.edittext23:
//                OptionPicker picker3 = new OptionPicker(this, new String[]{
//                        "第一单位", "第二单位", "第三单位"
//                });
//                picker3.setCanceledOnTouchOutside(false);
//                picker3.setDividerRatio(WheelView.DividerConfig.FILL);
//                picker3.setShadowColor(Color.BLUE, 40);
//                picker3.setSelectedIndex(1);
//                picker3.setCycleDisable(true);
//                picker3.setTextSize(20);
//                picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//                    @Override
//                    public void onOptionPicked(int index, String item) {
//                        edittext23.setText(item);
//                    }
//                });
//                picker3.show();
//                break;
//            case R.id.edittext26:
//
//                final DatePicker picker4 = new DatePicker(this);
//                picker4.setCanceledOnTouchOutside(true);
//                picker4.setUseWeight(true);
//                picker4.setTopPadding(ConvertUtils.toPx(this, 10));
//                picker4.setRangeEnd(2111, 1, 11);
//                picker4.setRangeStart(2016, 8, 29);
//                picker4.setSelectedItem(2050, 10, 14);
//                picker4.setResetWhileWheel(false);
//                picker4.setContentPadding(15, 0);
//                picker4.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
//                    @Override
//                    public void onDatePicked(String year, String month, String day) {
//
//                        edittext26.setText(year + "年" + month + "月" + day + "日");
//                    }
//                });
//                picker4.setOnWheelListener(new DatePicker.OnWheelListener() {
//                    @Override
//                    public void onYearWheeled(int index, String year) {
//                        picker4.setTitleText(year + "-" + picker4.getSelectedMonth() + "-" + picker4.getSelectedDay());
//                    }
//
//                    @Override
//                    public void onMonthWheeled(int index, String month) {
//                        picker4.setTitleText(picker4.getSelectedYear() + "-" + month + "-" + picker4.getSelectedDay());
//                    }
//
//                    @Override
//                    public void onDayWheeled(int index, String day) {
//                        picker4.setTitleText(picker4.getSelectedYear() + "-" + picker4.getSelectedMonth() + "-" + day);
//                    }
//                });
//                picker4.show();
//                break;
//            case R.id.savecommit:
//                ToastUtils.showShort("信息保存成功");
//                break;
//            case R.id.right_icon:
//                drawerLayout.openDrawer(Gravity.END);
//                break;
//        }
//    }
    }
}
