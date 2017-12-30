package cc.zkteam.zkinfocollectpro.managers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ZKConnectionManager ZKteam 的网络管理类
 * 单例使用教程: http://blog.csdn.net/lmj121212/article/details/68922401
 * Created by WangQing on 2017/10/28.
 */
public class ZHConnectionManager {

    private static final String TAG = "ZKConnectionManager";


    //http://blog.csdn.net/u014695188/article/details/52985514
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();


    private static ZHConnectionManager instance = null;

    private ZHConnectionManager() {
    }

    public static ZHConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ZHConnectionManager.class) {
                ZHConnectionManager temp = instance;
                if (temp == null) {
                    temp = new ZHConnectionManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }


    private Gson getGson() {
        return new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    private Retrofit getRetrofit() {
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        if (ZKBase.isDebug) {
            //log信息拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Debug Log模式
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(Constant.ZHI_HUI_DOMAIN_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public ZHApi getZHApi() {
        return getRetrofit().create(ZHApi.class);
    }

    public void test() {
        Log.d(TAG, "test() called");
    }

}
