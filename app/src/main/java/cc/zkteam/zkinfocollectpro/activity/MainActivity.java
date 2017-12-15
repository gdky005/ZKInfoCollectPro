package cc.zkteam.zkinfocollectpro.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BDTokenBean;
import cc.zkteam.zkinfocollectpro.managers.ZKConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    String accessToken = "";
    int expires_in = 0; //单位是秒


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_btn)
    Button homeBtn;
    @BindView(R.id.bd_access_token)
    Button bdAccessToken;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar, R.id.home_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.home_btn:
                PageCtrl.startActivity(HomeActivity.class);
                break;
            case R.id.home_btn:
                PageCtrl.startActivity(MapActivity.class);
                break;
        }
    }

    @OnClick(R.id.bd_access_token)
    public void onViewClicked() {
//        参考地址：http://ai.baidu.com/docs#/Auth/top

        //{
        //  "access_token":"24.4aa184e7c8f9026c967660a101a648b5.2592000.1515926944.282335-10539172",
        //  "session_key":"9mzdD0Wia2Q1VKBPqL6oFEeWRAp2yQF8QFQSooQR45SvSUmmePah6WMuNAX9JggJj\/mvo29LDwAHhK31G3u\/eh7HZbKC4w==",
        //  "scope":"public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic brain_ocr_general_enhanced vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower bnstest_fasf lpq_\u5f00\u653e",
        //  "refresh_token":"25.0a9d6223afecb3f3e54a618823301f36.315360000.1828694944.282335-10539172",
        //  "session_secret":"8724c6186ba896bbe17cb4ad07579c75",
        //  "expires_in":2592000
        // }

        ZKConnectionManager.getInstance().getZKApi().bdAccessToken(Constant.BD_GRANT_TYPE,
                Constant.BD_API_KEY, Constant.BD_SECRET_KEY).enqueue(new Callback<BDTokenBean>() {
            @Override
            public void onResponse(Call<BDTokenBean> call, Response<BDTokenBean> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");

                BDTokenBean bdTokenBean = response.body();
                if (bdTokenBean != null) {
                    accessToken = bdTokenBean.getAccess_token();
                    expires_in = bdTokenBean.getExpires_in();

                    ToastUtils.showShort("获取 accessToken 成功，请继续下一步操作！");
                }

            }

            @Override
            public void onFailure(Call<BDTokenBean> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");

                ToastUtils.showShort("获取 Token 失败");

            }
        });
    }
}
