package cc.zkteam.zkinfocollectpro.di;

import android.app.Activity;

import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * ActivityBuilder
 * 每次添加一个 Activity 都需要在此注册上
 *
 * Created by WangQing on 2017/11/17.
 */
@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> buildMainActivity(HomeActivityComponent.Builder builder);

}
