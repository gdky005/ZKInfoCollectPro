package cc.zkteam.zkinfocollectpro.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.BasicInfoActivity;
import cc.zkteam.zkinfocollectpro.activity.IDCardScanActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;
import cc.zkteam.zkinfocollectpro.view.ZKRecyclerView;

import static android.app.Activity.RESULT_OK;

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
    ZKRecyclerView listPersonalInfo;
    @BindView(R.id.img_personal_info_back)
    ImageView imgPersonalInfoBack;
    @BindView(R.id.img_personal_info_scan)
    ImageView imgPersonalInfoScan;
    @BindView(R.id.btn_test_detail)
    Button btnTestDetail;
    @BindView(R.id.img_change_left)
    ImageView imgChangeLeft;
    @BindView(R.id.img_change_right)
    ImageView imgChangeRight;
    Unbinder unbinder;

    private boolean isRight;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info_collect;
    }

    @Override
    public void initView(View rootView) {
        tvPersonalInfoCollectName.setText("姓名");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.img_personal_info_back, R.id.img_personal_info_scan, R.id.btn_modification_info,
            R.id.btn_test_detail, R.id.img_change_left, R.id.img_change_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_personal_info_back:
                getActivity().finish();
                break;
            case R.id.img_personal_info_scan:
                Intent intent = new Intent(getActivity(), IDCardScanActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_modification_info:

                break;
            case R.id.btn_test_detail:
                PageCtrl.startActivity(getActivity(), BasicInfoActivity.class);
                break;
            case R.id.img_change_left:
                if (isRight) {

                }
                break;
            case R.id.img_change_right:
                isRight = true;

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                if (data != null && requestCode == 100) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        BDIdCardBean.WordsResultBean wordsResultBean = (BDIdCardBean.WordsResultBean) bundle.getSerializable(IDCardScanActivity.KEY_ID_CARD_INFO_BEAN);

                        if (wordsResultBean != null) {
                            String name = wordsResultBean.getName().getWords();
                            L.i("扫描的姓名是；" + name);
                            ToastUtils.showShort("扫描的姓名是：" + name);
                        }
                    }
                }
                break;
        }
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
}
