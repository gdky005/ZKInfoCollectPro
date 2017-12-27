package cc.zkteam.zkinfocollectpro.viewholder;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    @BindView(R.id.ll_check_problem_detail)
    LinearLayout mCheckProblemDetailLayout;

    public ProblemPreviewHolder(View itemView, int type) {
        this(itemView, type, null);
    }

    public ProblemPreviewHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
        mCheckProblemDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageCtrl.startActivity(v.getContext(), ProblemDetailActivity.class);
            }
        });
    }

    @Override
    public void bindHolder(ProblemPreview problem, int position) {
        textColor("编号：", problem.getProblemNo(), mProblemNo);
        textColor("描述：", problem.getProblemDesc(), mProblemDesc);
        textColor("时间：", problem.getProblemReportTime(), mProblemReportTime);
    }

    private void textColor(String prefix, String content, TextView textView) {
        SpannableString spannable = new SpannableString(prefix + content);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, prefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), prefix.length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }
}
