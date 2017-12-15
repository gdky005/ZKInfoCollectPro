package cc.zkteam.zkinfocollectpro.activity.home;

import cc.zkteam.zkinfocollectpro.activity.MyBean;
import cc.zkteam.zkinfocollectpro.fragment.test.dagger2.TestFragmentComponent;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/12/15.
 */
@Module(subcomponents = {TestFragmentComponent.class})
public class HomeActivityModule {

    @Provides
    MyBean provideName() {
        return new MyBean();
    }
}
