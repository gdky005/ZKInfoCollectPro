package cc.zkteam.zkinfocollectpro.fragment.problem.mvp;

import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPView;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface PRView extends BaseMVPView {

    /**
     * 位置设置
     * @param location
     */
    void setLocationInfo(String location);

    void cleanInput();

    void showLoading();

    void hideLoading();
}
