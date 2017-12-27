package cc.zkteam.zkinfocollectpro.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.adapter.ProblemPreviewAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;

/**
 * Created by Administrator on 2017/12/22.
 */

public class MyProblemListActivity extends BaseActivity implements RvListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_my_problem)
    RecyclerView mMyProblem;
    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    private List<ProblemPreview> mProblemList = new ArrayList<>();
    private ProblemPreviewAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_problem;
    }

    @Override
    protected void initViews() {
        initToolbar(mToolbar);
        mToolbarTitle.setText("我的问题上报");

        initRecyclerView();
    }

    private void initRecyclerView() {
        mMyProblem.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProblemPreviewAdapter(this, mProblemList, this);
        mMyProblem.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getProblemList();
        mAdapter.notifyDataSetChanged();
    }

    private void getProblemList() {
        mProblemList.add(new ProblemPreview("12345665222",
                "这是一个不太清晰的描述",
                "2017-12-01  10:13:45"));
        mProblemList.add(new ProblemPreview("12345665222",
                "这是一个不太清晰的描述",
                "2017-12-01  10:13:45"));
        mProblemList.add(new ProblemPreview("12345665222",
                "这是一个不太清晰的描述",
                "2017-12-01  10:13:45"));
        mProblemList.add(new ProblemPreview("12345665222",
                "这是一个不太清晰的描述",
                "2017-12-01  10:13:45"));
        mProblemList.add(new ProblemPreview("12345665222",
                "这是一个不太清晰的描述",
                "2017-12-01  10:13:45"));
    }

    @Override
    public void onItemClick(int id, int position) {

    }
}
