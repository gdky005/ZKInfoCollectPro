package cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp;

import java.util.List;

import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPView;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;

/**
 * Created by WangQing on 2017/12/15.
 */

public interface RentPersonView extends BaseMVPView {
    void updata(List<RentInfo> data);

}
