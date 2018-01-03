package cc.zkteam.zkinfocollectpro.activity;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.fragment.problem.ProblemReportFragment;
import cc.zkteam.zkinfocollectpro.viewholder.ProblemPreviewHolder;

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

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        ProblemPreview.DataBean problem = getIntent().getParcelableExtra(ProblemPreviewHolder.PROBLEM_DETAIL_FLAG);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, ProblemReportFragment.newInstance(false, problem))
                .commit();
    }
}
