package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp;

import android.support.annotation.NonNull;
import android.util.Log;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
      /*  List<HouseInfo> houseInfos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            HouseInfo houseInfo = new HouseInfo(i, 5);
            houseInfos.add(houseInfo);
        }
        mView.updateRecycle(houseInfos);*/

        loadStreetCommunity("1", "0");
    }

    public void loadStreetCommunity(String id, String type) {
        Log.e("TAG", "loadStreetCommunity " + id + "-" + type);
        zkApi.shequliandong(id, type).enqueue(new Callback<ZHCommunityBean>() {
            @Override
            public void onResponse(@NonNull Call<ZHCommunityBean> call, @NonNull Response<ZHCommunityBean> response) {
                if (response.isSuccessful()) {
                    ZHCommunityBean zhCommunityBean = response.body();
                    if (zhCommunityBean != null) {
                        mView.loadSpinner(zhCommunityBean, type);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ZHCommunityBean> call, @NonNull Throwable t) {

            }
        });
    }




    public void getUnitInfos(String id, String typ){

    }
}
