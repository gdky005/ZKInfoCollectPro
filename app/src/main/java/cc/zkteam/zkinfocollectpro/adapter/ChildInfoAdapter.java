package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.ChildInfoBean;
import cc.zkteam.zkinfocollectpro.viewholder.ChildInfoHolder;

/**
 * Created by Administrator on 2017/12/30.
 */

public class ChildInfoAdapter extends RvAdapter<ChildInfoBean> {
    private List<ChildInfoBean> mChildInfoBeans;
    private View.OnClickListener mDeleteListener;

    public ChildInfoAdapter(Context context, List<ChildInfoBean> data, RvListener listener) {
        super(context, data, listener);
        mChildInfoBeans = data;
    }

    public ChildInfoAdapter(Context context, List<ChildInfoBean> data, RvListener listener,
                            View.OnClickListener deleteListener) {
        this(context, data, listener);
        mDeleteListener = deleteListener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_child_info;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ChildInfoHolder(view, viewType, null, mDeleteListener);
    }
}
