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

    void updateRecycle(List<HouseInfo> mData);

    void loadSpinner(ZHCommunityBean zhCommunity, String type);

    void updata(RentPersoner data);

    void showLoading();

    void hideLoading();
}
