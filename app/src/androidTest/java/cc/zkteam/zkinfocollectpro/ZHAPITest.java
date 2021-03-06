package cc.zkteam.zkinfocollectpro;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.bean.ZHTongJiBean;
import cc.zkteam.zkinfocollectpro.bean.ZKSettingBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.retrofit2.ZHCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZHAPITest
 * Created by WangQing on 2017/12/21.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ZHAPITest {

    private static final String TAG = "ZHAPITest";

    ZHApi zhApi;
    Context context;
    CountDownLatch countDownLatch;

    @Before
    public void init() {
        countDownLatch = new CountDownLatch(1);
        context = InstrumentationRegistry.getTargetContext();
        zhApi = ZHConnectionManager.getInstance().getZHApi();
    }

    @Test
    public void testLogin() {
        zhApi.login("admin", "admin").enqueue(new ZHCallback<ZHLoginBean>() {
            @Override
            public void onResponse(ZHBaseBean<ZHLoginBean> baseBean, ZHLoginBean result) {
                if (result != null) {
                    Log.d(TAG, "onResponse: " + result.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTongJi() {
        zhApi.tongji("3").enqueue(new Callback<ZHTongJiBean>() {
            @Override
            public void onResponse(Call<ZHTongJiBean> call, Response<ZHTongJiBean> response) {
                ZHTongJiBean tongJiBean = response.body();
                if (tongJiBean != null) {
                    List<ZHTongJiBean.DataBean> dataBeanList = tongJiBean.getData();

                    if (dataBeanList != null) {
                        for (ZHTongJiBean.DataBean dataBean : dataBeanList) {
                            Log.d(TAG, "onResponse: " + dataBean.toString());
                        }
                    }

                    countDownLatch.countDown();
                }
            }

            @Override
            public void onFailure(Call<ZHTongJiBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSheQuLianDong() {
        zhApi.shequliandong("1", "1").enqueue(new Callback<ZHCommunityBean>() {
            @Override
            public void onResponse(Call<ZHCommunityBean> call, Response<ZHCommunityBean> response) {
                ZHCommunityBean zhCommunityBean = response.body();
                if (zhCommunityBean != null) {
                    List<ZHCommunityBean.DataBean> dataBeanList = zhCommunityBean.getData();

                    if (dataBeanList != null) {
                        for (ZHCommunityBean.DataBean dataBean : dataBeanList) {
                            Log.d(TAG, "onResponse: " + dataBean.toString());
                        }
                    }

                    countDownLatch.countDown();
                }
            }

            @Override
            public void onFailure(Call<ZHCommunityBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHouse() {
        zhApi.getHouses("1", "1", "1", "122", "kskss").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d(TAG, "onResponse: " + zhBaseBean.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSign() {
        zhApi.sign("22", "23", "66233.32432", "3322.004324", "kskss").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d(TAG, "onResponse: " + zhBaseBean.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddHouse() {
//        zhApi.addHouse("JD001", "SQ001", "XQ001", "LF001", "DY001", "LC001", "FJ001").enqueue(new Callback<ZHBaseBean>() {
//            @Override
//            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
//                ZHBaseBean zhBaseBean = response.body();
//                if (zhBaseBean != null) {
//                    Log.d(TAG, "onResponse: " + zhBaseBean.toString());
//                }
//                countDownLatch.countDown();
//            }
//
//            @Override
//            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
//                countDownLatch.countDown();
//            }
//        });
//
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    public void testAddPersonBaseInfo() {
        zhApi.addPersonBaseInfo("JD001", "SQ001", "XQ001", "LF001", "DY001", "LC001", "FJ001").enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d(TAG, "onResponse: " + zhBaseBean.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     @Test
    public void testReport() {
         MultipartBody.Part number = MultipartBody.Part.createFormData("number", "wenti" + System.currentTimeMillis());
         MultipartBody.Part reporter = MultipartBody.Part.createFormData("reporter", "小王");
         MultipartBody.Part problemposition = MultipartBody.Part.createFormData("problemposition", "陕西省西安市");
         MultipartBody.Part problemcontent = MultipartBody.Part.createFormData("problemcontent", "这是测试数据哦");
         MultipartBody.Part type = MultipartBody.Part.createFormData("type", "城市污染");
         MultipartBody.Part filetype = MultipartBody.Part.createFormData("filetype", "json");
         MultipartBody.Part remarks = MultipartBody.Part.createFormData("remarks", "只是备注而已");




         File file = new File(Environment.getExternalStorageDirectory()
                 .getAbsolutePath() + "/xiaotieie_id_card.png");
         RequestBody requestBody =
                 RequestBody.create(MediaType.parse("image/png"), file);

        //参数1 数组名，参数2 文件名。
         MultipartBody.Part photo1part =
                 MultipartBody.Part.createFormData("path", "xiaotieie_id_card.png", requestBody);


        zhApi.report(
                number,
                reporter,
                problemposition,
                problemcontent,
                type,
                filetype,
                remarks,
                photo1part).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d(TAG, "onResponse: " + zhBaseBean.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSetting() {
        // 可以传入 "POORTYPE", "LOWHOMETYPE"
        zhApi.setting(null).enqueue(new ZHCallback<ZKSettingBean>() {
            @Override
            public void onResponse(ZHBaseBean<ZKSettingBean> baseBean, ZKSettingBean result) {
                if (result != null) {
                    Log.d(TAG, "onResponse: " + result.toString());
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
