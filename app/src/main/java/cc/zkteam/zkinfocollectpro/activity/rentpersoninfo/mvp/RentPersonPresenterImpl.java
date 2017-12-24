package cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DCPresenter;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcModel;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;

/**
 * TestPresenterImpl
 * Created by WangQing on 2017/12/15.
 */

public class RentPersonPresenterImpl extends BaseMVPPresenter<RentPersonView, DcModel> implements DCPresenter {

    ZHApi zkApi;
    public static final String TAG = "DcPresenterImpl";

    public RentPersonPresenterImpl(RentPersonView view) {
        this.mView = view;
        init();
    }

    private void init() {
        zkApi = ZHConnectionManager.getInstance().getZHApi();
    }

    @Override
    public void loadData() {
      List<RentInfo> infos = new ArrayList<>();
        RentInfo rentInfo1 = new RentInfo("序号", "姓名", "与户主关系", "更新", "操作");
        infos.add(rentInfo1);
        for (int i = 0; i < 5; i++) {
            RentInfo rentInfo = new RentInfo();
            rentInfo.name = "name" + i;
            rentInfo.num = i + "";
            rentInfo.relation = "relation" + i;
            rentInfo.update = "update" + i;
            rentInfo.operate = "operate" + i;
            infos.add(rentInfo);
        }
        mView.updata(infos);
    }
}
