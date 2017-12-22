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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;

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
}
