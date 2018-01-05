package cc.zkteam.zkinfocollectpro.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.managers.ZKManager;

public class CommonFragmentActivity extends AppCompatActivity {

    public static String CLASS_NAME = "class_name";

    public static void startCommonFragmentActivity(Activity activity, String clazzName) {
        if (null == activity || TextUtils.isEmpty(clazzName)) return;
        Intent intent = new Intent(activity, CommonFragmentActivity.class);
        intent.putExtra(CLASS_NAME, clazzName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fragment);
        initStatus();
        String clazzName = getIntent().getStringExtra(CLASS_NAME);

        if (TextUtils.isEmpty(clazzName)) return;
        Fragment fragment;
        try {
            Class clazz = Class.forName(clazzName);
            fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (null == fragment) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.common_content, fragment);
        fragmentTransaction.commit();
    }

    CountDownTimer timer;

    private void initStatus() {
        if (!TextUtils.isEmpty(ZKManager.getInstance().getWatermarkText())) {
            TextView tv = new TextView(this);
            tv.setText(ZKManager.getInstance().getWatermarkText());
            tv.setTextSize(25);
            tv.setTextColor(getResources().getColor(R.color.red));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addContentView(tv, params);
        }

        if (!TextUtils.isEmpty(ZKManager.getInstance().getWarningText())) {
            Toast.makeText(this, ZKManager.getInstance().getWarningText(), Toast.LENGTH_SHORT).show();
        }

        if (ZKManager.getInstance().isAppState()) {
            timer = new CountDownTimer(1000 * 60, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(CommonFragmentActivity.this, "应用马上退出，付费完成可以享受完整功能 ！！！", Toast.LENGTH_SHORT).show();
                    getCurrentFocus().postDelayed(new Runnable() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
