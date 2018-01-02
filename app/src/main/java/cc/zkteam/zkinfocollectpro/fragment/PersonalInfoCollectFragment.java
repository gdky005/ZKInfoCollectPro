package cc.zkteam.zkinfocollectpro.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.BasicInfoActivity;
import cc.zkteam.zkinfocollectpro.activity.CommonFragmentActivity;
import cc.zkteam.zkinfocollectpro.activity.IDCardScanActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKRecyclerView;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

import static android.app.Activity.RESULT_OK;

/**
 * 个人数据采集页面
 * Created by loong on 2017/12/16.
 */

public class PersonalInfoCollectFragment extends BaseFragment {
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
    @BindView(R.id.btn_test_detail)
    Button btnTestDetail;
    @BindView(R.id.img_change_left)
    ImageView imgChangeLeft;
    @BindView(R.id.img_change_right)
    ImageView imgChangeRight;
    @BindView(R.id.title_personal_info_collect)
    ZKTitleView titlePersonalInfoCollect;
    @BindView(R.id.btn_modification_info)
    TextView tvModificationInfo;

    private boolean isRight;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info_collect;
    }

    @Override
    public void initView(View rootView) {
        titlePersonalInfoCollect.setCenterTVText("数据采集");
        titlePersonalInfoCollect.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().finish();
            }
        });
        titlePersonalInfoCollect.rightIV.setImageResource(R.drawable.icon_scan);
        titlePersonalInfoCollect.rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "扫一扫", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.layout_change_collection_state,
            R.id.btn_test_detail, R.id.img_change_left, R.id.img_change_right, R.id.img_personal_info_item_edit_base,
            R.id.img_personal_info_item_edit_special, R.id.img_personal_info_item_marriage,
            R.id.img_personal_info_item_members, R.id.img_personal_info_item_relation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_change_collection_state:
                OptionPicker picker3 = new OptionPicker(getActivity(), new String[]{"采集完成", "重新激活"});
                picker3.setCanceledOnTouchOutside(false);
                picker3.setDividerRatio(WheelView.DividerConfig.FILL);
                picker3.setShadowColor(Color.BLUE, 40);
                picker3.setSelectedIndex(0);
                picker3.setCycleDisable(true);
                picker3.setTextSize(20);
                picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index1, String item) {
                        tvModificationInfo.setText(item);
                    }
                });
                picker3.show();
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
            case R.id.img_personal_info_item_edit_base:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), PersonalBaseInfoFragment.class.getName());
                break;
            case R.id.img_personal_info_item_edit_special:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), SpecialPersonFragment.class.getName());
                break;
            case R.id.img_personal_info_item_marriage:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), MarriageInfoCollectionFragment.class.getName());
                break;
            case R.id.img_personal_info_item_members:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), CensusInfoCollectionFragment.class.getName());
                break;
            case R.id.img_personal_info_item_relation:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), MembersRelationshipFragment.class.getName());
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
}
