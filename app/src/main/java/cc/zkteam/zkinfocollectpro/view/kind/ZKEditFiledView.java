package cc.zkteam.zkinfocollectpro.view.kind;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cc.zkteam.zkinfocollectpro.R;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ZKFiledView
 * Created by wangqing on 2017/12/26.
 */

public class ZKEditFiledView extends LinearLayout {

    TextView numberFiled;
    TextView keyFiled;
    EditText valueFiled;
    Button selectDataFiled;
    Button timeFiled;

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

    private Activity activity;
    private Context context;

    public ZKEditFiledView(Context context) {
        super(context);
        init(context);

    }

    public ZKEditFiledView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZKEditFiledView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        if (context instanceof Activity)
            this.activity = (Activity) context;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.kind_layout_edit_field, null);

        numberFiled = view.findViewById(R.id.text_filed_number);
        keyFiled = view.findViewById(R.id.text_filed_key);


        valueFiled = view.findViewById(R.id.right_layout_text_filed_value);
        selectDataFiled = view.findViewById(R.id.right_layout_select_data_filed_value);
        timeFiled = view.findViewById(R.id.right_layout_time_filed_value);

        addView(view);
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

    public void setZKFiledOnClickListener(ZKFiledOnClickListener zkFiledOnClickListener) {
        this.zkFiledOnClickListener = zkFiledOnClickListener;
    }

    private void updateView(String number, String k, String v, int index, @FiledFormType int filedFormType) {
        if (number != null && numberFiled != null && !numberFiled.getText().equals(number)) {
            numberFiled.setText(number);
        }

        if (k != null && keyFiled != null && !keyFiled.getText().equals(k)) {
            keyFiled.setText(k);
        }

        if (v != null && valueFiled != null && !valueFiled.getText().equals(v)) {
            valueFiled.setText(v);
        }

//        selectDataFiled.setOnClickListener(onClickListener);
//        timeFiled.setOnClickListener(onClickListener);

        selectDataFiled.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
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
                    public void onOptionPicked(int index, String item) {
                        btn.setText(item);
                    }
                });
                picker3.show();
            }
        });

        timeFiled.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Button btn = (Button) v;

                final DatePicker picker2 = new DatePicker(activity);
                picker2.setCanceledOnTouchOutside(true);
                picker2.setUseWeight(true);
                picker2.setTopPadding(ConvertUtils.toPx(context, 10));
                picker2.setRangeEnd(2111, 1, 11);
                picker2.setRangeStart(2016, 8, 29);
                picker2.setSelectedItem(2050, 10, 14);
                picker2.setContentPadding(15, 0);

                picker2.setResetWhileWheel(false);
                picker2.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {

                        btn.setText(year + "年" + month + "月" + day + "日");
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
            }
        });

        switch (filedFormType) {
            case TYPE_FILED_FORM_EDIT_TEXT:
                if (valueFiled != null) {
                    valueFiled.setVisibility(VISIBLE);
                }
                if (selectDataFiled != null) {
                    selectDataFiled.setVisibility(GONE);
                }
                if (timeFiled != null) {
                    timeFiled.setVisibility(GONE);
                }
                break;
            case TYPE_FILED_FORM_SELECT_DATA:
                if (valueFiled != null) {
                    valueFiled.setVisibility(GONE);
                }
                if (selectDataFiled != null) {
                    selectDataFiled.setVisibility(VISIBLE);
                }
                if (timeFiled != null) {
                    timeFiled.setVisibility(GONE);
                }
                break;
            case TYPE_FILED_FORM_TIME:
                if (valueFiled != null) {
                    valueFiled.setVisibility(GONE);
                }
                if (selectDataFiled != null) {
                    selectDataFiled.setVisibility(GONE);
                }
                if (timeFiled != null) {
                    timeFiled.setVisibility(VISIBLE);
                }
                break;
        }
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
