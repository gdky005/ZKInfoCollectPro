package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;

/**
 * Created by loong on 2017/12/15.
 */

public class SingInFragment extends BaseFragment {

    @BindView(R.id.img_personal_info_back)
    ImageView imgPersonalInfoBack;
    @BindView(R.id.img_personal_info_scan)
    ImageView imgPersonalInfoScan;
    @BindView(R.id.toolbar_personal_info_collection)
    Toolbar toolbarPersonalInfoCollection;
    @BindView(R.id.img_title_text)
    ImageView imgTitleText;
    @BindView(R.id.img_sign_main)
    RelativeLayout imgSignMain;

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
}
