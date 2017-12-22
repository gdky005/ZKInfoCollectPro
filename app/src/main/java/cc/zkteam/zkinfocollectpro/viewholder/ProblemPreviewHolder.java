package cc.zkteam.zkinfocollectpro.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.ProblemDetailActivity;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPreviewHolder extends RvHolder<ProblemPreview> {
    @BindView(R.id.tv_problem_no)
    TextView mProblemNo;
    @BindView(R.id.tv_problem_desc)
    TextView mProblemDesc;
    @BindView(R.id.tv_problem_report_time)
    TextView mProblemReportTime;
    @BindView(R.id.btn_check_problem_detail)
    Button mCheckProblemDetailBtn;

    public ProblemPreviewHolder(View itemView, int type) {
        this(itemView, type, null);
    }

    public ProblemPreviewHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this,itemView);
        mCheckProblemDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageCtrl.startActivity(v.getContext(), ProblemDetailActivity.class);
            }
        });
    }

    @Override
    public void bindHolder(ProblemPreview problem, int position) {
        mProblemNo.setText(problem.getProblemNo());
        mProblemDesc.setText(problem.getProblemDesc());
        mProblemReportTime.setText(problem.getProblemReportTime());
    }
}
