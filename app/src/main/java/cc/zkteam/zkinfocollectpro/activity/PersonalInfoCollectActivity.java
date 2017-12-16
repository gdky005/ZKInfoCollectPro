package cc.zkteam.zkinfocollectpro.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.PersonalInfoCollectFragment;

public class PersonalInfoCollectActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_content;
    }

    @Override
    protected void initViews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_personal_info_collect,new PersonalInfoCollectFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
