package cc.zkteam.zkinfocollectpro;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bugtags.library.Bugtags;

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
        // 2017/12/24  其他的类初始化都在这个内部
        ZKBase.init(this);
        //  百度地图
        SDKInitializer.initialize(getApplicationContext());

        //在这里初始化Bugtags
        Bugtags.start("f1a4f3ae0071b52e8138b926f4763075", this, Bugtags.BTGInvocationEventBubble);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
