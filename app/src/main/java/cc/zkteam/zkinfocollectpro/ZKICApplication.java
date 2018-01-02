package cc.zkteam.zkinfocollectpro;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bugtags.library.Bugtags;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.dumpapp.plugins.HprofDumperPlugin;
import com.facebook.stetho.okhttp3.StethoInterceptor;

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
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
