package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.BasicInfoActivity;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.managers.ZHMemoryCacheManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;
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

    @BindView(R.id.new_31_root_view_ll)
    LinearLayout new31RootViewLl;
    @BindView(R.id.new_31_commit)
    Button new31Commit;

    private String titleName;
    private String pageType;

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


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleName = bundle.getString(NEW_31_INFO_NAME_KEY);
            pageType = bundle.getString(NEW_31_INFO_PAGE_TYPE_KEY);
        }

        if (TextUtils.isEmpty(pageType)) {
//            pageType = "zhengzhaoxinxi_type";
            pageType = "renyuanxinxi_type";
        }

        if (TextUtils.isEmpty(titleName)) {
            titleName = "人员信息";
        }

        ((BasicInfoActivity) getActivity()).setTitle(titleName);

        showZKModuleAPI(new31RootViewLl, pageType);
    }

    @Override
    public void initListener() {

    }

    ZKModuleLayout zkModuleLayout;

    @OnClick(R.id.new_31_commit)
    public void onViewClicked() {
        JSONObject resultObj = zkModuleLayout.getResult();

        ToastUtils.showShort("提交接口数据信息：" + resultObj);

        try {
            String userID = ZHMemoryCacheManager.getInstance().getUserId();

            if (TextUtils.isEmpty(userID)) {
                userID = "2";
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", userID);
            jsonObject.put("data", resultObj);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            ZHConnectionManager.getInstance().getZHApi().update31Data(body).enqueue(new Callback<ZHBaseBean>() {
                @Override
                public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                    Log.d(TAG, "onResponse: " + response);
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

    private void showZKModuleAPI(LinearLayout linearLayout, String pageType) {
        ZHConnectionManager.getInstance().getZHApi().get31Data(pageType).enqueue(new Callback<ZK31Bean>() {

            @Override
            public void onResponse(Call<ZK31Bean> call, Response<ZK31Bean> result) {

                ZK31Bean zk31Bean = result.body();

                if (zk31Bean == null) {
                    L.e("zk31Bean is null!");
                    return;
                }




                List<ZK31Bean.DataBeanX> dataBeanList = zk31Bean.getData();

                for (ZK31Bean.DataBeanX dataBeanX :
                        dataBeanList) {

                    JSONArray moduleArray = new JSONArray();
                    Map<Integer, Object> objectHashMap = new HashMap<>();

                    zkModuleLayout = new ZKModuleLayout(mContext);

                    int index = 0;
                    for (ZK31Bean.DataBeanX.DataBean dataBean :
                            dataBeanX.getData()) {
                        ArrayList list = new ArrayList();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(dataBean.getNumber(), dataBean.getName());
                            jsonObject.put(ZKFiled.ZK_FILED_DATA_BEAN, dataBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        moduleArray.put(jsonObject);

                        int type = Integer.parseInt(dataBean.getType());

//                    二级子数据，每一个 item。map 表示 右边需要的数据：
//     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
//     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
//     *                  --------第0项表示 当前右边的类型 的 type;
//     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。


                        list.add(type);
                        list.add(dataBean.getName());
                        // TODO: 2018/1/2 test
                        try {
                            String str = dataBean.getDefault_list_data();
                            if (!TextUtils.isEmpty(str)) {
                                String[] strList = str.split(",");
                                list.add(strList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        objectHashMap.put(index, list);

                        index++;
                    }


                    List titleList = new ArrayList();

                    titleList.add(dataBeanX.getName());
                    titleList.add(dataBeanX.getType());
//                    titleList.add(dataBean.getList()); // 这里如果有值的话，就是列表 String[]
                    //                zkModuleLayout.setJsonArray(moduleArray, null, titleList);
                    zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);
                    linearLayout.addView(zkModuleLayout);
                }

            }


            @Override
            public void onFailure(Call<ZK31Bean> call, Throwable throwable) {

                L.e("onFailure: ", throwable);
            }
        });
    }
}
