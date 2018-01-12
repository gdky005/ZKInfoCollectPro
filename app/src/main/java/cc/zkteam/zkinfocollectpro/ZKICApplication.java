package cc.zkteam.zkinfocollectpro;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;

import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * ZKICApplication
 * Created by WangQing on 2017/12/14.
 */

public class ZKICApplication extends DaggerApplication {

    Context mContext;
    public static ZHLoginBean zhLoginBean;
    public static HomeActivity homeActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 2017/12/24  其他的类初始化都在这个内部
        ZKBase.init(this);
        //  百度地图
        SDKInitializer.initialize(getApplicationContext());

        try {
            //            http://blog.csdn.net/qq_23179075/article/details/70314473?locationNum=5&fps=1
            // android 7.0系统解决拍照的问题
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                builder.detectFileUriExposure();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
