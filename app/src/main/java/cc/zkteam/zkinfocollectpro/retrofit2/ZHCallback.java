package cc.zkteam.zkinfocollectpro.retrofit2;


import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZKCallback
 * Created by WangQing on 2017/10/28.
 */

public abstract class ZHCallback<T> implements Callback<ZHBaseBean<T>> {
    @Override
    public void onResponse(Call<ZHBaseBean<T>> call, Response<ZHBaseBean<T>> response) {
        ZHBaseBean<T> baseBean = response.body();

        if (baseBean != null) {
            T results = baseBean.getData();
            onResponse(results);
        } else {
            onFailure(new Throwable("baseBean is null!"));
        }
    }

    @Override
    public void onFailure(Call<ZHBaseBean<T>> call, Throwable throwable) {
        onFailure(throwable);
    }

    public abstract void onResponse(T result);

    public abstract void onFailure(Throwable throwable);
}
