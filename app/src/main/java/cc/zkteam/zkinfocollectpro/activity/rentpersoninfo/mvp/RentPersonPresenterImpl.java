package cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.RentInfo;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DCPresenter;
import cc.zkteam.zkinfocollectpro.fragment.datacollectfirst.mvp.DcModel;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void loadRentInfo(MultipartBody.Part roadid, MultipartBody.Part cummunityId,
                             MultipartBody.Part sqid, MultipartBody.Part hsid,
                             MultipartBody.Part unitid, MultipartBody.Part floorid,
                             MultipartBody.Part roomid){
        zkApi.addHouse(roadid, cummunityId, sqid, hsid, unitid, floorid, roomid).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d("rentinfo", "onResponse: " + zhBaseBean.toString());
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {

            }
        });
    }
}
