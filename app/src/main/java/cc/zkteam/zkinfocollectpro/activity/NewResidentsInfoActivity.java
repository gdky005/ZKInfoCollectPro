package cc.zkteam.zkinfocollectpro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.ZHAddhosePersonBean;
import cc.zkteam.zkinfocollectpro.dialog.OnZKDialogCancelListener;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragment;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.List2StringArrayUtils;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewResidentsInfoActivity extends BaseActivity {
    private static final String TAG = "NewResidentsInfoActivit";

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
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    private String address = "";
    private String b_id = "";
    private String h_id = "";
    private ZHApi zkApi;
    private String r_type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_residents_info;
    }

    @Override
    protected void initViews() {
        pbLoading.setVisibility(View.GONE);
        Intent intent = getIntent();
        address = intent.getStringExtra("address") != null ? intent.getStringExtra("address") : "";
        b_id = intent.getStringExtra("b_id") != null ? intent.getStringExtra("b_id") : "";
        h_id = intent.getStringExtra("h_id") != null ? intent.getStringExtra("h_id") : "";
        L.i("h_id；" + h_id + "b_id；" + b_id);
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showLong("没有识别到地址，请返回重试");
        }
        addressName.setText(address);
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
                picker2.setRangeEnd(2050, 12, 31);
                picker2.setRangeStart(1900, 1, 1);
                picker2.setSelectedItem(1900, 1, 1);
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
                        "身份证", "居住证", "护照", "港澳通行证", "台湾通行证", "残疾证", "军官证"
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
                        cardButton.setText(item);
                    }
                });
                picker4.show();
                break;

            case R.id.edittext23:
                LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

                    @Override
                    public boolean isOnlyTwo() {
                        return true;
                    }

                    @NonNull
                    @Override
                    public List<String> provideFirstData() {
                        ArrayList<String> firstList = new ArrayList<>();
                        firstList.add("邻居");
                        firstList.add("同事");
                        firstList.add("亲属");
                        firstList.add("家属");
                        firstList.add("房主");
                        return firstList;
                    }

                    @NonNull
                    @Override
                    public List<String> provideSecondData(int firstIndex) {
                        ArrayList<String> secondList = new ArrayList<>();

                        String[] relation_0 = getResources().getStringArray(R.array.relation_0);
                        String[] relation_1 = getResources().getStringArray(R.array.relation_1);
                        String[] relation_2 = getResources().getStringArray(R.array.relation_2);
                        String[] relation_3 = getResources().getStringArray(R.array.relation_3);
                        String[] relation_4 = getResources().getStringArray(R.array.relation_4);

                        List<String> relation0 = List2StringArrayUtils.string2List(relation_0);
                        List<String> relation1 = List2StringArrayUtils.string2List(relation_1);
                        List<String> relation2 = List2StringArrayUtils.string2List(relation_2);
                        List<String> relation3 = List2StringArrayUtils.string2List(relation_3);
                        List<String> relation4 = List2StringArrayUtils.string2List(relation_4);

                        if (firstIndex == 0) {
                            return relation0;
                        } else if (firstIndex == 1) {
                            return relation1;
                        } else if (firstIndex == 2) {
                            return relation2;
                        } else if (firstIndex == 3) {
                            return relation3;
                        } else {
                            return relation4;
                        }
                    }

                    @Nullable
                    @Override
                    public List<String> provideThirdData(int firstIndex, int secondIndex) {
                        return null;
                    }

                };
                LinkagePicker picker3 = new LinkagePicker(this, provider);
                picker3.setCycleDisable(true);
                picker3.setUseWeight(true);
                picker3.setLabel("关系", "关系");
                picker3.setSelectedIndex(0, 8);
                //picker.setSelectedItem("12", "9");
                picker3.setContentPadding(10, 0);
                picker3.setOnStringPickListener(new LinkagePicker.OnStringPickListener() {
                    @Override
                    public void onPicked(String first, String second, String third) {
                        if ("邻居".equals(first)) {
                            r_type = "1";
                        } else if ("同事".equals(first)) {
                            r_type = "2";
                        } else if ("亲属".equals(first)) {
                            r_type = "3";
                        } else if ("家属".equals(first)) {
                            r_type = "4";
                        } else if ("房主".equals(first)) {
                            r_type = "5";
                        }
                        edittext23.setText(second);
                    }
                });
                picker3.show();
                break;

            case R.id.savecommit:
                pbLoading.setVisibility(View.VISIBLE);
                //判断是扫码返回的页面还是手动填写的页面
                int flag = sexedittext.getVisibility();
                String name = nameedittext.getText().toString().trim();
                String sex = View.VISIBLE == flag ? (sexedittext.getText().toString().trim()) : (sexedittext.getText().toString().trim());
                String data = View.VISIBLE == flag ? (bornedittext.getText().toString().trim()) : (bornedittext2.getText().toString().trim());
                String nation = nationaledittext.getText().toString().trim();
                String cardtype = View.VISIBLE == flag ? (cardButton.getText().toString().trim()) : (cardButton2.getText().toString().trim());
                String cardid = edittext21.getText().toString().trim();
                String address = edittext22.getText().toString().trim();
                String relation = edittext23.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(data) && !TextUtils.isEmpty(nation) && !TextUtils.isEmpty(cardtype) && !TextUtils.isEmpty(cardid) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(relation)) {
                    try {
                        zkApi = ZHConnectionManager.getInstance().getZHApi();
                        zkApi.addhouseperson(
                                MultipartBody.Part.createFormData("b_id", b_id),
                                MultipartBody.Part.createFormData("h_id", h_id),
                                MultipartBody.Part.createFormData("r_type", r_type),
                                MultipartBody.Part.createFormData("relation", relation),
                                MultipartBody.Part.createFormData("name", name),
                                MultipartBody.Part.createFormData("id_card", cardid),
                                MultipartBody.Part.createFormData("sex", sex),
                                MultipartBody.Part.createFormData("birthday", data),
                                MultipartBody.Part.createFormData("nation", nation),
                                MultipartBody.Part.createFormData("id_card_type", cardtype),
                                MultipartBody.Part.createFormData("place_of_domicile", address)

                        ).enqueue(new Callback<ZHAddhosePersonBean>() {
                            @Override
                            public void onResponse(Call<ZHAddhosePersonBean> call, Response<ZHAddhosePersonBean> response) {
                                pbLoading.setVisibility(View.GONE);
                                L.d(TAG, "onResponse: " + response.body().toString());
                                int status = response.body().status;
                                String msg = response.body().msg;
                                ZHAddhosePersonBean.DataEntity data = response.body().data;

                                if (status == 2) {
                                    ZKDialogFragment dialogFragment = ZKDialogFragmentHelper.showDialog(getSupportFragmentManager(),
                                            "提交失败",
                                            msg,
                                            new ZKDialogResultListener<Integer>() {
                                                @Override
                                                public void onDataResult(Integer result) {

                                                    switch (result) {
                                                        case DialogInterface.BUTTON_POSITIVE: //确定
                                                            pbLoading.setVisibility(View.VISIBLE);
                                                            zkApi.addhousepersonconfirm(
                                                                    MultipartBody.Part.createFormData("p_id", data.p_id),
                                                                    MultipartBody.Part.createFormData("b_id", data.b_id),
                                                                    MultipartBody.Part.createFormData("h_id", data.h_id),
                                                                    MultipartBody.Part.createFormData("r_type", data.r_type),
                                                                    MultipartBody.Part.createFormData("relation", data.relation)

                                                            ).enqueue(new Callback<ZHAddhosePersonBean>() {
                                                                @Override
                                                                public void onResponse(Call<ZHAddhosePersonBean> call, Response<ZHAddhosePersonBean> response) {
                                                                    pbLoading.setVisibility(View.GONE);
                                                                    L.d(TAG, "onResponse: " + response.body().toString());
                                                                    int status = response.body().status;
                                                                    String msg = response.body().msg;
                                                                    if (status == 1) {
                                                                        ToastUtils.showLong(msg);
                                                                    } else {
                                                                        ToastUtils.showLong("数据提交失败，请重试");
                                                                    }


                                                                }

                                                                @Override
                                                                public void onFailure(Call<ZHAddhosePersonBean> call, Throwable throwable) {
                                                                    pbLoading.setVisibility(View.GONE);
                                                                    L.d(TAG, "onResponse:失败 ");
                                                                }
                                                            });

                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE: // 取消
                                                            break;
                                                    }
                                                }
                                            }, new OnZKDialogCancelListener() {
                                                @Override
                                                public void onCancel() {
                                                }
                                            });

                                } else if (status == 1) {
                                    ZKDialogFragment dialogFragment = ZKDialogFragmentHelper.showSingleBtnDialog(getSupportFragmentManager(),
                                            "提交成功",
                                            msg,
                                            new ZKDialogResultListener<Integer>() {
                                                @Override
                                                public void onDataResult(Integer result) {

                                                    switch (result) {
                                                        case DialogInterface.BUTTON_POSITIVE: //确定
                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE: // 取消
                                                            break;
                                                    }
                                                }
                                            }, new OnZKDialogCancelListener() {
                                                @Override
                                                public void onCancel() {
                                                }
                                            });
                                } else {
                                    //其他失败情况
                                    ZKDialogFragment dialogFragment = ZKDialogFragmentHelper.showSingleBtnDialog(getSupportFragmentManager(),
                                            "提交失败",
                                            msg,
                                            new ZKDialogResultListener<Integer>() {
                                                @Override
                                                public void onDataResult(Integer result) {

                                                    switch (result) {
                                                        case DialogInterface.BUTTON_POSITIVE: //确定
                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE: // 取消
                                                            break;
                                                    }
                                                }
                                            }, new OnZKDialogCancelListener() {
                                                @Override
                                                public void onCancel() {
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onFailure(Call<ZHAddhosePersonBean> call, Throwable throwable) {
                                pbLoading.setVisibility(View.GONE);
                                L.d(TAG, "onResponse:失败 ");

                            }
                        });
                    } catch (Exception e) {
                        pbLoading.setVisibility(View.GONE);
                        ToastUtils.showLong("提交参数异常，请重试");
                        e.printStackTrace();
                        return;
                    }

                } else {
                    pbLoading.setVisibility(View.GONE);
                    ToastUtils.showLong("以上都是必填项，请全部填写在提交");
                }
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


                            sexedittext.setVisibility(View.GONE);
                            bornedittext.setVisibility(View.GONE);
                            cardButton.setVisibility(View.GONE);

                            sexedittext2.setVisibility(View.VISIBLE);
                            bornedittext2.setVisibility(View.VISIBLE);
                            cardButton2.setVisibility(View.VISIBLE);

                            cardButton2.setText("身份证");
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
