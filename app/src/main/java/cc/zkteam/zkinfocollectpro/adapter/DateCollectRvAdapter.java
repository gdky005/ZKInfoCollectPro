package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.RentPersonInfoActivity;
import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.viewholder.DataCollectRvHolder;

/**
 * Created by Administrator on 2017/12/15.
 */

public class DateCollectRvAdapter extends RvAdapter<RentPersoner.PersonlistBean> {
    private String mAddress = "";

    public DateCollectRvAdapter(Context context, List<RentPersoner.PersonlistBean> list, RvListener listener) {
        this(context, list, listener, "");
    }

    public DateCollectRvAdapter(Context context, List<RentPersoner.PersonlistBean> list, RvListener listener, String address) {
        super(context, list, listener);
        mAddress = address;
    }


    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.data_collect_head;
        } else if (viewType == 1) {
            return R.layout.tab_view;
        } else {
            return R.layout.data_collect_btn;
        }
    }

    @Override
    public void addData(List<RentPersoner.PersonlistBean> data) {
        this.list.addAll(data);
        notifyItemRangeInserted(1, data.size());
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) {
            viewType = 0;
        } else if (position == getItemCount() - 1) {
            viewType = 2;
        }
        return viewType;
    }


    @Override
    protected RvHolder getHolder(View view, int viewType) {
        DataCollectRvHolder holder = new DataCollectRvHolder(view, viewType, listener);
        holder.setAdapter(this);
        if (viewType == 0) holder.setAddress(mAddress);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list, position);
    }


    @Override
    public void onBindViewHolder(RvHolder holder, int position, List<Object> payloads) {
        if (payloads.size() == 0) {
            super.onBindViewHolder(holder, position, payloads);
        } else {

        }

    }
}
