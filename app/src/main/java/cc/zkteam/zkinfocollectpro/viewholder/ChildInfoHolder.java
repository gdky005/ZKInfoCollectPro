package cc.zkteam.zkinfocollectpro.viewholder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.familyPlanningInfo.ChildbearingAgeAndChildrenInfoActivity;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ChildInfoBean;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Administrator on 2017/12/30.
 */

public class ChildInfoHolder extends RvHolder<ChildInfoBean> {
    @BindView(R.id.et_child_name)
    EditText mChildName;
    @BindView(R.id.tv_select_child_sex)
    TextView mSelectChildSex;
    @BindView(R.id.tv_select_child_birthday)
    TextView mSelectChildBirthday;
    @BindView(R.id.et_child_birth_id)
    EditText mChildBirthId;
    @BindView(R.id.et_child_birth_hospital)
    EditText mChildBirthHospital;
    @BindView(R.id.tv_select_policy)
    TextView mSelectPolicy;
    @BindView(R.id.et_child_health_condition)
    EditText mChildHealthCondition;
    @BindView(R.id.et_child_phone)
    EditText mChildPhone;
    @BindView(R.id.btn_edit)
    Button mEditBtn;
    @BindView(R.id.btn_delete)
    Button mDeleteBtn;
    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Context mContext;

    public ChildInfoHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        initDate();
        mSelectChildBirthday.setOnClickListener(this::showTimeDialog);
    }

    public ChildInfoHolder(View itemView, int type, RvListener listener,
                           View.OnClickListener deletelistener) {
        this(itemView, type, listener);
        mDeleteBtn.setOnClickListener(deletelistener);
    }


    private void initDate() {
        mYear = Calendar.getInstance().get(Calendar.YEAR);
        mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        mYearStr = String.valueOf(mYear);
        mMonthStr = String.valueOf(mMonth);
        mDayStr = String.valueOf(mDay);
    }

    @Override
    public void bindHolder(ChildInfoBean childInfoBean, int position) {
        initSelector(mSelectChildSex, new String[]{"男", "女"});
        initSelector(mSelectPolicy, new String[]{"内", "外"});
        mSelectChildBirthday.setText(String.format(mContext.getString(R.string.date_title_top_text),
                mYearStr, mMonthStr, mDayStr));
    }

    private void initSelector(TextView view, String[] value) {
        view.setOnClickListener(v1 -> {
            TextView btn = (TextView) v1;
            OptionPicker picker3 = new OptionPicker((Activity) mContext, value);
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
        });
        view.setText(value[0]);
    }

    private void showTimeDialog(View view) {
        TextView btn = (TextView) view;

        try {
            final DatePicker picker2 = new DatePicker((Activity) mContext);
            picker2.setCanceledOnTouchOutside(true);
            picker2.setUseWeight(true);
            picker2.setTopPadding(ConvertUtils.toPx(mContext, 10));
            picker2.setRangeEnd(2111, 1, 11);
            picker2.setRangeStart(2016, 8, 29);
            picker2.setContentPadding(15, 0);
            picker2.setLabel(mYearStr, mMonthStr, mDayStr);
            picker2.setSelectedItem(mYear, mMonth - 1, mDay);

            picker2.setResetWhileWheel(false);
            picker2.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year1, month1, day1) ->
                    btn.setText(String.format(mContext.getString(R.string.date_year_month_day), year1, month1, day1)));
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

        picker2.setTitleText(String.format(mContext.getString(R.string.date_title_top_text), year, month, day));
    }
}
