package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;

/**
 * Created by Administrator on 2017/12/25.
 */

public class LDSpinnerAdapter extends BaseAdapter {
    private int index = -1;


    protected List<ZHCommunityBean.DataBean> mData;


    protected Context context;


    public LDSpinnerAdapter(Context mContext) {
        this.context = mContext;
        mData = new ArrayList<>();
        mData.add(new ZHCommunityBean.DataBean("-1", "请选择"));
    }

    public LDSpinnerAdapter(Context mContext, int index) {
        this.context = mContext;
        mData = new ArrayList<>();
        this.index = index;
    }

    public int getmCeng() {
        return isHaveData() ? Integer.parseInt(mData.get(1).getBuildceng()) : 0;
    }

    public int getHome() {
        return isHaveData() ? Integer.parseInt(mData.get(1).getBuildhome()) : 0;
    }

    public boolean isHaveData() {
        return mData.size() == 1 ? false : true;
    }

    public void setData(List<ZHCommunityBean.DataBean> tempData) {
        if (tempData != null && tempData.size() > 0) {
            reSetData();
            mData.addAll(tempData);
            notifyDataSetChanged();
        }
    }

    public void reSetData() {
        mData.clear();
        if (index != 0) {
            mData.add(new ZHCommunityBean.DataBean("-1", "请选择"));
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
        TextView title = (TextView) result.findViewById(android.R.id.text1);
        title.setText(mData.get(i).getName());
        return result;
    }
}
