package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.bean.ZK31Bean;
import cc.zkteam.zkinfocollectpro.bean.CheckIdCardBean;
import cc.zkteam.zkinfocollectpro.fragment.New31ImageEvent;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZKFiledView
 * Created by wangqing on 2017/12/26.
 */
public class ZKFiled extends ZKBaseView implements IZKResult {

    public static final String ZK_FILED_DATA_BEAN = "zk_filed_data_bean";
    public static final String ZK_FILED_TABLE_NAME = "zk_filed_table_name";
    public static final String ZK_FILED_NEW_VALUE = "zk_filed_new_value";

    private TextView keyFiled;
    private TextView numberFiled;

    private EditText rightLayoutLeftEt;
    private TextView rightLayoutTimeFiledValue;
    private TextView rightLayoutSelectDataFiledValue;
    private TextView rightLayoutTwoTimeLeftTv;
    private TextView rightLayoutTwoTimeRightTv;
    private EditText rightLayoutLeftIdCardNumberEt;

    @FiledFormType
    protected int type;
    protected int index;
    protected String number;
    protected String key;
    public Object defaultValue;

    protected int year;
    protected int month;
    protected int day;
    protected String yearStr;
    protected String monthStr;
    protected String dayStr;
    private ZK31Bean.DataBeanX.DataBean dataBean;

    /**
     * 选择项 的数据 map 数据
     */
    private Map<String, String> selectMap = new HashMap<>();

    public ZKFiled(Context context) {
        super(context);
    }

