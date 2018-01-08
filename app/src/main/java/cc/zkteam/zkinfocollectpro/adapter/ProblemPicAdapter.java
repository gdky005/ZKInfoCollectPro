package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.viewholder.ProblemPicHolder;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPicAdapter extends RvAdapter<String> {

    public ProblemPicAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_problem_pic;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ProblemPicHolder(view, viewType, listener);
    }
}
