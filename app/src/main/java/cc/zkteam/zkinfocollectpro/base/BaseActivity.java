package cc.zkteam.zkinfocollectpro.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.networkbench.agent.impl.NBSAppAgent;

import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;

/**
 * BaseActivity
 * <p>
 * Created by WangQing on 2017/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mTitle;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//
//        if (!(this instanceof LoginActivity)) {
//            if (ZKICApplication.zhLoginBean == null) {
//                startActivity(new Intent(this, LoginActivity.class));
//                finish();
//                return;
//            }
//        }


        // TODO: 2018/1/2  防商用标识
        TextView tv = new TextView(this);
        tv.setText("临时预览版本");
        tv.setTextSize(15);
        tv.setTextColor(getResources().getColor(R.color.red));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(tv, params);


        ButterKnife.bind(this);
        NBSAppAgent.leaveBreadcrumb(getClass().getSimpleName() + " onCreate");
        mContext = this;
        initViews();
        initListener();
        initData();
    }

    //获取资源ID
    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initListener();

    protected abstract void initData();

    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        //  设置了左上角的返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
        NBSAppAgent.leaveBreadcrumb(getClass().getSimpleName() + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
        NBSAppAgent.leaveBreadcrumb(getClass().getSimpleName() + " onPause");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }
}
