package cc.zkteam.zkinfocollectpro.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by wustor
 * 日期  2017-10-30.
 * 邮箱  fat_chao@163.com
 */
public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
    protected RvListener mListener;
    protected int mType;

    public RvHolder(View itemView, int type, RvListener listener) {
        super(itemView);
        this.mListener = listener;
        this.mType = type;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(v.getId(), getAdapterPosition());
            }
        });
    }

    public abstract void bindHolder(T t, int position);

    public void bindHolder(List<T> data, int position) {

    }
}
