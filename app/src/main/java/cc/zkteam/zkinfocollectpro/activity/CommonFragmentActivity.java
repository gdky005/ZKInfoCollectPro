package cc.zkteam.zkinfocollectpro.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import cc.zkteam.zkinfocollectpro.R;

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
}
