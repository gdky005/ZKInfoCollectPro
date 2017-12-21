package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;

/**
 * Created by loong on 2017/12/16.
 */

public class PersonalInfoCollectFragment extends BaseFragment {
    @BindView(R.id.toolbar_personal_info_collection)
    Toolbar toolbarPersonalInfoCollection;
    @BindView(R.id.img_personal_avatar)
    ZKImageView imgPersonalAvatar;
    @BindView(R.id.tv_personal_info_collect_name)
    TextView tvPersonalInfoCollectName;
    @BindView(R.id.tv_personal_info_collect_id)
    TextView tvPersonalInfoCollectId;
    @BindView(R.id.tv_personal_info_collect_project)
    TextView tvPersonalInfoCollectProject;
    @BindView(R.id.tv_personal_info_collect_completed)
    TextView tvPersonalInfoCollectCompleted;
    @BindView(R.id.tv_personal_info_collect_completion)
    TextView tvPersonalInfoCollectCompletion;
    @BindView(R.id.list_personal_info)
    RecyclerView listPersonalInfo;
    @BindView(R.id.img_personal_info_back)
    ImageView imgPersonalInfoBack;
    @BindView(R.id.img_personal_info_scan)
    ImageView imgPersonalInfoScan;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info_collect;
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

    @OnClick({R.id.img_personal_info_back, R.id.img_personal_info_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_personal_info_back:
                getActivity().finish();
                break;
            case R.id.img_personal_info_scan:
                // TODO 扫一扫
                break;
        }
    }
}
