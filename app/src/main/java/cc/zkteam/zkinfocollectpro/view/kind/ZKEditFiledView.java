package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

import cc.zkteam.zkinfocollectpro.R;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ZKFiledView
 * Created by wangqing on 2017/12/26.
 */

public class ZKEditFiledView extends ZKBaseView {

    TextView keyFiled;
    TextView numberFiled;

    @FiledFormType
    int type;
    String number;
    String key;
    String value;
    int index;

    private ZKFiledOnClickListener zkFiledOnClickListener;

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (zkFiledOnClickListener != null)
                zkFiledOnClickListener.onClick(index, v);
        }
    };

    public ZKEditFiledView(Context context) {
        super(context);
    }

    public ZKEditFiledView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKEditFiledView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.kind_layout_edit_field;
    }

    @Override
    protected void initViews(View view) {
        keyFiled = findView(R.id.text_filed_key);
        numberFiled = findView(R.id.text_filed_number);

    }

    public void setData(String number, String key, String value, int index) {
        setData(number, key, value, index, TYPE_FILED_FORM_EDIT_TEXT);
    }

    public void setData(String number, String key, String value, int index, @FiledFormType int filedFormType) {
        this.number = number;
        this.key = key;
        this.value = value;
        this.index = index;
        this.type = filedFormType;
        updateView(number, key, value, index, type);
    }

    private void updateView(String number, String key, String value, int index, @FiledFormType int filedFormType) {
        setViewText(numberFiled, number);
        setViewText(keyFiled, key);


        switch (filedFormType) {
            case TYPE_FILED_FORM_EDIT_TEXT:
                View rightLayoutLeftEtLayout = ((ViewStub) findViewById(R.id.right_layout_left_et_layout)).inflate();
                EditText rightLayoutLeftEt = rightLayoutLeftEtLayout.findViewById(R.id.right_layout_left_et);
                rightLayoutLeftEt.setText(value);

                break;
            case TYPE_FILED_FORM_TIME:
                View rightLayoutTimeFiledValueLayout = ((ViewStub) findViewById(R.id.right_layout_time_filed_value_layout)).inflate();
                TextView rightLayoutTimeFiledValue = rightLayoutTimeFiledValueLayout.findViewById(R.id.right_layout_time_filed_value);
                rightLayoutTimeFiledValue.setOnClickListener(v12 -> {
                    TextView btn = (TextView) v12;

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    final DatePicker picker2 = new DatePicker(activity);
                    picker2.setLabel(String.valueOf(year), String.valueOf(month), String.valueOf(day));
                    picker2.setCanceledOnTouchOutside(true);
                    picker2.setUseWeight(true);
                    picker2.setTopPadding(ConvertUtils.toPx(context, 10));
                    picker2.setRangeEnd(2111, 1, 11);
                    picker2.setRangeStart(2016, 8, 29);
                    picker2.setSelectedItem(year, month, day);
                    picker2.setContentPadding(15, 0);

                    picker2.setResetWhileWheel(false);
                    picker2.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year1, month1, day1) ->
                            btn.setText(String.format(context.getString(R.string.date_year_month_day), year1, month1, day1)
                            ));
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
                });

                break;
            case TYPE_FILED_FORM_SELECT_DATA:
                View rightLayoutSelectDataFiledValueLayout = ((ViewStub) findViewById(R.id.right_layout_select_data_filed_value_layout)).inflate();
                TextView rightLayoutSelectDataFiledValue = rightLayoutSelectDataFiledValueLayout.findViewById(R.id.right_layout_select_data_filed_value);
                rightLayoutSelectDataFiledValue.setOnClickListener(v1 -> {
                    TextView btn = (TextView) v1;
                    OptionPicker picker3 = new OptionPicker(activity, new String[]{
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
                        public void onOptionPicked(int index1, String item) {
                            btn.setText(item);
                        }
                    });
                    picker3.show();
                });
                break;

            case TYPE_FILED_FORM_IMAGE:
                View rightLayoutImageViewLayout = ((ViewStub) findViewById(R.id.right_layout_image_view_layout)).inflate();
                ImageView rightLayoutImageView = rightLayoutImageViewLayout.findViewById(R.id.right_layout_image_view);

                break;

            case TYPE_FILED_FORM_DOUBLE_BUTTON:
                View rightLayoutBtnLayout = ((ViewStub) findViewById(R.id.right_layout_btn_layout)).inflate();
                LinearLayout rightLayoutBtnLl = rightLayoutBtnLayout.findViewById(R.id.right_layout_btn_ll);

                break;

            case TYPE_FILED_FORM_TWO_TIME_BUTTON:
                View rightLayoutTwoTimeLlLayout = ((ViewStub) findViewById(R.id.right_layout_two_time_ll_layout)).inflate();
                RelativeLayout rightLayoutTwoTimeLl = rightLayoutTwoTimeLlLayout.findViewById(R.id.right_layout_two_time_ll);

                rightLayoutTwoTimeLl.findViewById(R.id.right_layout_two_time_left_tv).setOnClickListener(v -> {
                    TextView btn = (TextView) v;

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    final DatePicker picker2 = new DatePicker(activity);
                    picker2.setLabel(String.valueOf(year), String.valueOf(month), String.valueOf(day));
                    picker2.setCanceledOnTouchOutside(true);
                    picker2.setUseWeight(true);
                    picker2.setTopPadding(ConvertUtils.toPx(context, 10));
                    picker2.setRangeEnd(2111, 1, 11);
                    picker2.setRangeStart(2016, 8, 29);
                    picker2.setSelectedItem(year, month, day);
                    picker2.setContentPadding(15, 0);

                    picker2.setResetWhileWheel(false);
                    picker2.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year1, month1, day1) ->
                            btn.setText(String.format(context.getString(R.string.date_year_month_day), year1, month1, day1)
                            ));
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
                });

                rightLayoutTwoTimeLl.findViewById(R.id.right_layout_two_time_right_tv).setOnClickListener(v -> {
                    TextView btn = (TextView) v;

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    final DatePicker picker2 = new DatePicker(activity);
                    picker2.setLabel(String.valueOf(year), String.valueOf(month), String.valueOf(day));
                    picker2.setCanceledOnTouchOutside(true);
                    picker2.setUseWeight(true);
                    picker2.setTopPadding(ConvertUtils.toPx(context, 10));
                    picker2.setRangeEnd(2111, 1, 11);
                    picker2.setRangeStart(2016, 8, 29);
                    picker2.setSelectedItem(year, month, day);
                    picker2.setContentPadding(15, 0);

                    picker2.setResetWhileWheel(false);
                    picker2.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year1, month1, day1) ->
                            btn.setText(String.format(context.getString(R.string.date_year_month_day), year1, month1, day1)
                            ));
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
                });

                break;

            case TYPE_FILED_FORM_ID_CARD:
                View rightLayoutIdCardLayout = ((ViewStub) findViewById(R.id.right_layout_id_card_layout)).inflate();
                LinearLayout rightLayoutIdCard = rightLayoutIdCardLayout.findViewById(R.id.right_layout_id_card);

                break;

            case TYPE_FILED_FORM_ID_CARD_NUMBER:
                View rightLayoutIdCardNumberLayout = ((ViewStub) findViewById(R.id.right_layout_id_card_number_layout)).inflate();
                RelativeLayout rightLayoutIdCardNumber = rightLayoutIdCardNumberLayout.findViewById(R.id.right_layout_id_card_number);
                break;
        }
    }


    private void setDateTitleText(DatePicker picker2) {
        String year = picker2.getSelectedYear();
        String month = picker2.getSelectedMonth();
        String day = picker2.getSelectedDay();

        picker2.setTitleText(String.format(context.getString(R.string.date_title_top_text), year, month, day));
    }

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
