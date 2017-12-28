package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.BasicInfoItemBean;
import cc.zkteam.zkinfocollectpro.viewholder.BasinInfoItemHolder;

/**
 * Created by popydog on 2017/12/28.
 */

public class BasicInfoItemAdapter extends RvAdapter<BasicInfoItemBean> {
    public BasicInfoItemAdapter(Context context, List<BasicInfoItemBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_basininforight;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new BasinInfoItemHolder(view, viewType, listener);
    }
}
