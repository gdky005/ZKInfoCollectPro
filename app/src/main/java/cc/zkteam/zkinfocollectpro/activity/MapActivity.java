package cc.zkteam.zkinfocollectpro.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MapActivity extends BaseActivity implements TextWatcher, SensorEventListener {
    public static final String NEW_LOCATION = "new_location";
    @BindView(R.id.et_search_in_map)
    AutoCompleteTextView mSearchInMap;
    @BindView(R.id.btn_select_location)
    Button mSelectLocationBtn;
    @BindView(R.id.mTexturemap)
    TextureMapView mMapView;
    private BaiduMap mBDMap;
    private MyLocationListener myListener = new MyLocationListener();
    private boolean mIsFirstLoc = true;
    private PoiSearch mPoiSearch;
    private PoiCitySearchOption mSearchOption;
    private List<String> mSearchLocationList = new ArrayList<>();
    private List<PoiInfo> mPoiInfo = new ArrayList<>();
    private GeoCoder mSearch;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private boolean mHintFlag = true;
    private LocationClient mLocClient;
    private PoiInfo mCurrPoiInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {
        mSearchInMap.addTextChangedListener(this);

        mSearchInMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHintFlag = false;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(mPoiInfo.get(position).location));
                mCurrPoiInfo = mPoiInfo.get(position);
            }
        });

        mSelectLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSelectLocationBtn.getText().toString())
                        || mCurrPoiInfo == null) {
                    ToastUtils.showShort("未选择合适的地理位置...");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(NEW_LOCATION, mCurrPoiInfo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        initLocation();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(listener);

        mBDMap = mMapView.getMap();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private void initLocation() {
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);            //  打开gps
        option.setCoorType("bd09ll");       //  设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 初始化 Map
     * @param location 位置信息
     */
    private void initMap(BDLocation location) {
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        // 开启定位图层
        mBDMap.setMyLocationEnabled(true);
        setLocationData(100);
        //  设置定位数据
        mBDMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
    }

    /**
     * 设置 setLocationData
     *
     * @param direction 方向
     */
    private void setLocationData(float direction) {
        //  构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                //  此处设置开发者获取到的方向信息，顺时针0-360
                .direction(direction).latitude(mCurrentLat)
                .longitude(mCurrentLon).build();
        mBDMap.setMyLocationData(locData);
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBDMap.setMyLocationEnabled(false);
        //  在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mPoiSearch.destroy();
        mSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPoiSearch.searchInCity(mSearchOption.keyword(s.toString()).pageNum(20));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        changeIconDirection(event);
    }

    /**
     * 设置图标方向
     * @param event 传感器事件
     */
    private void changeIconDirection(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            int currentDirection = (int) x;
            setLocationData(currentDirection);
            LatLng ll = new LatLng(mCurrentLat,
                    mCurrentLon);
            setMapZoom(ll);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
            if (mIsFirstLoc) {
                mIsFirstLoc = false;
                initMap(location);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                setMapZoom(ll);
                mSearchOption = new PoiCitySearchOption();
                mSearchOption.city(location.getCity());
                Log.e("Tag", location.getCity());
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 设置地图缩放等级
     *
     * @param ll 位置参数
     */
    private void setMapZoom(LatLng ll) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBDMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        public void onGetPoiResult(PoiResult result) {
            if (!mHintFlag) {
                mHintFlag = true;
                return;
            }
            if (result == null || result.getAllPoi() == null) {
                ToastUtils.showShort("未找到合适位置...");
                return;
            }
            changeSearchLocationList(result.getAllPoi());
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {
            if (!mHintFlag) {
                mHintFlag = true;
                return;
            }
            if (result == null) {
                ToastUtils.showShort("未找到合适位置...");
                return;
            }
            //获取Place详情页检索结果
            Log.e("Tag", result.getAddress());
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };

    private void changeSearchLocationList(List<PoiInfo> allPoi) {
        //获取POI检索结果
        mSearchLocationList.clear();
        mPoiInfo.clear();
        for (PoiInfo r : allPoi) {
            Log.e("Tag", r.address);
            if (!TextUtils.isEmpty(r.address)) {
                mSearchLocationList.add(r.address);
            }
        }
        if (mSearchLocationList.isEmpty()) {
            ToastUtils.showShort("未找到合适位置...");
            return;
        }
        mPoiInfo = allPoi;
        setLocationIntoAutoComplete();
    }

    /**
     * 设置提示数据
     */
    private void setLocationIntoAutoComplete() {
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(MapActivity.this, R.layout.item_problem_item, mSearchLocationList);
        mSearchInMap.setAdapter(mAdapter);
        mSearchInMap.showDropDown();
    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                return;
            }

            //获取地理编码结果
            Log.e("Tag", result.getAddress());
            reLocation(result);
        }

        @Override

        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                return;
            }

            //获取反向地理编码结果
            Log.e("Tag", result.getAddress());
            reLocation(result);
        }
    };

    private void reLocation(GeoCodeResult result) {
        mBDMap.clear();
        mBDMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding)));
        mBDMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
    }

    private void reLocation(ReverseGeoCodeResult result) {
        mBDMap.clear();
        mBDMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding)));
        mBDMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
    }
}
