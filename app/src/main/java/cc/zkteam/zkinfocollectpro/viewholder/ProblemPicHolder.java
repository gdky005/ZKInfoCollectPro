package cc.zkteam.zkinfocollectpro.viewholder;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPicHolder extends RvHolder<String> {
    @BindView(R.id.iv_problem_pics)
    ZKImageView mProblemPic;

    public ProblemPicHolder(View itemView, int type) {
        this(itemView, type, null);
    }

    public ProblemPicHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(String url, int position) {
        mProblemPic.setImageURI(url);
    }
}
