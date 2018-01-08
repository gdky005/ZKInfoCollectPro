package cc.zkteam.zkinfocollectpro.fragment.problem.mvp;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PRPresenterImpl extends BaseMVPPresenter<PRView, PRModule> implements PRPresenter {
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private ZHApi zhApi;
    private Context mContext;

    public PRPresenterImpl(PRView view, Context context) {
        mContext = context;
        this.mView = view;
        init();
    }

    private void init() {
        zhApi = ZHConnectionManager.getInstance().getZHApi();
    }

    @Override
    public void loadData() {
        getLocationInfo();
    }

    public void report(String source, String typeStr, String desc, String location, String attachmentName, String suggestion, List<String> picPath) {
        Executors.newSingleThreadExecutor().execute(() -> {
            MultipartBody.Part number = MultipartBody.Part.createFormData("number", "wenti" + System.currentTimeMillis());
            MultipartBody.Part reporter = MultipartBody.Part.createFormData("reporter", ZKICApplication.zhLoginBean.getName());
            MultipartBody.Part problemposition = MultipartBody.Part.createFormData("problemposition", location);
            MultipartBody.Part problemcontent = MultipartBody.Part.createFormData("problemcontent", desc);
            MultipartBody.Part type = MultipartBody.Part.createFormData("type", typeStr);

            MultipartBody.Part remarks = MultipartBody.Part.createFormData("remarks", suggestion);

            MultipartBody.Part[] part = new MultipartBody.Part[picPath.size()];
            MultipartBody.Part[] fileType = new MultipartBody.Part[picPath.size()];

            List<File> afterFile = new ArrayList<>();
            Luban.with(mContext)
                    .load(picPath)                     //传人要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            //TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }
                        @Override
                        public void onSuccess(File file) {
                            //TODO 压缩成功后调用，返回压缩后的图片文件
                            Log.e("TAG", file.toString());
                            afterFile.add(file);
                            if (afterFile.size() == picPath.size()) {

                                for (int i = 0; i < afterFile.size(); i++) {
                                    RequestBody requestBody =
                                            RequestBody.create(MediaType.parse("image/png"), afterFile.get(i));

                                    //参数1 数组名，参数2 文件名。
                                    MultipartBody.Part photoPart =
                                            MultipartBody.Part.createFormData("path" + (i + 1), picPath.get(i), requestBody);
                                    part[i] = photoPart;
                                    String[] picPathDetail = picPath.get(i).split("[.]");
                                    String suffix = picPathDetail[picPathDetail.length - 1];
                                    Log.e("TAG", suffix);
                                    MultipartBody.Part fileSuffix = MultipartBody.Part.createFormData("filetype" + (i + 1), suffix);
                                    fileType[i] = fileSuffix;
                                }

                                zhApi.report(number,
                                        reporter,
                                        problemposition,
                                        problemcontent,
                                        remarks,
                                        type,
                                        part,
                                        fileType).enqueue(new Callback<ZHBaseBean>() {
                                    @Override
                                    public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                                        ZHBaseBean zhBaseBean = response.body();
                                        if (zhBaseBean != null) {
                                            Log.d("TAG", "onResponse: " + zhBaseBean.toString());
                                            if (zhBaseBean.getStatus() == 1) {
                                                ToastUtils.showShort("上报成功");
                                                mView.cleanInput();
                                            }
                                        }
                                        mView.hideLoading();
                                    }

                                    @Override
                                    public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                                        t.printStackTrace();
                                        mView.hideLoading();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            //TODO 当压缩过去出现问题时调用
                        }
                    }).launch();    //启动压缩
        });
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

            String addr = location.getAddrStr();    //获取详细地址信息
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            String city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息
            mView.setLocationInfo(addr);
        }
    }
}