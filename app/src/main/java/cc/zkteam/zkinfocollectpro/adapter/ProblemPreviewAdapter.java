package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.viewholder.ProblemPreviewHolder;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPreviewAdapter extends RvAdapter<ProblemPreview> {

    public ProblemPreviewAdapter(Context context, List<ProblemPreview> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_problem_preview;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ProblemPreviewHolder(view, viewType, listener);
    }
}
