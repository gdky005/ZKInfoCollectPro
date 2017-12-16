package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;

/**
 * Created by loong on 2017/12/15.
 */

public class SingInFragment extends BaseFragment {
    @BindView(R.id.img_sign_main)
    ZKImageView imgSignMain;
    @BindView(R.id.tv_collect_total_number)
    TextView tvCollectTotalNumber;
    @BindView(R.id.tv_collect_complete)
    TextView tvCollectComplete;
    @BindView(R.id.tv_collect_complete_proportion)
    TextView tvCollectCompleteProportion;
    @BindView(R.id.tv_collect_current)
    TextView tvCollectCurrent;
    @BindView(R.id.tv_collect_current_proportion)
    TextView tvCollectCurrentProportion;
    @BindView(R.id.tv_collect_not_yet)
    TextView tvCollectNotYet;
    @BindView(R.id.tv_collect_not_yet_proportion)
    TextView tvCollectNotYetProportion;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null)
            unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
