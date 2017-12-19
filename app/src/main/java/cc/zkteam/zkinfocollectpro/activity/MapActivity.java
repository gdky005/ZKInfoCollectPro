package cc.zkteam.zkinfocollectpro.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MapActivity extends BaseActivity {
    private TextureMapView mMapView;
    private BaiduMap mBDMap;
    private MyLocationListener myListener = new MyLocationListener();
    private boolean mIsFirstLoc = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViews() {
        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initLocation();
    }

    private void initLocation() {
        LocationClient locationClient = new LocationClient(this);
        locationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);            //  打开gps
        option.setCoorType("bd09ll");       //  设置坐标类型
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        locationClient.start();
    }

    private void initMap(BDLocation location) {
        mBDMap = mMapView.getMap();
        // 开启定位图层
        mBDMap.setMyLocationEnabled(true);
        //  构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                //  此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        //  设置定位数据
        mBDMap.setMyLocationData(locData);
//        //  当不需要定位图层时关闭定位图层
//        mBDMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            initMap(location);

            if (mIsFirstLoc) {
                mIsFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBDMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
