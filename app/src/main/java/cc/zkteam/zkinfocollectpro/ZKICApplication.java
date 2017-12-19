package cc.zkteam.zkinfocollectpro;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

import cc.zkteam.zkinfocollectpro.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * ZKICApplication
 * Created by WangQing on 2017/12/14.
 */

public class ZKICApplication extends DaggerApplication {

    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        ZKBase.init(this);
        //  百度地图
        SDKInitializer.initialize(getApplicationContext());
        Fresco.initialize(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
