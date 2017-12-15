package cc.zkteam.zkinfocollectpro.fragment.test.dagger2;

import cc.zkteam.zkinfocollectpro.fragment.test.TestFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WangQing on 2017/12/15.
 */
@Subcomponent(modules = {TestFragmentModule.class})
public interface TestFragmentComponent extends AndroidInjector<TestFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TestFragment>{}
}
