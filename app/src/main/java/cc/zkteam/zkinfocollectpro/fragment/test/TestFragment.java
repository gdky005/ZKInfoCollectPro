package cc.zkteam.zkinfocollectpro.fragment.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.fragment.test.mvp.TestPresenterImpl;
import cc.zkteam.zkinfocollectpro.fragment.test.mvp.TestView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * TestFragment
 * Created by WangQing on 2017/12/15.
 */

public class TestFragment extends BaseFragment implements HasSupportFragmentInjector, TestView {

    public static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    @Inject
    TestPresenterImpl presenter;

    @BindView(R.id.textView)
    TextView textView;

    public static TestFragment newInstance(String text) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void initView(View rootView) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String text = bundle.getString(ARG_SECTION_NUMBER);
            textView.setText(text);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        // 使用了 Inject 就不需要使用这个了
//        presenter = new TestPresenterImpl(this);
        presenter.loadData();

    }

    @Override
    public void initListener() {

    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onNetFinished() {

    }

    @Override
    public void requestFinish() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }
}
