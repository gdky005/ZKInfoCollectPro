package cc.zkteam.zkinfocollectpro.mvppresenter;

import cc.zkteam.zkinfocollectpro.activity.PersonalInfoCollectActivity;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;

/**
 * Created by loong on 2017/12/15.
 */

public class PersonalInfoCollectPresenter extends BaseMVPPresenter {

    private PersonalInfoCollectActivity mView;

    public PersonalInfoCollectPresenter(PersonalInfoCollectActivity mView) {
        this.mView = mView;
    }
}
