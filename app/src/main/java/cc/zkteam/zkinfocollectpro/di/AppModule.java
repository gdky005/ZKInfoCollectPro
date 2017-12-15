package cc.zkteam.zkinfocollectpro.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cc.zkteam.zkinfocollectpro.activity.home.HomeActivityComponent;
import dagger.Module;
import dagger.Provides;

/**
 * 每添加一个 四大组件 都需要在此写上 对应的 Component
 * Created by WangQing on 2017/11/17.
 */
@Module(subcomponents = {
        HomeActivityComponent.class
})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
