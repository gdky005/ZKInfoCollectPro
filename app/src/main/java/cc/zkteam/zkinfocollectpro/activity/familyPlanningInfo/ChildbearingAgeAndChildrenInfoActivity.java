package cc.zkteam.zkinfocollectpro.activity.familyPlanningInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.adapter.ChildInfoAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.ChildInfoBean;

/**
 * Created by gzw10 on 2017/12/29.
 */

public class ChildbearingAgeAndChildrenInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_woman_id_no)
    TextView mWomanIdNo;
    @BindView(R.id.tv_woman_name)
    TextView mWomanName;
    @BindView(R.id.tv_woman_sex)
    TextView mWomanSex;
    @BindView(R.id.tv_woman_birthday)
    TextView mWomanBirthday;
    @BindView(R.id.tv_woman_is_only_child)
    TextView mWomanIsOnlyChild;
    @BindView(R.id.tv_woman_time_to_get_married)
    TextView mWomanTimeToGetMarried;
    @BindView(R.id.tv_woman_number_of_children)
    TextView mWomanNumberOfChildren;
    @BindView(R.id.btn_add_other)
    Button mAddOtherBtn;
    @BindView(R.id.btn_save_and_commit)
    Button mSaveAndCommitBtn;
    @BindView(R.id.rv_child_info_list)
    RecyclerView mChildInfoList;
    private ChildInfoAdapter mAdapter;
    private ArrayList<ChildInfoBean> mData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_childbearing_age_children_info;
    }

    @Override
    protected void initViews() {
        mToolbarTitle.setText("计生信息填写");
        initToolbar(mToolbar);
        initCA();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mChildInfoList.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mData.add(new ChildInfoBean());
        mAdapter = new ChildInfoAdapter(this, mData, null, this);
        mChildInfoList.setAdapter(mAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void initCA() {
        mWomanIdNo.setText("110101199304050023");
        mWomanName.setText("落落");
        mWomanSex.setText("女");
        mWomanBirthday.setText("1989-09-06");
        mWomanIsOnlyChild.setText("否");
        mWomanTimeToGetMarried.setText("2009-09-06");
        mWomanNumberOfChildren.setText("0");
    }


    @Override
    protected void initListener() {
        mAddOtherBtn.setOnClickListener(v -> {
//            mData.clear();
//            mData.add(new ChildInfoBean());
//            mAdapter.addData(mData);
//            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
    }
}
