package cc.zkteam.zkinfocollectpro.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.managers.ZKManager;
import cc.zkteam.zkinfocollectpro.view.ZKImageView;

/**
 * BaseFragment
 * Created by WangQing on 2017/11/7.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Unbinder unbinder;
    private CountDownTimer timer;
    //context
    protected Context mContext = null;

    public String[] chooseList = new String[]{"是", "否"};

    // 获取布局资源文件
    public abstract @LayoutRes
    int getLayoutId();

    // 初始化布局
    public abstract void initView(View rootView);

    // 初始化数据
    public abstract void initData(Bundle savedInstanceState);

    // 初始化相关 View 的 listener
    public abstract void initListener();

    protected ZHApi zhApiInstance;

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
        initStatus();
    }

    private void initStatus() {
        if (!TextUtils.isEmpty(ZKManager.getInstance().getWatermarkText())) {
            TextView tv = new TextView(getActivity());
            tv.setText(ZKManager.getInstance().getWatermarkText());
            tv.setTextSize(25);
            tv.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.END;
            ((ViewGroup) rootView).addView(tv, params);
        }

        if (!TextUtils.isEmpty(ZKManager.getInstance().getWarningText())) {
            Toast.makeText(mContext, ZKManager.getInstance().getWarningText(), Toast.LENGTH_SHORT).show();
        }

        if (ZKManager.getInstance().isAppState()) {
            timer = new CountDownTimer(1000 * 60, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(mContext, "应用马上退出，付费完成可以享受完整功能 ！！！", Toast.LENGTH_SHORT).show();
                    rootView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    }, 2000);
                    timer = null;
                }
            };
            timer.start();
        }
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
        zhApiInstance = ZHConnectionManager.getInstance().getZHApi();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
