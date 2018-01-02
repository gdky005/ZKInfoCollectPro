package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
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
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NewBasicInfoFragment
 * Created by WangQing on 2018/1/2.
 */

public class New31InfoFragment extends BaseFragment {

    @BindView(R.id.new_31_root_view_ll)
    LinearLayout new31RootViewLl;
    @BindView(R.id.new_31_commit)
    Button new31Commit;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_31_basicinfo_fragment;
    }

    @Override
    public void initView(View rootView) {



    }

    @Override
    public void initData(Bundle savedInstanceState) {

        showZKModuleAPI(new31RootViewLl);
    }

    @Override
    public void initListener() {

    }


    @OnClick(R.id.new_31_commit)
    public void onViewClicked() {
        ToastUtils.showShort("提交接口数据信息");
    }

    private void showZKModuleAPI(LinearLayout linearLayout) {
//        String type = "zhengzhaoxinxi_type";
        String type = "renyuanxinxi_type";

        ZHConnectionManager.getInstance().getZHApi().get31Data(type).enqueue(new Callback<ZK31Bean>() {

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
                    ZKModuleLayout zkModuleLayout = new ZKModuleLayout(mContext);

                    for (ZK31Bean.DataBeanX.DataBean dataBean :
                            dataBeanX.getData()) {


                        ArrayList list = new ArrayList();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(dataBean.getNumber(), dataBean.getName());
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
                        list.add(new String[] {});

                        objectHashMap.put(0, list);


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
