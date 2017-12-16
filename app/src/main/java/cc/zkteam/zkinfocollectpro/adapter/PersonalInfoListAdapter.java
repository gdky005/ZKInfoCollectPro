package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cc.zkteam.zkinfocollectpro.base.RvAdapter;
import cc.zkteam.zkinfocollectpro.base.RvHolder;
import cc.zkteam.zkinfocollectpro.base.RvListener;

/**
 * Created by loong on 2017/12/15.
 */

public class PersonalInfoListAdapter extends RvAdapter {

    public PersonalInfoListAdapter(Context context, List list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
