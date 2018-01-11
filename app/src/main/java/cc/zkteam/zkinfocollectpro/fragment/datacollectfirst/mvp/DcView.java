package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp;

import java.util.List;

import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPView;
import cc.zkteam.zkinfocollectpro.bean.HouseInfo;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;

/**
 * Created by WangQing on 2017/12/15.
 */

public interface DcView extends BaseMVPView {

    /**
     * 更新视图
     * @param mData
     */
    void updateRecycle(List<HouseInfo> mData);

    /**
     * 下拉框加载
     * @param zhCommunity
     * @param type
     */
    void loadSpinner(ZHCommunityBean zhCommunity, String type);

    /**
     * 更新数据
     * @param data
     */
    void updata(RentPersoner data);

    void showLoading();

    void hideLoading();
}
