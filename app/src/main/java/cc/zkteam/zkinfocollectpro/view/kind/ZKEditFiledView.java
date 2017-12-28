package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
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
    TextView timeFiled;
    TextView numberFiled;
    TextView selectDataFiled;
    EditText valueFiled;

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

        timeFiled = findView(R.id.right_layout_time_filed_value);
        valueFiled = findView(R.id.right_layout_text_filed_value);
        selectDataFiled = findView(R.id.right_layout_select_data_filed_value);
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

    private void updateView(String number, String k, String v, int index, @FiledFormType int filedFormType) {
        setViewText(numberFiled, number);
        setViewText(keyFiled, k);
        setViewText(valueFiled, v);

        selectDataFiled.setOnClickListener(v1 -> {
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

        timeFiled.setOnClickListener(v12 -> {
            TextView btn = (TextView) v12;

            int year =Calendar.getInstance().get(Calendar.YEAR);
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

        switch (filedFormType) {
            case TYPE_FILED_FORM_EDIT_TEXT:
                setVisibility(valueFiled, true);
                setVisibility(selectDataFiled, false);
                setVisibility(timeFiled, false);
                break;
            case TYPE_FILED_FORM_SELECT_DATA:
                setVisibility(valueFiled, false);
                setVisibility(selectDataFiled, true);
                setVisibility(timeFiled, false);
                break;
            case TYPE_FILED_FORM_TIME:
                setVisibility(valueFiled, false);
                setVisibility(selectDataFiled, false);
                setVisibility(timeFiled, true);
                break;
        }
    }


    private void setDateTitleText(DatePicker picker2) {
        String year =  picker2.getSelectedYear();
        String month =  picker2.getSelectedMonth();
        String day =  picker2.getSelectedDay();

        picker2.setTitleText(String.format(context.getString(R.string.date_title_top_text), year, month, day));
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_FILED_FORM_EDIT_TEXT, TYPE_FILED_FORM_SELECT_DATA, TYPE_FILED_FORM_TIME})
    public @interface FiledFormType {
        int key() default TYPE_FILED_FORM_EDIT_TEXT;
    }

    public static final int TYPE_FILED_FORM_EDIT_TEXT = 1;
    public static final int TYPE_FILED_FORM_SELECT_DATA = 2;
    public static final int TYPE_FILED_FORM_TIME = 3;

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
