package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFormLayout;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;

/**
 * Created by loong on 2017/12/29.
 */

public class PersonalBaseInfoFragment extends BaseFragment {
    @BindView(R.id.title_special_person_info)
    ZKTitleView titleSpecialPersonInfo;
    @BindView(R.id.edit_personal_base_avatar)
    ZKFormLayout editPersonalBaseInfo;
    Unbinder unbinder;
    @BindView(R.id.kind_personal_edit_sex)
    ZKKindTitle kindPersonalEditSex;
    @BindView(R.id.edit_personal_date)
    ZKFormLayout editPersonalDate;
    @BindView(R.id.kind_personal_base_is_new)
    ZKKindTitle kindPersonalBaseIsNew;
    @BindView(R.id.edit_personal_base_id_card)
    ZKFormLayout editPersonalBaseIdCard;
    @BindView(R.id.kind_personal_base_work_type)
    ZKKindTitle kindPersonalBaseWorkType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_base_info;
    }

    @Override
    public void initView(View rootView) {
        titleSpecialPersonInfo.centerTextTV.setText("基本信息填写");
        JSONArray arrayAvatar = new JSONArray();
        JSONArray arrayDate = new JSONArray();
        JSONArray arrayId = new JSONArray();
        try {
            JSONObject avatar = new JSONObject();
            avatar.put("照片上传", "");
            arrayAvatar.put(avatar);
            Map<Integer, Object> mapAvatar = new HashMap<>();
            mapAvatar.put(0, ZKFiled.TYPE_FILED_FORM_IMAGE);
            editPersonalBaseInfo.setJsonArray(arrayAvatar, mapAvatar);

            JSONObject date = new JSONObject();
            date.put("出生日期", "");
            arrayDate.put(date);
            Map<Integer, Object> mapDate = new HashMap<>();
            mapDate.put(0, ZKFiled.TYPE_FILED_FORM_TIME);
            editPersonalDate.setJsonArray(arrayDate, mapDate);

            JSONObject idNum = new JSONObject();
            idNum.put("证件号码", "");
            arrayId.put(idNum);
            Map<Integer, Object> mapNum = new HashMap<>();
            mapNum.put(0, ZKFiled.TYPE_FILED_FORM_EDIT_TEXT);
            editPersonalBaseIdCard.setJsonArray(arrayDate, mapNum);

            kindPersonalEditSex.setSingleSelectTitle("性别", "男");
            kindPersonalBaseIsNew.setSingleSelectTitle("是否新生儿", "否");
            kindPersonalBaseWorkType.setSingleSelectTitle(" 工作类型", "工作类型");

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
