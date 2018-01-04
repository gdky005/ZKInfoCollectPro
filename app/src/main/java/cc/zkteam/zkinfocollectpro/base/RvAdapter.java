package cc.zkteam.zkinfocollectpro.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.R;

/**
 * Created by wustor
 * 日期  2017-10-30.
 * 邮箱  fat_chao@163.com
 */
@SuppressWarnings("unchecked")
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    protected List<T> list;
    protected Context mContext;
    protected RvListener listener;
    protected LayoutInflater mInflater;

    public RvAdapter(Context context, List<T> data, RvListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
        if (data != null)
            this.list.addAll(data);
        this.listener = listener;
    }

    public void addData(List<T> data) {
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    public void cleanAndAddAll(List<T> data) {
        list.clear();
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getLayoutId(R.layout.item_personal_info), null);
        return getHolder(view, viewType);
    }

    protected abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setText(TextView textView, String str) {
        if (textView != null && null != str) {
            textView.setText(str);
        }
    }

    protected abstract RvHolder getHolder(View view, int viewType);

}
