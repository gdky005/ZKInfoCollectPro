package cc.zkteam.zkinfocollectpro.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.adapter.BasicInfoItemAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.BasicInfoItemBean;

/**
 * 信息录入右侧抽屉
 */
public class BaseinfoRightFragment extends BaseFragment implements RvListener {


    @BindView(R.id.titleimage)
    ImageView titleimage;
    @BindView(R.id.titletextview)
    TextView titletextview;
    @BindView(R.id.titleview)
    RelativeLayout titleview;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private BasicInfoItemAdapter adapter;
    private List<BasicInfoItemBean> list;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_baseinfo_right;
    }

    @Override
    public void initView(View rootView) {
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Resources res = mContext.getResources();
        list.clear();
        for (int i = 0; i <= 30; i++) {
            int id = res.getIdentifier("basininfoitem" + (i), "drawable", getActivity().getPackageName());
            String[] mItems_name = mContext.getResources().getStringArray(R.array.base_info_item_name);
            String[] mItems_type = mContext.getResources().getStringArray(R.array.base_info_item_type);

            BasicInfoItemBean bean = new BasicInfoItemBean();
            bean.image = id;
            bean.itemname = mItems_name[i].trim();
            bean.itemtype = mItems_type[i].trim();
            list.add(bean);

        }

        adapter = new BasicInfoItemAdapter(getActivity(), list, this);
        recycleview.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }


    @OnClick(R.id.titleimage)
    public void onViewClicked() {
    }


    @Override
    public void onItemClick(int id, int position) {
        EventBus.getDefault().post(list.get(position));
    }
}
