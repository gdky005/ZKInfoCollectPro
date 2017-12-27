package cc.zkteam.zkinfocollectpro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.eventbusbean.BasicItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseinfoRightFragment extends BaseFragment {


    @BindView(R.id.titleimage)
    ImageView titleimage;
    @BindView(R.id.titletextview)
    TextView titletextview;
    @BindView(R.id.titleview)
    RelativeLayout titleview;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    Unbinder unbinder;
    @BindView(R.id.item1)
    RelativeLayout item1;
    @BindView(R.id.item2)
    RelativeLayout item2;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_baseinfo_right;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.titleimage)
    public void onViewClicked() {
    }

    @OnClick({R.id.item1, R.id.item2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item1:
                EventBus.getDefault().post(new  BasicItemClick(0));
                break;
            case R.id.item2:

                EventBus.getDefault().post(new  BasicItemClick(1));

                break;
        }
    }
}
