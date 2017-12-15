package cc.zkteam.zkinfocollectpro.fragment.test.mvp;

import java.util.List;

import cc.zkteam.zkinfocollectpro.api.ZKApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.bean.CategoryBean;
import cc.zkteam.zkinfocollectpro.managers.ZKConnectionManager;
import cc.zkteam.zkinfocollectpro.retrofit2.ZKCallback;
import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * TestPresenterImpl
 * Created by WangQing on 2017/12/15.
 */

public class TestPresenterImpl extends BaseMVPPresenter<TestView, TestModule> implements TestPresenter {

    ZKApi zkApi;

    public TestPresenterImpl(TestView testView) {
        this.mView = testView;
        init();
    }

    private void init() {
        zkApi = ZKConnectionManager.getInstance().getZKApi();
    }

    @Override
    public void loadData() {
        L.d("loadData: Hello");

        zkApi.categoryData(20).enqueue(new ZKCallback<List<CategoryBean>>() {
            @Override
            public void onResponse(List<CategoryBean> result) {

                L.d("onResponse: " + result.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {

                L.d("onFailure: " + throwable.toString());
            }
        });



    }
}
