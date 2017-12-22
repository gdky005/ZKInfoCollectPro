package cc.zkteam.zkinfocollectpro.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

public class BasicInfoActivity extends BaseActivity {


    @BindView(R.id.nameedittext)
    EditText nameedittext;
    @BindView(R.id.sexedittext)
    Button sexedittext;
    @BindView(R.id.bornedittext)
    Button bornedittext;
    @BindView(R.id.nationaledittext)
    EditText nationaledittext;
    @BindView(R.id.cardedittext)
    EditText cardedittext;
    @BindView(R.id.edittext21)
    EditText edittext21;
    @BindView(R.id.edittext22)
    EditText edittext22;
    @BindView(R.id.edittext23)
    Button edittext23;
    @BindView(R.id.edittext24)
    EditText edittext24;
    @BindView(R.id.edittext25)
    EditText edittext25;
    @BindView(R.id.edittext26)
    Button edittext26;
    @BindView(R.id.savecommit)
    Button savecommit;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.right_icon)
    ImageView rightIcon;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info;
    }

    @Override
    protected void initViews() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.END);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        titleName.setText("基本信息填写");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sexedittext, R.id.bornedittext, R.id.edittext23, R.id.edittext26, R.id.savecommit, R.id.right_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sexedittext:

                OptionPicker picker = new OptionPicker(this, new String[]{
                        "男", "女"
                });
                picker.setCanceledOnTouchOutside(false);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
                picker.setShadowColor(Color.BLUE, 40);
                picker.setSelectedIndex(1);
                picker.setCycleDisable(true);
                picker.setTextSize(20);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        sexedittext.setText(item);
                    }
                });
                picker.show();
                break;
            case R.id.bornedittext:

                final DatePicker picker2 = new DatePicker(this);
                picker2.setCanceledOnTouchOutside(true);
                picker2.setUseWeight(true);
                picker2.setTopPadding(ConvertUtils.toPx(this, 10));
                picker2.setRangeEnd(2111, 1, 11);
                picker2.setRangeStart(2016, 8, 29);
                picker2.setSelectedItem(2050, 10, 14);
                picker2.setContentPadding(15, 0);

                picker2.setResetWhileWheel(false);
                picker2.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {

                        bornedittext.setText(year + "年" + month + "月" + day + "日");
                    }
                });
                picker2.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker2.setTitleText(year + "-" + picker2.getSelectedMonth() + "-" + picker2.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker2.setTitleText(picker2.getSelectedYear() + "-" + month + "-" + picker2.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker2.setTitleText(picker2.getSelectedYear() + "-" + picker2.getSelectedMonth() + "-" + day);
                    }
                });
                picker2.show();
                break;
            case R.id.edittext23:
                OptionPicker picker3 = new OptionPicker(this, new String[]{
                        "第一单位", "第二单位", "第三单位"
                });
                picker3.setCanceledOnTouchOutside(false);
                picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                picker3.setShadowColor(Color.BLUE, 40);
                picker3.setSelectedIndex(1);
                picker3.setCycleDisable(true);
                picker3.setTextSize(20);
                picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        edittext23.setText(item);
                    }
                });
                picker3.show();
                break;
            case R.id.edittext26:

                final DatePicker picker4 = new DatePicker(this);
                picker4.setCanceledOnTouchOutside(true);
                picker4.setUseWeight(true);
                picker4.setTopPadding(ConvertUtils.toPx(this, 10));
                picker4.setRangeEnd(2111, 1, 11);
                picker4.setRangeStart(2016, 8, 29);
                picker4.setSelectedItem(2050, 10, 14);
                picker4.setResetWhileWheel(false);
                picker4.setContentPadding(15, 0);
                picker4.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {

                        edittext26.setText(year + "年" + month + "月" + day + "日");
                    }
                });
                picker4.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker4.setTitleText(year + "-" + picker4.getSelectedMonth() + "-" + picker4.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker4.setTitleText(picker4.getSelectedYear() + "-" + month + "-" + picker4.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker4.setTitleText(picker4.getSelectedYear() + "-" + picker4.getSelectedMonth() + "-" + day);
                    }
                });
                picker4.show();
                break;
            case R.id.savecommit:
                ToastUtils.showShort("信息保存成功");
                break;
            case R.id.right_icon:
                drawerLayout.openDrawer(Gravity.END);
                break;
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
