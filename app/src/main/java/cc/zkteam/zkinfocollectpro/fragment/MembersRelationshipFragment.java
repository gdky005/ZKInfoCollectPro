package cc.zkteam.zkinfocollectpro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueLayout;

/**
 * Created by loong on 2017/12/30.
 */

public class MembersRelationshipFragment extends BaseFragment {
    @BindView(R.id.title_relation)
    ZKTitleView titleRelation;
    @BindView(R.id.tv_relation_id_num_self)
    TextView tvRelationIdNumSelf;
    @BindView(R.id.filed_relation_self_info)
    ZKKeyValueLayout filedRelationSelfInfo;
    @BindView(R.id.filed_)
    ZKKeyValueLayout filed;
    @BindView(R.id.btn_relation_add)
    Button btnRelationAdd;
    @BindView(R.id.btn_relation_submit)
    Button btnRelationSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_members_relationship;
    }

    @Override
    public void initView(View rootView) {
        titleRelation.setCenterTVText("人员关系信息");
        titleRelation.rightTextTV.setVisibility(View.GONE);
        titleRelation.rightIV.setVisibility(View.GONE);
        titleRelation.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        JSONArray jaNameSex = new JSONArray();
        try {
            JSONObject jName = new JSONObject();
            jName.put("姓名", "小名");
            JSONObject jSex = new JSONObject();
            jSex.put("性别", "女");

            JSONObject jDate = new JSONObject();
            jDate.put("出生日期", "1989年09月05日");
            JSONObject jAge = new JSONObject();
            jAge.put(" 年龄", "28");

            jaNameSex.put(jName);
            jaNameSex.put(jAge);
            jaNameSex.put(jDate);
            jaNameSex.put(jSex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        filedRelationSelfInfo.setJsonArray(jaNameSex);

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.btn_relation_add, R.id.btn_relation_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_relation_add:
                break;
            case R.id.btn_relation_submit:
                break;
        }
    }
}
