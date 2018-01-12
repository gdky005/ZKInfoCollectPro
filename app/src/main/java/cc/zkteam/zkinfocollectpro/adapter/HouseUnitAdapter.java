package cc.zkteam.zkinfocollectpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;

/**
 * Created by Administrator on 2017/12/26.
 */

public class HouseUnitAdapter extends LDSpinnerAdapter {

    private static final String Invaild = "0";

    public HouseUnitAdapter(Context mContext) {
        super(mContext);
        reSetData();
    }

    @Override
    public void reSetData() {
        mData.clear();
        mData.add(new ZHCommunityBean.DataBean(Invaild, Invaild, Invaild, Invaild));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {


        return isHaveData() ? Integer.parseInt(mData.get(1).getBuildunit()) + 1 : 1;
    }

    public void setUnitData(List<ZHCommunityBean.DataBean> tempData) {
        if (tempData != null && tempData.size() > 0) {
            reSetData();
            mData.addAll(tempData);
            notifyDataSetChanged();
        }
    }

    @Override
    public Object getItem(int i) {
        return i == 0 ? "请选择" : i + " 单元";
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
        TextView title = (TextView) result.findViewById(android.R.id.text1);
        title.setText(((String) getItem(i)));
        return result;
    }
}
