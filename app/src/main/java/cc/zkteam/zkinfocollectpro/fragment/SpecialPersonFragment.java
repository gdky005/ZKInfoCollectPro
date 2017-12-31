package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
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
import cc.zkteam.zkinfocollectpro.view.kind.ZKFormLayout;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueLayout;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;

/**
 * Created by loong on 2017/12/27.
 */

public class SpecialPersonFragment extends BaseFragment {

    @BindView(R.id.filed_special_info_name_sex)
    ZKKeyValueLayout filedSpecialInfoNameSex;
    @BindView(R.id.filed_id_num)
    ZKKeyValueFiledView filedIdNum;
    @BindView(R.id.filed_id_add)
    ZKKeyValueFiledView filedIdAdd;
    @BindView(R.id.edit_special_info)
    ZKModuleLayout editSpecialInfo;
    @BindView(R.id.kind_criminal)
    ZKKindTitle kindCriminal;
    @BindView(R.id.kind_narcotics)
    ZKKindTitle kindNarcotics;
    @BindView(R.id.kind_escape)
    ZKKindTitle kindEscape;
    @BindView(R.id.kind_illness)
    ZKKindTitle kindIllness;
    @BindView(R.id.title_special_person_info)
    ZKTitleView titleSpecialPersonInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_special_person_info;
    }

    @Override
    public void initView(View rootView) {
        titleSpecialPersonInfo.setCenterTVText("特殊人群信息填写");
        titleSpecialPersonInfo.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        JSONArray jaNameSex = new JSONArray();
        try {
            JSONObject jName = new JSONObject();
            jName.put("姓名", "小名");
            JSONObject jSex = new JSONObject();
            jSex.put("性别", "女");

            jaNameSex.put(jName);
            jaNameSex.put(jSex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        filedSpecialInfoNameSex.setJsonArray(jaNameSex);

        filedIdNum.setKeyValue("身份证号：", "110111111111111111");
        filedIdAdd.setKeyValue("身份证地址", "北京市东城区");

        JSONArray jaSpecialInfo = new JSONArray();
        try {
            JSONObject event = new JSONObject();
            event.put("涉及事件", "");
            jaSpecialInfo.put(event);
            JSONObject group = new JSONObject();
            group.put("设计组织", "");
            jaSpecialInfo.put(group);
            JSONObject time = new JSONObject();
            time.put("参与时间", "");
            jaSpecialInfo.put(time);
            JSONObject join = new JSONObject();
            join.put("参与形式", "");
            jaSpecialInfo.put(join);
            JSONObject punishment = new JSONObject();
            punishment.put("处罚形式", "");
            jaSpecialInfo.put(punishment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Object> map = new HashMap<>();

        List dataTime = new ArrayList();
        dataTime.add(ZKFiled.TYPE_FILED_FORM_TIME);
        map.put(2, dataTime);

        List title = new ArrayList();
        title.add("涉恐涉稳人员");
        title.add(ZKKindTitle.TYPE_SINGLE_SELECT);
        title.add(chooseList);

        editSpecialInfo.setJsonArray(jaSpecialInfo, map, title);

        kindCriminal.setConstant(ZKKindTitle.TYPE_SINGLE_SELECT);
        kindNarcotics.setConstant(ZKKindTitle.TYPE_SINGLE_SELECT);
        kindEscape.setConstant(ZKKindTitle.TYPE_SINGLE_SELECT);
        kindIllness.setConstant(ZKKindTitle.TYPE_SINGLE_SELECT);

        kindCriminal.setSingleSelectTitle("是否重大刑事犯罪前科人员", chooseList);
        kindNarcotics.setSingleSelectTitle("是否涉毒人员", chooseList);
        kindEscape.setSingleSelectTitle("是否涉逃人员", chooseList);
        kindIllness.setSingleSelectTitle("是否肇事肇祸精神病人", chooseList);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}
