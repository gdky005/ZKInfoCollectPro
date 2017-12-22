package cc.zkteam.zkinfocollectpro.activity;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.problem.ProblemReportFragment;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemDetailActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_problem_detail;
    }

    @Override
    protected void initViews() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, ProblemReportFragment.newInstance(false))
                .commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
