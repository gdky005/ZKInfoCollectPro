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
 * Created by lmj on 2017/12/25.
 */

public class LDSpinnerAdapter extends BaseAdapter {


    private  List<ZHCommunityBean.DataBean> mData;


    private Context context;


    public LDSpinnerAdapter(Context mContext) {
        this.context = mContext;
        mData = new ArrayList<>();
        mData.add(new ZHCommunityBean.DataBean("-1", "请选择"));
    }

    public void setData(List<ZHCommunityBean.DataBean> tempData) {
        if (tempData != null && tempData.size() > 0){
            reSetData();
            mData.addAll(tempData);
            notifyDataSetChanged();
        }
    }

    public void reSetData(){
        mData.clear();
        mData.add(new ZHCommunityBean.DataBean("-1", "请选择"));
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ZHCommunityBean.DataBean getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(R.layout.item_problem_type, null);
        TextView title = result.findViewById(android.R.id.text1);
        title.setText(mData.get(i).getName());
        return result;
    }
}
