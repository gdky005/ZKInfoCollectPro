package cc.zkteam.zkinfocollectpro.retrofit2;

import java.io.IOException;

import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZHCall
 * Created by WangQing on 2017/10/28.
 */
public interface ZHCall<K> extends Call<ZHBaseBean<K>> {
    @Override
    Response<ZHBaseBean<K>> execute() throws IOException;

    @Override
    void enqueue(Callback<ZHBaseBean<K>> callback);

    @Override
    boolean isExecuted();

    @Override
    void cancel();

    @Override
    boolean isCanceled();

    @Override
    Call<ZHBaseBean<K>> clone();

    @Override
    Request request();
}
