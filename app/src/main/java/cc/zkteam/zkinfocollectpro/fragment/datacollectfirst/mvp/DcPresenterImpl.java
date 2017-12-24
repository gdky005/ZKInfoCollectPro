package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.HouseInfo;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;

/**
 * TestPresenterImpl
 * Created by WangQing on 2017/12/15.
 */

public class DcPresenterImpl extends BaseMVPPresenter<DcView, DcModel> implements DCPresenter {

    ZHApi zkApi;
    public static final String TAG = "DcPresenterImpl";

    public DcPresenterImpl(DcView view) {
        this.mView = view;
        init();
    }

    private void init() {
        zkApi = ZHConnectionManager.getInstance().getZHApi();
    }

    @Override
    public void loadData() {
        Log.i(TAG, "loadData: ");
        List<HouseInfo> houseInfos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            HouseInfo houseInfo = new HouseInfo(i, 5);
            houseInfos.add(houseInfo);
        }
        mView.updateRecycle(houseInfos);
    }
}
