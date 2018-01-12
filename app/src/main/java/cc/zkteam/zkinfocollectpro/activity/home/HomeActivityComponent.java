package cc.zkteam.zkinfocollectpro.activity.home;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WangQing on 2017/12/15.
 */
@Subcomponent(modules = {HomeActivityModule.class})
public interface HomeActivityComponent extends AndroidInjector<HomeActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<HomeActivity>{}
}
