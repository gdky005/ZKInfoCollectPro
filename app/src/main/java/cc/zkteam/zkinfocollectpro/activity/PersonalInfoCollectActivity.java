package cc.zkteam.zkinfocollectpro.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.blankj.utilcode.util.FragmentUtils;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.fragment.New31InfoFragment;
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
        fragmentTransaction.replace(R.id.content_personal_info_collect, new PersonalInfoCollectFragment());
        fragmentTransaction.commit();
    }

    public void showFragment(String name, String type) {
        FragmentUtils.add(getSupportFragmentManager(), New31InfoFragment.newInstance(name, type), R.id.content_personal_info_collect);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
