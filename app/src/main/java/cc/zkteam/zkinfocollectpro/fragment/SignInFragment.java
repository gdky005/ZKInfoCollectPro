package cc.zkteam.zkinfocollectpro.fragment;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Config;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.CommonFragmentActivity;
import cc.zkteam.zkinfocollectpro.activity.LoginActivity;
import cc.zkteam.zkinfocollectpro.activity.MyProblemListActivity;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHTongJiBean;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragment;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;
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

    @BindView(R.id.img_sign_about)
    ImageView imgSignAbout;
    @BindView(R.id.img_sign_search)
    ImageView imgSignSearch;
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
    @BindView(R.id.tv_question_upload)
    TextView tvQuestionUpload;
    @BindView(R.id.layout_slide_menu)
    LinearLayout layoutSlideMenu;


    private ZHApi zhApi;

    public boolean isSetIsShow() {
        return setIsShow;
    }

    public void setSetIsShow(boolean setIsShow) {
        this.setIsShow = setIsShow;
    }

    public void hideSetPage() {
        layoutSlideMenu.setVisibility(View.GONE);
        setIsShow = false;
    }

    private boolean setIsShow;


    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
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
        tvAboutName.setText(TextUtils.isEmpty(ZKICApplication.zhLoginBean.getName()) ? "" : ZKICApplication.zhLoginBean.getName());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        zhApi.getSignStatus(ZKICApplication.zhLoginBean.getId()).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    if (zhBaseBean.getStatus() == 0 && tvSign != null) {
                        tvSign.setOnClickListener(null);
                        setText(tvSign, "已签到");
                    }
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
            }
        });

        zhApi.tongji(ZKICApplication.zhLoginBean.getId()).enqueue(new Callback<ZHTongJiBean>() {
            @Override
            public void onResponse(Call<ZHTongJiBean> call, Response<ZHTongJiBean> response) {
                ZHTongJiBean tongJiBean = response.body();
                if (tongJiBean != null) {
                    List<ZHTongJiBean.DataBean> dataBeanList = tongJiBean.getData();

                    if (dataBeanList != null) {
                        for (ZHTongJiBean.DataBean dataBean : dataBeanList) {
                            if (!TextUtils.isEmpty(dataBean.getTotal())) {
                                setText(tvNumAll, dataBean.getTotal());
                            } else if (!TextUtils.isEmpty(dataBean.getWancheng())) {
                                setText(tvNumComplete, "采集完成" + dataBean.getWancheng());
                                if (TextUtils.isEmpty(dataBean.getPercent())) continue;
                                setText(tvPercentComplete, dataBean.getPercent());
                            } else if (!TextUtils.isEmpty(dataBean.getCaiji())) {
                                setText(tvNumCurrent, "正在采集" + dataBean.getCaiji());
                                if (TextUtils.isEmpty(dataBean.getCaiji())) continue;
                                setText(tvPercentCurrent, dataBean.getPercent());
                            } else if (!TextUtils.isEmpty(dataBean.getWeicaiji())) {
                                setText(tvNumNone, "未完成" + dataBean.getWeicaiji());
                                if (TextUtils.isEmpty(dataBean.getPercent())) return;
                                setText(tvPercentNone, dataBean.getPercent());
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

    @OnClick({R.id.img_sign_about, R.id.tv_sign, R.id.tv_sign_success_submit,
            R.id.tv_about_message, R.id.tv_about_setting, R.id.tv_about_quit, R.id.tv_question_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_sign_about:
                layoutSlideMenu.setVisibility(View.VISIBLE);
                setIsShow = true;
                break;
            case R.id.tv_sign:
                requestLocation();
                break;
            case R.id.tv_sign_success_submit:
                layoutSignSuccess.setVisibility(View.GONE);
                break;
            case R.id.tv_about_message:
                break;
            case R.id.tv_about_setting:
                CommonFragmentActivity.startCommonFragmentActivity(getActivity(), ResetPwdFragment.class.getName());
                break;
            case R.id.tv_about_quit:
                if (getActivity() != null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                break;
            case R.id.tv_question_upload:
                PageCtrl.startActivity(getActivity(), MyProblemListActivity.class);
                break;
        }
    }

    private void requestLocation() {
        LocationClient mLocationClient = new LocationClient(ZKBase.getContext());
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                double lon = bdLocation.getLongitude();
                double lat = bdLocation.getLatitude();
                String location = bdLocation.getLocationDescribe();
                doSign(lon, lat, location);
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void doSign(double lon, double lat, String location) {
        zhApi.sign(ZKICApplication.zhLoginBean.getName(), ZKICApplication.zhLoginBean.getId(), "" + lon, "" + lat, "" + location).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
//                layoutSignSuccess.setVisibility(View.VISIBLE);
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null && !TextUtils.isEmpty(zhBaseBean.getMsg())) {
                    setText(tvSignSuccessMsg, zhBaseBean.getMsg());
                }

                ZKDialogFragment zkDialogFragment = ZKDialogFragmentHelper.showDialog(SignInFragment.this.getChildFragmentManager(),
                        "签到成功", zhBaseBean.getMsg(), "确定", "", false,
                        new ZKDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {

                            }
                        }, null);
                if (tvSign != null) {
                    tvSign.setOnClickListener(null);
                    tvSign.setText("已签到");
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                Toast.makeText(getActivity(), "签到失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
