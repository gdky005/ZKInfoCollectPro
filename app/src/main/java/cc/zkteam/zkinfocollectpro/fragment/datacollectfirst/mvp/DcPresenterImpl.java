package cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp;

import android.support.annotation.NonNull;
import android.util.Log;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import okhttp3.MultipartBody;
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
        loadStreetCommunity("1", "0");
    }

    /**
     * 加载街道
     * @param id
     * @param type
     */
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
                mView.hideLoading();
            }

            @Override
            public void onFailure(@NonNull Call<ZHCommunityBean> call, @NonNull Throwable t) {
                mView.hideLoading();
            }
        });
    }




    public void getUnitInfos(String id, String typ){

    }

    public void loadRentInfo(MultipartBody.Part roadid, MultipartBody.Part cummunityId,
                             MultipartBody.Part sqid, MultipartBody.Part hsid,
                             MultipartBody.Part unitid, MultipartBody.Part floorid,
                             MultipartBody.Part roomid){
        zkApi.addHouse(roadid, cummunityId, sqid, hsid, unitid, floorid, roomid).enqueue(new Callback<RentPersoner>() {
            @Override
            public void onResponse(Call<RentPersoner> call, Response<RentPersoner> response) {
                RentPersoner data = response.body();
                if (data != null) {
//                    List<RentPersoner.PersonlistBean> personlist = data.getPersonlist();
                    mView.updata(data);
                }
                mView.hideLoading();
            }

            @Override
            public void onFailure(Call<RentPersoner> call, Throwable t) {
                mView.hideLoading();
            }
        });
    }
}
