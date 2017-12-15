package cc.zkteam.zkinfocollectpro.fragment.test.mvp;

import android.util.Log;

import cc.zkteam.zkinfocollectpro.base.mvp.BaseMVPPresenter;

/**
 * TestPresenterImpl
 * Created by WangQing on 2017/12/15.
 */

public class TestPresenterImpl extends BaseMVPPresenter<TestView, TestModule> implements TestPresenter {

    private static final String TAG = "TestPresenterImpl";

    public TestPresenterImpl(TestView testView) {
        this.mView = testView;
    }

    @Override
    public void loadData() {
        Log.d(TAG, "loadData: Hello");

    }
}
