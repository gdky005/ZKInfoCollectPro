package cc.zkteam.zkinfocollectpro.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.PersonalInfoCollectFragment;

public class PersonalInfoCollectActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info_collect;
    }

    @Override
    protected void initViews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new PersonalInfoCollectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PersonalInfoCollectFragment.PERSON_ID, null == getIntent() ? "" : getIntent().getStringExtra(PersonalInfoCollectFragment.PERSON_ID));
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_personal_info_collect, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