    public ZKFiled(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKFiled(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.kind_layout_edit_field_2;
    }

    @Override
    protected void initViews(View view) {
        keyFiled = findView(R.id.text_filed_key);
        numberFiled = findView(R.id.text_filed_number);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        yearStr = String.valueOf(year);
        monthStr = String.valueOf(month);
        dayStr = String.valueOf(day);
    }


    // ———————————简易设置数据使用方法———————————
    public void setEditText(String key) {
        setEditText(null, key);
    }

    public void setEditText(String number, String key) {
        setData(number, key, "", 0);
    }

    public void setTime(String key) {
        setTime(null, key);
    }

    public void setTime(String number, String key) {
        setData(number, key, null, 0, ZKFiled.TYPE_FILED_FORM_TIME);
    }

    public void setSelectData(String key, String[] strings) {
        setSelectData(null, key, strings);
    }

    public void setSelectData(String number, String key, String[] strings) {
        setData(number, key, strings, 0, ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
    }

    public void setImage(String key, String picPath) {
        setImage(null, key, picPath);
    }

    public void setImage(String number, String key, String picPath) {
        setData(number, key, picPath, 0, ZKFiled.TYPE_FILED_FORM_IMAGE);
    }

    public void setDoubleBtn(String key) {
        setDoubleBtn(null, key);
    }

    public void setDoubleBtn(String number, String key) {
        setData(number, key, null, 0, ZKFiled.TYPE_FILED_FORM_DOUBLE_BUTTON);
    }

    public void setDoubleTime(String key) {
        setDoubleTime(null, key);
    }

    public void setDoubleTime(String number, String key) {
        setData(number, key, null, 0, ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON);
    }

    public void setIdCard(String key, String[] strings) {
        setIdCard(null, key, strings);
    }

    public void setIdCard(String number, String key, String[] strings) {
        setData(number, key, strings, 0, ZKFiled.TYPE_FILED_FORM_ID_CARD);
    }

    public void setIdCardNumber(String key) {
        setIdCardNumber(null, key);
    }

    public void setIdCardNumber(String number, String key) {
        setData(number, key, null, 0, ZKFiled.TYPE_FILED_FORM_ID_CARD_NUMBER);
    }


    // ———————————最原始设置数据方法———————————
    public void setData(String number, String key, String defaultValue, int index) {
        setData(number, key, defaultValue, index, TYPE_FILED_FORM_EDIT_TEXT);
    }

    public void setData(String number, String key, Object defaultValue, int index, @FiledFormType int filedFormType) {
        this.number = number;
        this.key = key;
        this.defaultValue = defaultValue;
        this.index = index;
        this.type = filedFormType;
        updateView(number, key, defaultValue, index, type);
    }

    public void setKeyFiledTextLength(int length) {
        keyFiled.setEms(length);
    }

    public void setZkFiledDataBean(ZK31Bean.DataBeanX.DataBean dataBean) {
        this.dataBean = dataBean;
    }


    // ———————————更新 View———————————
    private void updateView(String number, String key, Object defaultValue, int index, @FiledFormType int filedFormType) {
        setViewText(numberFiled, number);
        setViewText(keyFiled, key);

        switch (filedFormType) {
            case TYPE_FILED_FORM_EDIT_TEXT:
                View rightLayoutLeftEtLayout = ((ViewStub) findViewById(R.id.right_layout_left_et_layout)).inflate();
                rightLayoutLeftEt = rightLayoutLeftEtLayout.findViewById(R.id.right_layout_left_et);
                if (defaultValue instanceof String)
                    rightLayoutLeftEt.setText((CharSequence) defaultValue);

                break;
            case TYPE_FILED_FORM_TIME:
                View rightLayoutTimeFiledValueLayout = ((ViewStub) findViewById(R.id.right_layout_time_filed_value_layout)).inflate();
                rightLayoutTimeFiledValue = rightLayoutTimeFiledValueLayout.findViewById(R.id.right_layout_time_filed_value);
                rightLayoutTimeFiledValue.setOnClickListener(this::showTimeDialog);
                setCurrentTime(rightLayoutTimeFiledValue);

                break;
            case TYPE_FILED_FORM_SELECT_DATA:
                if (defaultValue instanceof String[]) {
                    String[] value = (String[]) defaultValue;
                    String defualtX = value[0];

                    String[] newValueData = new String[value.length - 1];

                    for (int i = 0; i < value.length; i++) {
                        if (i == 0)
                            continue;
                        newValueData[i - 1] = value[i];
                    }

                    value = newValueData;

                    if (value.length > 0) {
                        String[] newValue = new String[value.length];

                        // TODO: 2018/1/2  不是我想要写这么多，服务器说很麻烦，非要这么弄，那行吧，就是费事一点的啦
                        for (int i = 0; i < value.length; i++) {

                            String str = value[i];

                            if (!TextUtils.isEmpty(str)) {
                                String[] singleItem = str.split("\\|");
                                if (singleItem.length > 0) {
                                    try {
                                        String item = singleItem[0];
                                        selectMap.put(item, singleItem[1]);
                                        newValue[i] = item;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        View rightLayoutSelectDataFiledValueLayout = ((ViewStub) findViewById(R.id.right_layout_select_data_filed_value_layout)).inflate();
                        rightLayoutSelectDataFiledValue = rightLayoutSelectDataFiledValueLayout.findViewById(R.id.right_layout_select_data_filed_value);
                        rightLayoutSelectDataFiledValue.setOnClickListener(v1 -> {
                            TextView btn = (TextView) v1;
                            if (newValue.length > 0) {

//                                修复bug
                                for (int i = 0; i < newValue.length; i++) {
                                    String v = newValue[i];

                                    if (TextUtils.isEmpty(v)) {
                                        newValue[i] = "";
                                    }
                                }
                                OptionPicker picker3 = new OptionPicker(activity, newValue);
                                picker3.setCanceledOnTouchOutside(false);
                                picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                                picker3.setShadowColor(Color.BLUE, 40);
                                picker3.setSelectedIndex(0);
                                picker3.setCycleDisable(true);
                                picker3.setTextSize(20);
                                picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                                    @Override
                                    public void onOptionPicked(int index1, String item) {
                                        btn.setText(item);
                                    }
                                });
                                picker3.show();
                            }

                        });

                        if (TextUtils.isEmpty(defualtX)) {
                            defualtX = newValue[0];
                        }

                        rightLayoutSelectDataFiledValue.setText(defualtX);
                    }
                }
                break;

            case TYPE_FILED_FORM_IMAGE:
                View rightLayoutImageViewLayout = ((ViewStub) findViewById(R.id.right_layout_image_view_layout)).inflate();
                ImageView rightLayoutImageView = rightLayoutImageViewLayout.findViewById(R.id.right_layout_image_view);

                ZKFiled zkFiled = this;
                rightLayoutImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        New31ImageEvent imageEvent = new New31ImageEvent();
                        imageEvent.zkFiled = zkFiled;
                        imageEvent.type = TYPE_FILED_FORM_IMAGE;

                        EventBus.getDefault().post(imageEvent);
                    }
                });
                if (defaultValue instanceof String) {
                    String path = (String) defaultValue;
                    rightLayoutImageView.setImageURI(Uri.parse(path));
                }
                break;

            case TYPE_FILED_FORM_DOUBLE_BUTTON:
                View rightLayoutBtnLayout = ((ViewStub) findViewById(R.id.right_layout_btn_layout)).inflate();
                LinearLayout rightLayoutBtnLl = rightLayoutBtnLayout.findViewById(R.id.right_layout_btn_ll);

                rightLayoutBtnLl.findViewById(R.id.right_layout_btn_edit).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("你点击的编辑按钮");
                    }
                });

