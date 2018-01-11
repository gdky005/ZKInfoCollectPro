package cc.zkteam.zkinfocollectpro.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BasicInfoItemBean;
import cc.zkteam.zkinfocollectpro.fragment.New31InfoFragment;
import cc.zkteam.zkinfocollectpro.fragment.TitleEvent;
import cc.zkteam.zkinfocollectpro.managers.ZKManager;
import cc.zkteam.zkinfocollectpro.utils.MapBean;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;

import static cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.DataCollectFragment.TYPE_FANG_WU_XIN_XI_TYPE;

public class BasicInfoActivity extends BaseActivity {

    private String titleName;
    private String pageType;
    private String personId;
    private MapBean mapBean;

    @BindView(R.id.title)
    ZKTitleView title;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Subscribe
    public void OnitemClick(BasicInfoItemBean basicItemClick) {
        drawerLayout.closeDrawer(Gravity.END);
        String itemType = basicItemClick.itemtype;
        String itemName = basicItemClick.itemname;
        showFragment(itemType, itemName);
    }

    @Subscribe
    public void onTitleEvent(TitleEvent event) {
        if (event != null) {
            setTitle(event.title);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basicnfo;
    }

    @Override
    protected void initViews() {
        if (ZKManager.getInstance().isNew31State()) {
            Toast.makeText(mContext, "" + ZKManager.getInstance().getWarningText(), Toast.LENGTH_SHORT).show();
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, 2000);
        }
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.END);
        title.setLeftIVSrc(R.drawable.icon_back);
        title.setRightIVSrc(R.mipmap.right_menu);

    }

    @Override
    protected void initListener() {
        title.rightIV.setOnClickListener(view -> drawerLayout.openDrawer(Gravity.END));
        title.leftIV.setOnClickListener(view -> finish());

    }

    public void setTitle(String titleName) {
        title.setCenterTVText(titleName);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            titleName = bundle.getString(New31InfoFragment.NEW_31_INFO_NAME_KEY);
            pageType = bundle.getString(New31InfoFragment.NEW_31_INFO_PAGE_TYPE_KEY);
            personId = bundle.getString(New31InfoFragment.NEW_31_INFO_PERSON_ID_KEY);
            mapBean = (MapBean) bundle.getSerializable(New31InfoFragment.NEW_31_INFO_MAP_BEAN_KEY);
        }

        if (TYPE_FANG_WU_XIN_XI_TYPE.equals(pageType)) {
            title.rightIV.setVisibility(View.INVISIBLE);
        }

        showFragment(pageType, titleName);
    }

    private void showFragment(String itemType, String itemName) {
        FragmentUtils.add(getSupportFragmentManager(), New31InfoFragment.newInstance(itemName, itemType, mapBean, personId), R.id.content_view);
    }

}
