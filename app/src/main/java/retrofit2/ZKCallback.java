package retrofit2;


import cc.zkteam.zkinfocollectpro.bean.BaseBean;

/**
 * ZKCallback
 * Created by WangQing on 2017/10/28.
 */

public abstract class ZKCallback<T> implements Callback<BaseBean<T>> {
    @Override
    public void onResponse(Call<BaseBean<T>> call, Response<BaseBean<T>> response) {
        BaseBean<T> baseBean = response.body();

        if (baseBean != null) {
            T results = baseBean.getResult();
            onResponse(results);
        } else {
            onFailure(new Throwable("baseBean is null!"));
        }
    }

    @Override
    public void onFailure(Call<BaseBean<T>> call, Throwable throwable) {
        onFailure(throwable);
    }

    public abstract void onResponse(T result);

    public abstract void onFailure(Throwable throwable);
}
