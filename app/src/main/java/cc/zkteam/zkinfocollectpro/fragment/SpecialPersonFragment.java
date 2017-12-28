package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKEditFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledFormView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledLayoutView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;

/**
 * Created by loong on 2017/12/27.
 */

public class SpecialPersonFragment extends BaseFragment {

    @BindView(R.id.filed_special_info_name_sex)
    ZKFiledLayoutView filedSpecialInfoNameSex;
    @BindView(R.id.filed_id_num)
    ZKFiledView filedIdNum;
    @BindView(R.id.filed_id_add)
    ZKFiledView filedIdAdd;
    @BindView(R.id.kind_terror)
    ZKKindTitle kindTerror;
    @BindView(R.id.edit_special_info)
    ZKFiledFormView editSpecialInfo;
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        filedSpecialInfoNameSex.setJsonArray(jaNameSex);

        filedIdNum.setKeyValue("身份证号：", "110111111111111111");
        filedIdAdd.setKeyValue("身份证地址", "北京市东城区");
        kindTerror.setSingleSelectTitle("涉恐涉稳人员", "否");

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

        Map<Integer, Integer> map = new HashMap<>();
        map.put(2, ZKEditFiledView.TYPE_FILED_FORM_SELECT_DATA);
        editSpecialInfo.setJsonArray(jaSpecialInfo, map);

        kindCriminal.setSingleSelectTitle("是否重大刑事犯罪前科人员", "否");
        kindNarcotics.setSingleSelectTitle("是否涉毒人员", "否");
        kindEscape.setSingleSelectTitle("是否涉逃人员", "否");
        kindIllness.setSingleSelectTitle("是否肇事肇祸精神病人", "否");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}
