package cc.zkteam.zkinfocollectpro.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.LoginActivity;
import cc.zkteam.zkinfocollectpro.managers.ZKManager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * BaseActivity
 * <p>
 * Created by WangQing on 2017/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mTitle;
    private CountDownTimer timer;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ZKManager.getInstance().refresh();
        mContext = this;
        if (!(this instanceof LoginActivity)) {
            if (ZKICApplication.zhLoginBean == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }
        }

        initStatus();

        ButterKnife.bind(this);
        initViews();
        initListener();
        initData();
    }

    private void initStatus() {
        if (!TextUtils.isEmpty(ZKManager.getInstance().getWatermarkText())) {
            TextView tv = new TextView(this);
            tv.setText(ZKManager.getInstance().getWatermarkText());
            tv.setTextSize(25);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(0, getResources().getDisplayMetrics().heightPixels / 2, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addContentView(tv, params);
        }

        if (!TextUtils.isEmpty(ZKManager.getInstance().getWarningText())) {
            Toast.makeText(mContext, ZKManager.getInstance().getWarningText(), Toast.LENGTH_SHORT).show();
        }

        if (ZKManager.getInstance().isAppState()) {
            timer = new CountDownTimer(1000 * 60, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(mContext, "应用马上退出，付费完成可以享受完整功能 ！！！", Toast.LENGTH_SHORT).show();
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    }, 2000);
                    timer = null;
                }
            };
            timer.start();
        }
    }

    //获取资源ID
    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initListener();

    protected abstract void initData();

    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        //  设置了左上角的返回按钮
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void setVisibility(View view, boolean isShow) {
        if (view != null) {
            view.setVisibility(isShow ? VISIBLE : GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
