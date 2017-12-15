package cc.zkteam.zkinfocollectpro.activity.home;

import cc.zkteam.zkinfocollectpro.activity.MyBean;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/12/15.
 */
@Module
public class HomeActivityModule {

    @Provides
    MyBean provideName() {
        return new MyBean();
    }
}
