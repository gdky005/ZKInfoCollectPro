package cc.zkteam.zkinfocollectpro.activity.familyPlanningInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.adapter.ChildInfoAdapter;
import cc.zkteam.zkinfocollectpro.adapter.GynecologicExaminationAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.ChildInfoBean;
import cc.zkteam.zkinfocollectpro.bean.GynecologicExaminationBean;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Administrator on 2017/12/29.
 */

public class ConceptionControlActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_child_count)
    EditText mChildCount;
    @BindView(R.id.tv_cc_way)
    TextView mCcWay;
    @BindView(R.id.tv_select_child_birthday)
    TextView mSelectChildBirthday;
    @BindView(R.id.et_service_unit)
    EditText mServiceUnit;
    @BindView(R.id.et_cc_deactivated_reason)
    EditText mCcDeactivatedReason;
    @BindView(R.id.btn_add_other)
    Button mAddOtherBtn;
    @BindView(R.id.btn_save_and_commit)
    Button mSaveAndCommitBtn;
    @BindView(R.id.rv_gynecologic_examination)
    RecyclerView mGynecologicExamination;
    private GynecologicExaminationAdapter mAdapter;
    private ArrayList<GynecologicExaminationBean> mData;
    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_conception_control;
    }

    @Override
    protected void initViews() {
        mToolbarTitle.setText("计生信息填写");
        initToolbar(mToolbar);
        initRecyclerView();
        initSelector(mCcWay, new String[]{"输卵管结扎", "未知"});
    }

    private void initRecyclerView() {
        mGynecologicExamination.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mData.add(new GynecologicExaminationBean());
        mAdapter = new GynecologicExaminationAdapter(this, mData, null);
        mGynecologicExamination.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mSelectChildBirthday.setOnClickListener(this::showTimeDialog);
    }

    @Override
    protected void initData() {
        initDate();
        mSelectChildBirthday.setText(String.format(getString(R.string.date_year_month_day),
                mYearStr, mMonthStr, mDayStr));
    }

    private void initSelector(TextView view, String[] value) {
        view.setOnClickListener(v1 -> {
            TextView btn = (TextView) v1;
            OptionPicker picker3 = new OptionPicker(ConceptionControlActivity.this, value);
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

    private void initDate() {
        mYear = Calendar.getInstance().get(Calendar.YEAR);
        mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        mYearStr = String.valueOf(mYear);
        mMonthStr = String.valueOf(mMonth);
        mDayStr = String.valueOf(mDay);
    }

    private void showTimeDialog(View view) {
        TextView btn = (TextView) view;

        try {
            final DatePicker picker2 = new DatePicker(this);
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
