package cc.zkteam.zkinfocollectpro.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import com.blankj.utilcode.util.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BasicInfoItemBean;
import cc.zkteam.zkinfocollectpro.fragment.New31InfoFragment;
import cc.zkteam.zkinfocollectpro.fragment.TitleEvent;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;

public class BasicInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    ZKTitleView title;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Subscribe
    public void OnitemClick(BasicInfoItemBean basicItemClick) {
        drawerLayout.closeDrawer(Gravity.END);
        String itemType = basicItemClick.itemtype;
        String itemName = basicItemClick.itemname;
//        viewpager.setCurrentItem(basicItemClick.getI());
//        ToastUtils.showShort("-"+basicItemClick.getI());

        // TODO: 2018/1/3  启动 Fragment
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
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basicnfo;
    }

    @Override
    protected void initViews() {
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
        showFragment("renyuanxinxi_type", "人员信息");
    }

    private void showFragment(String itemType, String itemName) {
        FragmentUtils.add(getSupportFragmentManager(), New31InfoFragment.newInstance(itemName, itemType), R.id.content_view);
    }

}
