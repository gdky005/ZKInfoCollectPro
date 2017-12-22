package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.viewholder.DataCollectRvHolder;

/**
 * Created by Administrator on 2017/12/15.
 */

public class DateCollectRvAdapter extends RvAdapter<RentInfo> {

    public DateCollectRvAdapter(Context context, List<RentInfo> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.data_collect_head;
        } else if (viewType == 1){
            return R.layout.tab_view;
        }else {
            return R.layout.data_collect_btn;
        }

    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) {
            viewType = 0;
        } else if (position == getItemCount()-1){
            viewType =2;
        }
        return viewType;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {

        return new DataCollectRvHolder(view, viewType, listener);
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list, position);
    }
}
