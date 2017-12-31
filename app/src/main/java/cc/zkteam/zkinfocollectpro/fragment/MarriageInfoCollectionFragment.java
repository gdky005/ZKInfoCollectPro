package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;

/**
 * Created by loong on 2017/12/30.
 */

public class MarriageInfoCollectionFragment extends BaseFragment {
    @BindView(R.id.title_marriage_info)
    ZKTitleView titleMarriageInfo;
    @BindView(R.id.kind_marriage)
    ZKModuleLayout kindMarriage;

    private String[] statusArray = new String[]{"初婚", "二婚"};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_marriage_info_collection;
    }


    @Override
    public void initView(View rootView) {
        titleMarriageInfo.setCenterTVText("户籍信息填写");

        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject status = new JSONObject();
            status.put(" 婚姻状况", "");

            JSONObject name = new JSONObject();
            name.put("配偶姓名", "");

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
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}
