package cc.zkteam.zkinfocollectpro.fragment.problem.mvp;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.MapActivity;
import cc.zkteam.zkinfocollectpro.api.ZKApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.managers.ZKConnectionManager;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PRPresenterImpl extends BaseMVPPresenter<PRView, PRModule> implements PRPresenter {
    private ZKApi zkApi;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public PRPresenterImpl(PRView view) {
        this.mView = view;
        init();
    }

    private void init() {
        zkApi = ZKConnectionManager.getInstance().getZKApi();
    }

    @Override
    public void loadData() {
        getLocationInfo();
    }

    private void getLocationInfo() {
        mLocationClient = new LocationClient(ZKBase.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            StringBuilder sb = new StringBuilder();
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            sb.append(addr).append(country)
                    .append(province).append(city)
                    .append(district).append(street);
            mView.setLocationInfo(sb.toString());
        }
    }


}