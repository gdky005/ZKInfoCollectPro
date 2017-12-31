package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;

/**
 * Created by loong on 2017/12/30.
 */

public class CensusInfoCollectionFragment extends BaseFragment {
    @BindView(R.id.title_census_info)
    ZKTitleView titleCensusInfo;
    @BindView(R.id.zk_census_num_type_date_reason_add)
    ZKModuleLayout zkCensusNumTypeDateReasonAdd;
    @BindView(R.id.zk_census_members_info)
    ZKModuleLayout zkCensusMembersInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_census_info_collection;
    }

    @Override
    public void initView(View rootView) {
        titleCensusInfo.centerTextTV.setText("户籍信息填写");
        titleCensusInfo.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initBaseInfo();
        initMembers();
    }

    private void initBaseInfo() {
        JSONArray jaSpecialInfo = new JSONArray();
        try {
            JSONObject event = new JSONObject();
            event.put("户口编号", "");
            jaSpecialInfo.put(event);
            JSONObject group = new JSONObject();
            group.put("户别", "");
            jaSpecialInfo.put(group);
            JSONObject time = new JSONObject();
            time.put("登记时间", "");
            jaSpecialInfo.put(time);
            JSONObject join = new JSONObject();
            join.put("由何地迁入本市", "");
            jaSpecialInfo.put(join);
            JSONObject punishment = new JSONObject();
            punishment.put("由何地迁入本址", "");
            jaSpecialInfo.put(punishment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Object> map = new HashMap<>();

        List dataNum = new ArrayList();
        dataNum.add(ZKKindTitle.TYPE_BUTTON);

        List dataType = new ArrayList();
        dataType.add(ZKKindTitle.TYPE_SINGLE_SELECT);
        dataType.add(new String[]{"集体户", "个体户"});

        List dataTime = new ArrayList();
        dataTime.add(ZKFiled.TYPE_FILED_FORM_TIME);
        dataTime.add(chooseList);

//        map.put(0, dataNum);
        map.put(1, dataType);
        map.put(2, dataTime);

        List title = new ArrayList();
        title.add("户主信息");
        title.add(ZKKindTitle.TYPE_NONE);

        zkCensusNumTypeDateReasonAdd.setJsonArray(jaSpecialInfo, map, title);
    }

    private void initMembers() {
        JSONArray jaSpecialInfo = new JSONArray();
        try {
            JSONObject event = new JSONObject();
            event.put("成员身份证号", "");
            jaSpecialInfo.put(event);
            JSONObject group = new JSONObject();
            group.put("成员姓名", "");
            jaSpecialInfo.put(group);
            JSONObject time = new JSONObject();
            time.put("登记时间", "");
            jaSpecialInfo.put(time);
            JSONObject join = new JSONObject();
            join.put("由何地迁入本市", "");
            jaSpecialInfo.put(join);
            JSONObject punishment = new JSONObject();
            punishment.put("承办人", "");
            jaSpecialInfo.put(punishment);
            JSONObject situation = new JSONObject();
            situation.put(" 户口情况", "");
            jaSpecialInfo.put(situation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Object> map = new HashMap<>();

        List dataTime = new ArrayList();
        dataTime.add(ZKFiled.TYPE_FILED_FORM_TIME);
        dataTime.add(chooseList);

        List dataType = new ArrayList();
        dataType.add(ZKKindTitle.TYPE_SINGLE_SELECT);
        dataType.add(new String[]{"无效(已迁出)", "有效"});

        map.put(2, dataTime);
        map.put(5, dataType);

        List title = new ArrayList();
        title.add("添加成员信息");
        title.add(ZKKindTitle.TYPE_NONE);


        zkCensusMembersInfo.setJsonArray(jaSpecialInfo, map, title);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
    }

    @OnClick(R.id.btn_save_submit)
    public void onViewClicked() {
    }
}
