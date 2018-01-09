package cc.zkteam.zkinfocollectpro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cc.zkteam.zkinfocollectpro.managers.ZHConfigDataManager;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.IdentityUtils;
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
    Button nationaledittext;

    @BindView(R.id.edittext21)
    EditText edittext21;
    @BindView(R.id.edittext22)
    EditText edittext22;
    @BindView(R.id.edittext23)
    Button edittext23;


    @BindView(R.id.savecommit)
    Button savecommit;


    @BindView(R.id.card_button)
    Button cardButton;
    @BindView(R.id.zk_title_view)
    ZKTitleView zkTitleView;

    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    private String address = "";
    private String b_id = "";
    private String h_id = "";
    private ZHApi zkApi;
    private String r_type = "1";

    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!IdentityUtils.isChinese(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

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
                Intent intent = new Intent(NewResidentsInfoActivity.this, IDCardScanActivity.class);
                startActivityForResult(intent, SCAN_REQUEST_CODE);
            }
        });

        setNationData();
    }

    /**
     * 设置民族的数据
     */
    private void setNationData() {
        List<String> nationList = ZHConfigDataManager.getInstance().getNation();
        if (nationList != null && nationList.size() > 0) {
            nationaledittext.setText(nationList.get(0));
        }

        nationaledittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = List2StringArrayUtils.list2StringArray(nationList);
                if (strings.length > 0) {
//                                修复bug
                    for (int i = 0; i < strings.length; i++) {
                        String tempStr = strings[i];

                        if (TextUtils.isEmpty(tempStr)) {
                            strings[i] = "";
                        }
                    }
                    OptionPicker picker3 = new OptionPicker(NewResidentsInfoActivity.this, strings);
                    picker3.setCanceledOnTouchOutside(false);
                    picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                    picker3.setShadowColor(Color.BLUE, 40);
                    picker3.setSelectedIndex(0);
                    picker3.setCycleDisable(true);
                    picker3.setTextSize(20);
                    picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index1, String item) {
                            nationaledittext.setText(item);
                        }
                    });
                    picker3.show();
                }
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

                        bornedittext.setText(year + "-" + month + "-" + day);
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
                        firstList.clear();
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
//                picker3.setSelectedIndex(0, 8);
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
                String name = nameedittext.getText().toString().trim();
                String sex = sexedittext.getText().toString().trim();
                String data = bornedittext.getText().toString().trim();
                String nation = nationaledittext.getText().toString().trim();
                String cardtype = cardButton.getText().toString().trim();
                String cardid = edittext21.getText().toString().trim();
                String address = edittext22.getText().toString().trim();
                String relation = edittext23.getText().toString().trim();
                if ("身份证".equals(cardtype)) {
                    boolean IDcardValid = IdentityUtils.checkIDCard(cardid);
                    if (!IDcardValid) {
                        ToastUtils.showLong("身份证格式有误，请核对后重试");
                        pbLoading.setVisibility(View.GONE);
                        break;
                    }
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(data) && !TextUtils.isEmpty(nation) && !TextUtils.isEmpty(cardtype) && !TextUtils.isEmpty(cardid) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(relation)) {
                    try {
                        L.i("request---"+"b_id--"+b_id+"h_id--"+h_id+"r_type--"+r_type+"relation--"+relation+"name--"+name+"cardid--"+cardid+"sex"+sex+"birthday"+data+"nation--"+nation+"id_card_type--"+cardtype+"place_of_domicile--"+address);
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
                                                                        NewResidentsInfoActivity.this.finish();
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
                                                            NewResidentsInfoActivity.this.finish();
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
                            BDIdCardBean.NameBean nameBean = wordsResultBean.getName();
                            BDIdCardBean.SexBean sexBean = wordsResultBean.getSex();
                            BDIdCardBean.BirthdayBean birthdayBean = wordsResultBean.getBirthday();
                            BDIdCardBean.IdCardNumberBean idCardNumberBean = wordsResultBean.getIdCardNumber();
                            BDIdCardBean.AddressBean addressBean = wordsResultBean.getAddress();
                            BDIdCardBean.NationBean nationBean = wordsResultBean.getNation();


                            String name = null;
                            String sex = null;
                            String birthday = null;
                            String idCardNumber = null;
                            String address = null;
                            String nation = null;

                            if (nameBean != null) {
                                name = nameBean.getWords();
                            }

                            if (sexBean != null) {
                                sex = sexBean.getWords();
                            }

                            if (birthdayBean != null) {
                                birthday = birthdayBean.getWords();
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                                SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    birthday = sdf2.format(sdf.parse(birthday));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                L.i("扫描的姓名是；" + birthday);
                            }

                            if (idCardNumberBean != null) {
                                idCardNumber = idCardNumberBean.getWords();
                            }

                            if (addressBean != null) {
                                address = addressBean.getWords();
                            }

                            if (nationBean != null) {
                                nation = nationBean.getWords();
                            }

                            L.i("扫描的姓名是；" + name);

                            cardButton.setText("身份证");
                            nameedittext.setText(name);
                            sexedittext.setText(sex);
                            bornedittext.setText(birthday);
                            nationaledittext.setText(nation);
                            edittext21.setText(idCardNumber);
                            edittext22.setText(address);

                        }
                    }
                }
                break;
        }

    }

}
