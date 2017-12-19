package cc.zkteam.zkinfocollectpro.fragment.test.dagger2;

import android.support.v4.app.Fragment;

import cc.zkteam.zkinfocollectpro.fragment.test.TestFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by WangQing on 2017/12/15.
 */
@Module
public abstract class TestFragmentProvider {
    @Binds
    @IntoMap
    @FragmentKey(TestFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerTestFragment(TestFragmentComponent.Builder builder);

}
