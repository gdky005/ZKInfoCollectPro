package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.MarriageBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by loong on 2017/12/30.
 */

public class MarriageInfoCollectionFragment extends BaseFragment {
    @BindView(R.id.title_marriage_info)
    ZKTitleView titleMarriageInfo;
    @BindView(R.id.kind_marriage)
    ZKModuleLayout kindMarriage;

    private ZHApi zhApi;
    private String[] statusArray = new String[]{"初婚", "二婚"};


    @Override
    public int getLayoutId() {
        return R.layout.fragment_marriage_info_collection;
    }


    @Override
    public void initView(View rootView) {
        titleMarriageInfo.setCenterTVText("户籍信息填写");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        zhApi = ZHConnectionManager.getInstance().getZHApi();
        zhApi.getMarriageInfo("1").enqueue(new Callback<MarriageBean>() {
            @Override
            public void onResponse(Call<MarriageBean> call, Response<MarriageBean> response) {
                MarriageBean marriageBean = response.body();
                JSONArray jsonArray = new JSONArray();
                try {
                    JSONObject status = new JSONObject();
                    status.put(" 婚姻状况", "" + getMarraigeType(Integer.parseInt(marriageBean.getData().getType())));

                    JSONObject name = new JSONObject();
                    name.put("配偶姓名", marriageBean.getData().getName());

                    JSONObject date = new JSONObject();
                    date.put("登记日期", "");

                    JSONObject num = new JSONObject();
                    num.put("结(离)婚证", "");

                    JSONObject organ = new JSONObject();
                    organ.put("登记机关", "");

                    jsonArray.put(status);
                    jsonArray.put(name);
                    jsonArray.put(date);
                    jsonArray.put(num);
                    jsonArray.put(organ);


                    Map<Integer, Object> map = new HashMap<>();

                    List dataStatus = new ArrayList();
                    dataStatus.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
                    dataStatus.add(statusArray);

                    List dataDate = new ArrayList();
                    dataDate.add(ZKFiled.TYPE_FILED_FORM_TIME);

                    map.put(0, dataStatus);
                    map.put(2, dataDate);

                    kindMarriage.setJsonArray(jsonArray, map, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MarriageBean> call, Throwable t) {
                Toast.makeText(mContext, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getMarraigeType(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "初婚";
                break;
            case 2:
                result = "离异";
                break;
            case 3:
                result = "复婚";
                break;
            case 4:
                result = "丧偶";
                break;
            case 5:
                result = "再婚";
                break;
        }
        return result;
    }

    @Override
    public void initListener() {

    }
}
