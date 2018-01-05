package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.managers.ZHMemoryCacheManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleListLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NewBasicInfoFragment
 * Created by WangQing on 2018/1/2.
 */

public class New31InfoFragment extends BaseFragment {
    private static final String TAG = "New31InfoFragment";

    public static final String NEW_31_INFO_NAME_KEY = "name";
    public static final String NEW_31_INFO_PAGE_TYPE_KEY = "pageType";

    @BindView(R.id.new_31_zk_module_list_layout)
    ZKModuleListLayout new31ZkModuleListLayout;
    @BindView(R.id.new_31_commit)
    Button new31Commit;
    Unbinder unbinder;
    @BindView(R.id.new_31_ll)
    LinearLayout new31Ll;
    Unbinder unbinder1;

    private String titleName;
    private String pageType;

    private String userID;

    public static New31InfoFragment newInstance(String name, String pageType) {
        Bundle args = new Bundle();
        New31InfoFragment fragment = new New31InfoFragment();
        args.putString(NEW_31_INFO_NAME_KEY, name);
        args.putString(NEW_31_INFO_PAGE_TYPE_KEY, pageType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_31_basicinfo_fragment;
    }

    @Override
    public void initView(View rootView) {
        new31Ll.setVisibility(View.GONE);
        new31Commit.setVisibility(View.GONE);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleName = bundle.getString(NEW_31_INFO_NAME_KEY);
            pageType = bundle.getString(NEW_31_INFO_PAGE_TYPE_KEY);
        }

        // TODO: 2018/1/4 test
        if (TextUtils.isEmpty(pageType)) {
            pageType = "renyuanxinxi_type";
        }

        // TODO: 2018/1/4 test
        if (TextUtils.isEmpty(titleName)) {
            titleName = "人员信息";
        }

        TitleEvent titleEvent = new TitleEvent();
        titleEvent.title = titleName;
        EventBus.getDefault().post(titleEvent);

        userID = ZHMemoryCacheManager.getInstance().getUserId();

        // TODO: 2018/1/2 test 数据
        if (TextUtils.isEmpty(userID)) {
            userID = "2";
        }

        showZKModuleAPI(new31ZkModuleListLayout, pageType);
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.new_31_commit)
    public void onViewClicked() {
        JSONObject resultObj = new31ZkModuleListLayout.getResult();

        ToastUtils.showShort("提交接口数据信息：" + resultObj);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", userID);
            jsonObject.put("data", resultObj);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            ZHConnectionManager.getInstance().getZHApi().update31Data(body).enqueue(new Callback<ZHBaseBean>() {
                @Override
                public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                    Log.d(TAG, "onResponse: " + response);
                    if (response.body() != null && response.body().getStatus() == 1) {
                        ToastUtils.showShort("数据提交成功");
                    }
                }

                @Override
                public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showZKModuleAPI(ZKModuleListLayout zkModuleListLayout, String pageType) {
        ZHConnectionManager.getInstance().getZHApi().get31Data(pageType, userID).enqueue(new Callback<ZK31Bean>() {

            @Override
            public void onResponse(Call<ZK31Bean> call, Response<ZK31Bean> result) {
                ZK31Bean zk31Bean = result.body();

                if (zk31Bean == null) {
                    L.e("zk31Bean is null!");
                    return;
                }

                List<ZK31Bean.DataBeanX> dataBeanList = zk31Bean.getData();

                if (zkModuleListLayout != null) {
                    new31Commit.setVisibility(View.VISIBLE);
                    zkModuleListLayout.setDataBeanList(dataBeanList);
                } else {
                    ToastUtils.showShort("new31ZkModuleListLayout is null!");
                }

                new31Ll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ZK31Bean> call, Throwable throwable) {
                new31Ll.setVisibility(View.VISIBLE);
                L.e("onFailure: ", throwable);
                ToastUtils.showShort("onFailure: " + throwable.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
