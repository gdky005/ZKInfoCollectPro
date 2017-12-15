package cc.zkteam.zkinfocollectpro.fragment.test.dagger2;

import cc.zkteam.zkinfocollectpro.fragment.test.TestFragment;
import cc.zkteam.zkinfocollectpro.fragment.test.mvp.TestPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.test.mvp.TestView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/12/15.
 */
@Module
public class TestFragmentModule {

    @Provides
    TestView provideTestView(TestFragment fragment) {
        return fragment;
    }

    @Provides
    TestPresenterImpl provideTestPresenterImpl(TestFragment fragment) {
        return new TestPresenterImpl(fragment);
    }
}
