package cc.zkteam.zkinfocollectpro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

public class NewResidentsInfoActivity extends BaseActivity {

    private static final int SCAN_REQUEST_CODE = 100;

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

    @BindView(R.id.card_button)
    Button cardButton;
    @BindView(R.id.zk_title_view)
    ZKTitleView zkTitleView;
    @BindView(R.id.sexedittext2)
    EditText sexedittext2;
    @BindView(R.id.bornedittext2)
    EditText bornedittext2;
    @BindView(R.id.card_button2)
    EditText cardButton2;
    @BindView(R.id.edittext23_2)
    EditText edittext232;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_residents_info;
    }

    @Override
    protected void initViews() {



        sexedittext.setVisibility(View.VISIBLE);
        bornedittext.setVisibility(View.VISIBLE);
        cardButton.setVisibility(View.VISIBLE);
        edittext23.setVisibility(View.VISIBLE);

        sexedittext2.setVisibility(View.GONE);
        bornedittext2.setVisibility(View.GONE);
        cardButton2.setVisibility(View.GONE);
        edittext232.setVisibility(View.GONE);

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.END);


        zkTitleView.setLeftIVSrc(R.drawable.icon_back);
        zkTitleView.setRightIVSrc(R.drawable.icon_saoyisao);

        zkTitleView.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        zkTitleView.centerTextTV.setText("新增住户信息填写");

        zkTitleView.rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("按钮点击");

                Intent intent = new Intent(NewResidentsInfoActivity.this, IDCardScanActivity.class);
                startActivityForResult(intent, SCAN_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sexedittext, R.id.card_button, R.id.bornedittext, R.id.edittext23, R.id.savecommit})
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (data != null && requestCode == SCAN_REQUEST_CODE) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        BDIdCardBean.WordsResultBean wordsResultBean = (BDIdCardBean.WordsResultBean) bundle.getSerializable(IDCardScanActivity.KEY_ID_CARD_INFO_BEAN);

                        if (wordsResultBean != null) {
                            String name = wordsResultBean.getName().getWords();
                            String sex = wordsResultBean.getSex().getWords();
                            String birthday = wordsResultBean.getBirthday().getWords();
                            String idCardNumber = wordsResultBean.getIdCardNumber().getWords();
                            String address = wordsResultBean.getAddress().getWords();
                            String nation = wordsResultBean.getNation().getWords();

                            L.i("扫描的姓名是；" + name);
                            // TODO: 2017/12/24 请处理这里的数据 ，并修改 UI


                            sexedittext.setVisibility(View.GONE);
                            bornedittext.setVisibility(View.GONE);
                            cardButton.setVisibility(View.GONE);
                            edittext23.setVisibility(View.GONE);

                            sexedittext2.setVisibility(View.VISIBLE);
                            bornedittext2.setVisibility(View.VISIBLE);
                            cardButton2.setVisibility(View.VISIBLE);
                            edittext232.setVisibility(View.VISIBLE);

                            cardButton2.setText("身份证");
                            edittext232.setText("房东与租客");
                            nameedittext.setText(name);
                            sexedittext2.setText(sex);
                            bornedittext2.setText(birthday);
                            nationaledittext.setText(nation);
                            edittext21.setText(idCardNumber);
                            edittext22.setText(address);

                            nameedittext.setFocusable(false);
                            nationaledittext.setFocusable(false);
                            edittext21.setFocusable(false);
                            edittext22.setFocusable(false);

                            nameedittext.setFocusableInTouchMode(false);
                            nationaledittext.setFocusableInTouchMode(false);
                            edittext21.setFocusableInTouchMode(false);
                            edittext22.setFocusableInTouchMode(false);
                        }
                    }
                }
                break;
        }

    }

}
