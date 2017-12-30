package cc.zkteam.zkinfocollectpro.activity.familyPlanningInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;

/**
 * Created by gzw10 on 2017/12/29.
 */

public class ChildbearingAgeAndChildrenInfoActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_id_no)
    TextView mIdNo;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_sex)
    TextView mSex;
    @BindView(R.id.tv_birthday)
    TextView mBirthday;
    @BindView(R.id.tv_is_only_child)
    TextView mIsOnlyChild;
    @BindView(R.id.tv_time_to_get_married)
    TextView mTimeToGetMarried;
    @BindView(R.id.tv_number_of_children)
    TextView mNumberOfChildren;
    @BindView(R.id.zk_module_layout)
    ZKModuleLayout mModuleLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_childbearing_age_children_info;
    }

    @Override
    protected void initViews() {
        mToolbarTitle.setText("计生信息填写");
        initToolbar(mToolbar);

        initCA();
        initZK();
    }

    private void initZK() {
        JSONArray moduleArray = null;
        try {
            moduleArray = new JSONArray();

            for (int i = 0; i < 8; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("item" + i, "hello" + i);
                moduleArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] list = new String[]{"哈哈哈", "第一单位", "第二单位", "第三单位"};

        Map<Integer, Object> objectHashMap = new HashMap<>();

        List datas1 = new ArrayList();
        datas1.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
        datas1.add(list);
        objectHashMap.put(1, datas1);
        objectHashMap.put(3, ZKFiled.TYPE_FILED_FORM_TIME);

        List titleList = new ArrayList();
        titleList.add("我是小标题");
        titleList.add(ZKKindTitle.TYPE_BUTTON);
        titleList.add(list);

        mModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);
    }

    @SuppressLint("SetTextI18n")
    private void initCA() {
        mIdNo.setText("110101199304050023");
        mName.setText("落落");
        mSex.setText("女");
        mBirthday.setText("1989-09-06");
        mIsOnlyChild.setText("否");
        mTimeToGetMarried.setText("2009-09-06");
        mNumberOfChildren.setText("0");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
