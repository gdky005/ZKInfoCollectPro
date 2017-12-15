package cc.zkteam.zkinfocollectpro.di;

import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.di.annotations.SingletonGlobal;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * AppComponent: Dagger2 的全局注册组件
 * Created by WangQing on 2017/11/17.
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        ZKModule.class
})
@SingletonGlobal
public interface AppComponent extends AndroidInjector<ZKICApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<ZKICApplication>{}
}