                rightLayoutBtnLl.findViewById(R.id.right_layout_btn_delete).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("你点击的删除按钮");
                    }
                });

                break;

            case TYPE_FILED_FORM_TWO_TIME_BUTTON:
                View rightLayoutTwoTimeLlLayout = ((ViewStub) findViewById(R.id.right_layout_two_time_ll_layout)).inflate();
                RelativeLayout rightLayoutTwoTimeLl = rightLayoutTwoTimeLlLayout.findViewById(R.id.right_layout_two_time_ll);

                rightLayoutTwoTimeLeftTv = rightLayoutTwoTimeLl.findViewById(R.id.right_layout_two_time_left_tv);
                rightLayoutTwoTimeLeftTv.setOnClickListener(this::showTimeDialog);
                setCurrentTime(rightLayoutTwoTimeLeftTv);

                rightLayoutTwoTimeRightTv = rightLayoutTwoTimeLl.findViewById(R.id.right_layout_two_time_right_tv);
                rightLayoutTwoTimeRightTv.setOnClickListener(this::showTimeDialog);
                setCurrentTime(rightLayoutTwoTimeRightTv);

                break;
            case TYPE_FILED_FORM_ID_CARD:
                View rightLayoutIdCardLayout = ((ViewStub) findViewById(R.id.right_layout_id_card_layout)).inflate();
                LinearLayout rightLayoutIdCard = rightLayoutIdCardLayout.findViewById(R.id.right_layout_id_card);
                ImageView rightLayoutIdCardLeft = rightLayoutIdCard.findViewById(R.id.right_layout_id_card_left);
                ImageView rightLayoutIdCardRight = rightLayoutIdCard.findViewById(R.id.right_layout_id_card_right);

                ZKFiled zkFiledIdCard = this;

                rightLayoutIdCardLeft.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        New31ImageEvent imageEvent = new New31ImageEvent();
                        imageEvent.zkFiled = zkFiledIdCard;
                        imageEvent.type = TYPE_FILED_FORM_ID_CARD;

                        imageEvent.isIdcardLeft = true;

                        EventBus.getDefault().post(imageEvent);
                    }
                });

                rightLayoutIdCardRight.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        New31ImageEvent imageEvent = new New31ImageEvent();
                        imageEvent.zkFiled = zkFiledIdCard;
                        imageEvent.type = TYPE_FILED_FORM_ID_CARD;

                        imageEvent.isIdcardLeft = false;

                        EventBus.getDefault().post(imageEvent);
                    }
                });

                if (defaultValue instanceof String[]) {
                    String[] value = (String[]) defaultValue;
                    if (value.length > 0) {
                        rightLayoutIdCardLeft.setImageURI(Uri.parse(value[0]));
                        rightLayoutIdCardRight.setImageURI(Uri.parse(value[1]));
                    } else {
                        // TODO: 2018/1/2  默认值图片设置
                    }
                }

                break;
            case TYPE_FILED_FORM_ID_CARD_NUMBER:
                View rightLayoutIdCardNumberLayout = ((ViewStub) findViewById(R.id.right_layout_id_card_number_layout)).inflate();
                RelativeLayout rightLayoutIdCardNumber = rightLayoutIdCardNumberLayout.findViewById(R.id.right_layout_id_card_number);
                rightLayoutLeftIdCardNumberEt = rightLayoutIdCardNumber.findViewById(R.id.right_layout_left_et);
                TextView searchBtn = rightLayoutIdCardNumber.findViewById(R.id.right_layout_id_card_serial_number_right_search_btn);
                searchBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idCardStr = rightLayoutLeftIdCardNumberEt.getText().toString();

                        if (!TextUtils.isEmpty(idCardStr)) {
                            ToastUtils.showShort("正在查询，请稍后");
                            ZHConnectionManager.getInstance().getZHApi().checkIdCard(idCardStr).enqueue(new Callback<CheckIdCardBean>() {
                                @Override
                                public void onResponse(Call<CheckIdCardBean> call, Response<CheckIdCardBean> response) {

                                    CheckIdCardBean checkIdCardBean = response.body();
                                    if (checkIdCardBean != null) {
                                        String msg = checkIdCardBean.getMsg();

                                        if (checkIdCardBean.isResult()) {
                                            if (rightLayoutLeftIdCardNumberEt != null) {
                                                rightLayoutLeftIdCardNumberEt.setFocusable(true);
                                                rightLayoutLeftIdCardNumberEt.setFocusableInTouchMode(true);
                                                rightLayoutLeftIdCardNumberEt.requestFocus();
                                            }

                                        }

                                        ToastUtils.showShort(msg);
                                        return;
                                    }

                                    ToastUtils.showShort("请重试");
                                }

                                @Override
                                public void onFailure(Call<CheckIdCardBean> call, Throwable t) {
                                    ToastUtils.showShort("错误：" + t.getMessage());
                                }
                            });
                        } else {
                            ToastUtils.showShort("不能输入为空");
                        }

                    }
                });

                break;
        }
    }


    // ———————————获取当前结果———————————
    @Override
    public Map getResult() {
        switch (type) {
            case TYPE_FILED_FORM_EDIT_TEXT:
                Editable editable = rightLayoutLeftEt.getText();
                return getMapResult(editable.toString());
            case TYPE_FILED_FORM_TIME:
                CharSequence timeFiled = rightLayoutTimeFiledValue.getText();
                return getMapResult(timeFiled.toString());
            case TYPE_FILED_FORM_SELECT_DATA:
                // 2018/1/2 这里获取到 map，去后面的数据提交到服务器。 别问我为什么，问了也不想说
                if (rightLayoutSelectDataFiledValue != null) {
                    CharSequence selectFiled = rightLayoutSelectDataFiledValue.getText();
                    return getMapResult(selectMap.get(selectFiled.toString()));
                }
                return null;
            case TYPE_FILED_FORM_IMAGE:
                if (defaultValue instanceof String) {
                    return getMapResult((String) defaultValue);
                }
            case TYPE_FILED_FORM_DOUBLE_BUTTON:
                return null;
            case TYPE_FILED_FORM_TWO_TIME_BUTTON:
                // TODO: 2018/1/2  这里请使用 逗号 区分
                return getMapResult(rightLayoutTwoTimeLeftTv.getText().toString() + "," + rightLayoutTwoTimeRightTv.getText().toString());
            case TYPE_FILED_FORM_ID_CARD:
                if (defaultValue instanceof String[]) {
                    String[] value = (String[]) defaultValue;

                    String stringBuilder = value[0] +
                            "," +
                            value[1];
                    return getMapResult(stringBuilder);
                }
            case TYPE_FILED_FORM_ID_CARD_NUMBER:
                if (rightLayoutLeftIdCardNumberEt != null)
                    return getMapResult(rightLayoutLeftIdCardNumberEt.getText().toString());
        }

        return null;
    }

    private Map getMapResult(String value) {
        Map map = new HashMap();
        map.put(ZK_FILED_TABLE_NAME, dataBean.getTable_name());
        map.put(ZK_FILED_NEW_VALUE, value);
        return map;
    }

    // ———————————内部使用方法———————————
    private void setCurrentTime(TextView rightLayoutTwoTimeLeftTv) {
        try {
            if (defaultValue instanceof String) {
                if (!TextUtils.isEmpty((CharSequence) defaultValue)) {
                    String[] date = ((String) defaultValue).split("-");

                    yearStr = date[0];
                    monthStr = date[1];
                    dayStr = date[2];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }


        rightLayoutTwoTimeLeftTv.setText(String.format(context.getString(R.string.date_year_month_day), yearStr, monthStr, dayStr));
    }

    private void showTimeDialog(View view) {
        TextView btn = (TextView) view;

        try {
            final DatePicker picker2 = new DatePicker(activity);
            picker2.setCanceledOnTouchOutside(true);
            picker2.setUseWeight(true);
            picker2.setTopPadding(ConvertUtils.toPx(context, 10));
            picker2.setRangeEnd(2050, 12, 31);
            picker2.setRangeStart(1900, 1, 1);
            picker2.setContentPadding(15, 0);
            picker2.setLabel(getContext().getString(R.string.zk_filed_year_label),
                    getContext().getString(R.string.zk_filed_month_label),
                    getContext().getString(R.string.zk_filed_day_label));
            picker2.setSelectedItem(year, month - 1, day);

            picker2.setResetWhileWheel(false);
            picker2.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year1, month1, day1) ->
                    btn.setText(String.format(context.getString(R.string.date_year_month_day), year1, month1, day1)));
            picker2.setOnWheelListener(new DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index12, String year) {
                    setDateTitleText(picker2);
                }

                @Override
                public void onMonthWheeled(int index12, String month) {
                    setDateTitleText(picker2);
                }

                @Override
                public void onDayWheeled(int index12, String day) {
                    setDateTitleText(picker2);
                }
            });
            picker2.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDateTitleText(DatePicker picker2) {
        String year = picker2.getSelectedYear();
        String month = picker2.getSelectedMonth();
        String day = picker2.getSelectedDay();

        picker2.setTitleText(String.format(context.getString(R.string.date_year_month_day), year, month, day));
    }


    // ———————————注解枚举———————————
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_FILED_FORM_EDIT_TEXT, TYPE_FILED_FORM_SELECT_DATA, TYPE_FILED_FORM_TIME, TYPE_FILED_FORM_IMAGE,
            TYPE_FILED_FORM_DOUBLE_BUTTON, TYPE_FILED_FORM_TWO_TIME_BUTTON, TYPE_FILED_FORM_ID_CARD,
            TYPE_FILED_FORM_ID_CARD_NUMBER,})
    public @interface FiledFormType {
        int key() default TYPE_FILED_FORM_EDIT_TEXT;
    }

    public static final int TYPE_FILED_FORM_EDIT_TEXT = 1;
    public static final int TYPE_FILED_FORM_SELECT_DATA = 2;
    public static final int TYPE_FILED_FORM_TIME = 3;
    public static final int TYPE_FILED_FORM_IMAGE = 4;
    public static final int TYPE_FILED_FORM_DOUBLE_BUTTON = 5;
    public static final int TYPE_FILED_FORM_TWO_TIME_BUTTON = 6;
    public static final int TYPE_FILED_FORM_ID_CARD = 7;
    public static final int TYPE_FILED_FORM_ID_CARD_NUMBER = 8;

    @FiledFormType
    int defaultKey;

    @FiledFormType
    public int getConstant() {
        return defaultKey;
    }

    public void setConstant(@FiledFormType int key) {
        this.defaultKey = key;
    }


    public interface ZKFiledOnClickListener {
        void onClick(int position, View view);
    }

}
