package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.MainActivity;
import cc.zkteam.zkinfocollectpro.activity.MyProblemListActivity;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHTongJiBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by loong on 2017/12/15.
 */

public class SignInFragment extends BaseFragment {

    @BindView(R.id.img_personal_info_about)
    ImageView imgPersonalInfoAbout;
    @BindView(R.id.img_personal_info_search)
    ImageView imgPersonalInfoSearch;
    @BindView(R.id.toolbar_personal_info_collection)
    Toolbar toolbarPersonalInfoCollection;
    @BindView(R.id.img_title_text)
    ImageView imgTitleText;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.img_sign_main)
    RelativeLayout imgSignMain;
    @BindView(R.id.tv_num_all)
    TextView tvNumAll;
    @BindView(R.id.tv_num_current)
    TextView tvNumCurrent;
    @BindView(R.id.tv_percent_current)
    TextView tvPercentCurrent;
    @BindView(R.id.tv_num_complete)
    TextView tvNumComplete;
    @BindView(R.id.tv_percent_complete)
    TextView tvPercentComplete;
    @BindView(R.id.tv_num_none)
    TextView tvNumNone;
    @BindView(R.id.tv_percent_none)
    TextView tvPercentNone;
    @BindView(R.id.tv_sign_success_msg)
    TextView tvSignSuccessMsg;
    @BindView(R.id.tv_sign_success_submit)
    TextView tvSignSuccessSubmit;
    @BindView(R.id.layout_sign_success)
    LinearLayout layoutSignSuccess;
    @BindView(R.id.img_about_avatar)
    ZKImageView imgAboutAvatar;
    @BindView(R.id.tv_about_name)
    TextView tvAboutName;
    @BindView(R.id.tv_about_message)
    TextView tvAboutMessage;
    @BindView(R.id.tv_about_setting)
    TextView tvAboutSetting;
    @BindView(R.id.tv_about_quit)
    TextView tvAboutQuit;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.test_btn)
    Button testBtn;
    @BindView(R.id.tv_question_upload)
    TextView tvQuestionUpload;
    Unbinder unbinder;

    private ZHApi zhApi;


    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_slide;
    }

    @Override
    public void initView(View rootView) {
        zhApi = ZHConnectionManager.getInstance().getZHApi();

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        zhApi.getSignStatus("3").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    if (zhBaseBean.getStatus() == 0) {
                        tvSign.setOnClickListener(null);
                        tvSign.setText("已签到");
                    }
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
            }
        });

        zhApi.tongji("3").enqueue(new Callback<ZHTongJiBean>() {
            @Override
            public void onResponse(Call<ZHTongJiBean> call, Response<ZHTongJiBean> response) {
                ZHTongJiBean tongJiBean = response.body();
                if (tongJiBean != null) {
                    List<ZHTongJiBean.DataBean> dataBeanList = tongJiBean.getData();

                    if (dataBeanList != null) {
                        for (ZHTongJiBean.DataBean dataBean : dataBeanList) {
                            if (!TextUtils.isEmpty(dataBean.getTotal())) {
                                tvNumAll.setText(dataBean.getTotal());
                            } else if (!TextUtils.isEmpty(dataBean.getWancheng())) {
                                tvNumComplete.setText("采集完成" + dataBean.getWancheng());
                                if (TextUtils.isEmpty(dataBean.getPercent())) continue;
                                tvPercentComplete.setText(dataBean.getPercent());
                            } else if (!TextUtils.isEmpty(dataBean.getCaiji())) {
                                tvNumCurrent.setText("正在采集" + dataBean.getCaiji());
                                if (TextUtils.isEmpty(dataBean.getCaiji())) continue;
                                tvPercentCurrent.setText(dataBean.getPercent());
                            } else if (!TextUtils.isEmpty(dataBean.getWeicaiji())) {
                                tvNumNone.setText("未完成" + dataBean.getWeicaiji());
                                if (TextUtils.isEmpty(dataBean.getPercent())) return;
                                tvPercentNone.setText(dataBean.getPercent());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ZHTongJiBean> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.img_personal_info_about, R.id.img_personal_info_search, R.id.tv_sign, R.id.tv_sign_success_submit,
            R.id.tv_about_message, R.id.tv_about_setting, R.id.tv_about_quit, R.id.tv_question_upload, R.id.test_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_personal_info_about:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.img_personal_info_search:
                Toast.makeText(getContext(), "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_sign:
                doSign();
                break;
            case R.id.tv_sign_success_submit:
                layoutSignSuccess.setVisibility(View.GONE);
                break;
            case R.id.tv_about_message:
                break;
            case R.id.tv_about_setting:
                break;
            case R.id.tv_about_quit:
                break;
            case R.id.tv_question_upload:
                PageCtrl.startActivity(getActivity(), MyProblemListActivity.class);
                break;
            case R.id.test_btn:
                PageCtrl.startActivity(getActivity(), MainActivity.class);
                break;
        }
    }

    private void doSign() {
        zhApi.sign("22", "23", "66233.32432", "3322.004324", "kskss").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                layoutSignSuccess.setVisibility(View.VISIBLE);
                tvSign.setOnClickListener(null);
                tvSign.setText("已签到");
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null && !TextUtils.isEmpty(zhBaseBean.getMsg())) {
                    tvSignSuccessMsg.setText(zhBaseBean.getMsg());
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                Toast.makeText(getActivity(), "签到失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
