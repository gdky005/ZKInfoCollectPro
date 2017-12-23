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

public class NewResidentsInfoActivity extends BaseActivity {


    @BindView(R.id.nameedittext)
    EditText nameedittext;
    @BindView(R.id.sexedittext)
    Button sexedittext;
    @BindView(R.id.bornedittext)
    Button bornedittext;
    @BindView(R.id.nationaledittext)
    EditText nationaledittext;

    @BindView(R.id.edittext21)
    EditText edittext21;
    @BindView(R.id.edittext22)
    EditText edittext22;
    @BindView(R.id.edittext23)
    Button edittext23;


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
    @BindView(R.id.card_button)
    Button cardButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_residents_info;
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

    @OnClick({R.id.sexedittext,R.id.card_button, R.id.bornedittext, R.id.edittext23, R.id.savecommit, R.id.right_icon})
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
            case R.id.card_button:
                OptionPicker picker4 = new OptionPicker(this, new String[]{
                        "身份证", "居住证", "护照", "港澳通行证", "台湾通行证"
                });
                picker4.setCanceledOnTouchOutside(false);
                picker4.setDividerRatio(WheelView.DividerConfig.FILL);
                picker4.setShadowColor(Color.BLUE, 40);
                picker4.setSelectedIndex(1);
                picker4.setCycleDisable(true);
                picker4.setTextSize(20);
                picker4.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        edittext23.setText(item);
                    }
                });
                picker4.show();
                break;

            case R.id.edittext23:
                OptionPicker picker3 = new OptionPicker(this, new String[]{
                        "房东与租客", "房东与租客", "房东与租客"
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

            case R.id.savecommit:
                ToastUtils.showShort("信息保存成功");
                break;
            case R.id.right_icon:
//                drawerLayout.openDrawer(Gravity.END);
                ToastUtils.showLong("按钮点击");
                break;
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
