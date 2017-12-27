package cc.zkteam.zkinfocollectpro.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;

/**
 * BaseFragment
 * Created by WangQing on 2017/11/7.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Unbinder unbinder;
    //context
    protected Context mContext = null;

    // 获取布局资源文件
    public abstract @LayoutRes
    int getLayoutId();

    // 初始化布局
    public abstract void initView(View rootView);

    // 初始化数据
    public abstract void initData(Bundle savedInstanceState);

    // 初始化相关 View 的 listener
    public abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setText(TextView textView, String str) {
        if (textView != null && null != str) {
            textView.setText(str);
        }
    }

}
