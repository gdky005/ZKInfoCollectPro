package cc.zkteam.zkinfocollectpro;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;
import com.bugtags.library.Bugtags;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.dumpapp.plugins.HprofDumperPlugin;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.di.DaggerAppComponent;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
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

        //在这里初始化Bugtags
        Bugtags.start("f1a4f3ae0071b52e8138b926f4763075", this, Bugtags.BTGInvocationEventBubble);

        Stetho.initialize(Stetho.newInitializerBuilder(mContext)
                .enableDumpapp(new DumperPluginsProvider() {
                    @Override
                    public Iterable<DumperPlugin> get() {
                        return new Stetho.DefaultDumperPluginsBuilder(mContext)
                                .provide(new HprofDumperPlugin(mContext))
                                .finish();
                    }
                })
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mContext))
                .build());

        //添加 Stetho 的拦截器
        ZHConnectionManager.getInstance().getBuilder().addNetworkInterceptor(new StethoInterceptor());

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
