package cc.zkteam.zkinfocollectpro.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.adapter.PersonalInfoListAdapter;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.base.RvListener;
import cc.zkteam.zkinfocollectpro.bean.CollectItemBean;
import cc.zkteam.zkinfocollectpro.bean.PersonalSimpleInfoBean;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;
import cc.zkteam.zkinfocollectpro.view.ZKRecyclerView;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人数据采集页面
 * Created by loong on 2017/12/16.
 */

public class PersonalInfoCollectFragment extends BaseFragment {

    public static String PERSON_ID = "personid";

    @BindView(R.id.title_personal_info_collect)
    ZKTitleView titlePersonalInfoCollect;
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
    @BindView(R.id.img_change_left)
    ImageView imgChangeLeft;
    @BindView(R.id.btn_modification_info)
    TextView btnModificationInfo;
    @BindView(R.id.img_change_right)
    ImageView imgChangeRight;
    @BindView(R.id.layout_change_collection_state)
    RelativeLayout layoutChangeCollectionState;
    @BindView(R.id.list_personal_info)
    ZKRecyclerView listPersonalInfo;
    @BindView(R.id.pb_collect_loading)
    ProgressBar mLoading;

    private boolean callEdit;
    private String mPersonid = "";
    private int mStatus;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null)
            mPersonid = args.getString(PERSON_ID);

        if (TextUtils.isEmpty(mPersonid)) mPersonid = "";
    }

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
        titlePersonalInfoCollect.rightIV.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        requestCollectionStatus();
        showLoading();
        zhApiInstance.getPersonalSimpleInfo(mPersonid).enqueue(new Callback<PersonalSimpleInfoBean>() {
            @Override
            public void onResponse(Call<PersonalSimpleInfoBean> call, Response<PersonalSimpleInfoBean> response) {
                hideLoading();
                if (null == imgPersonalAvatar) return;
                if (null == response.body() || 1 != (response.body().getStatus()) || null == response.body().getData()) {
                    Toast.makeText(mContext, "信息请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    return;
                }

                PersonalSimpleInfoBean.DataBean infoBean = response.body().getData();

                if (imgPersonalAvatar != null) {
                    if (TextUtils.isEmpty(infoBean.getImg())) {
                        imgPersonalAvatar.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        imgPersonalAvatar.setImageURI(infoBean.getImg());
                    }
                }

                setText(tvPersonalInfoCollectName, "姓名：" + infoBean.getName());
                setText(tvPersonalInfoCollectId, "身份证号：" + infoBean.getId_card());
                setText(tvPersonalInfoCollectProject, "性别：" + infoBean.getSex() + "    民族：" + infoBean.getNation());
                setText(tvPersonalInfoCollectCompleted, "采集项：" + infoBean.getCaijixiang_lei() + "  " + infoBean.getCaijixiang_xiang());
            }

            @Override
            public void onFailure(Call<PersonalSimpleInfoBean> call, Throwable t) {
                hideLoading();
                if (null == PersonalInfoCollectFragment.this) return;
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        zhApiInstance.getPersonalInfoList().enqueue(new Callback<CollectItemBean>() {
            @Override
            public void onResponse(Call<CollectItemBean> call, Response<CollectItemBean> response) {
                if (null == PersonalInfoCollectFragment.this || null == response.body() || null == listPersonalInfo)
                    return;

                if (null == response.body() || response.body().getStatus() != 1) {
                    Toast.makeText(mContext, "信息请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    return;
                }

                PersonalInfoListAdapter adapter = new PersonalInfoListAdapter(getActivity(), response.body().getData(), new RvListener() {
                    @Override
                    public void onItemClick(int id, int position) {
                        if (!callEdit) {
                            Toast.makeText(mContext, "当前状态不允许修改信息", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (null == response.body().getData() || response.body().getData().size() < position)
                            return;
                        PageCtrl.startNew31InfoActivity(getActivity(), response.body().getData().get(position).getName(), response.body().getData().get(position).getType());
                    }
                });

                listPersonalInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
                listPersonalInfo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CollectItemBean> call, Throwable t) {
                if (null == PersonalInfoCollectFragment.this) return;
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestCollectionStatus() {
        zhApiInstance.changeCollectionStatus(mPersonid, "getstatus", "memo").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                if (null == PersonalInfoCollectFragment.this || null == response.body() || null == layoutChangeCollectionState)
                    return;
                if (1 == (response.body().getStatus())) {
                    setText(tvPersonalInfoCollectCompletion, "采集状态：" + response.body().getMsg());
                    if (layoutChangeCollectionState != null) {
                        layoutChangeCollectionState.setVisibility(View.VISIBLE);
                    }
                    if (response.body().getType() == 3 || response.body().getType() == 4 || response.body().getType() == 2) {
                        callEdit = false;
                    } else {
                        callEdit = true;
                    }
                    mStatus = response.body().getType();
                } else {
                    Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                if (null == PersonalInfoCollectFragment.this) return;
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.layout_change_collection_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_change_collection_state:
                showStatusChange();
                break;
        }
    }

    private void showStatusChange() {
        String[] titles = getStatus();
        if (null == titles || titles.length == 0) {
            Toast.makeText(mContext, "当前状态不允许修改", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionPicker picker3 = new OptionPicker(getActivity(), titles);
        picker3.setCanceledOnTouchOutside(false);
        picker3.setDividerRatio(WheelView.DividerConfig.FILL);
        picker3.setShadowColor(Color.BLUE, 40);
        picker3.setSelectedIndex(0);
        picker3.setCycleDisable(true);
        picker3.setTextSize(20);
        picker3.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index1, String item) {
                String act = mStatus == 1 ? "finish" : "edit";

                zhApiInstance.changeCollectionStatus(mPersonid, act, "memo").enqueue(new Callback<ZHBaseBean>() {
                    @Override
                    public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                        if (null == PersonalInfoCollectFragment.this || null == response.body() || null == mContext) {
                            Toast.makeText(mContext, "请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (1 == (response.body().getStatus())) {
                            Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            requestCollectionStatus();
                        } else {
                            Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                        if (null == PersonalInfoCollectFragment.this) return;
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        picker3.show();
    }

    private String[] getStatus() {
        String[] result = null;
        switch (mStatus) {
            case 1:
                result = new String[]{"采集完成"};
                break;
            case 3:
                result = new String[]{"重新激活"};
                break;
        }
        return result;
    }

    public void showLoading() {
        if (null != mLoading)
            mLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (null != mLoading)
            mLoading.setVisibility(View.GONE);
    }
}
