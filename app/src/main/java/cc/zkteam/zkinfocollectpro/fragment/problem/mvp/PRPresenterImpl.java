package cc.zkteam.zkinfocollectpro.fragment.problem.mvp;

import cc.zkteam.zkinfocollectpro.api.ZKApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.managers.ZKConnectionManager;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PRPresenterImpl extends BaseMVPPresenter<PRView, PRModule> implements PRPresenter {
    private ZKApi zkApi;

    public PRPresenterImpl(PRView view) {
        this.mView = view;
        init();
    }

    private void init() {
        zkApi = ZKConnectionManager.getInstance().getZKApi();
    }

    @Override
    public void loadData() {

    }
}
