package cc.zkteam.zkinfocollectpro.fragment.test.mvp;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * TestPresenterImpl
 * Created by WangQing on 2017/12/15.
 */

public class TestPresenterImpl extends BaseMVPPresenter<TestView, TestModule> implements TestPresenter {

    ZHApi zkApi;

    public TestPresenterImpl(TestView testView) {
        this.mView = testView;
        init();
    }

    private void init() {
        zkApi = ZHConnectionManager.getInstance().getZHApi();
    }

    @Override
    public void loadData() {
        L.d("loadData: Hello");

//        zkApi.categoryData(20).enqueue(new ZHCallback<List<ZKTestCategoryBean>>() {
//            @Override
//            public void onResponse(List<ZKTestCategoryBean> result) {
//
//                L.d("onResponse: " + result.toString());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//
//                L.d("onFailure: " + throwable.toString());
//            }
//        });



    }
}
