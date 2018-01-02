package cc.zkteam.zkinfocollectpro.activity;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.adapter.ProblemPreviewAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mLoading.setVisibility(View.VISIBLE);
        ZHConnectionManager.getInstance().getZHApi().getProblemList(ZKICApplication.zhLoginBean.getId()).enqueue(new Callback<ProblemPreview>() {
            @Override
            public void onResponse(Call<ProblemPreview> call, Response<ProblemPreview> response) {
                Log.e("TAG", response.body().toString());
                mLoading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mAdapter = new ProblemPreviewAdapter(MyProblemListActivity.this,
                            response.body().getData(), MyProblemListActivity.this);
                    mMyProblem.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<ProblemPreview> call, Throwable t) {
                mLoading.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(int id, int position) {

    }
}
