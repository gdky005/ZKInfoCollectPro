package cc.zkteam.zkinfocollectpro.view.kind;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ZKBaseView 自定义View 的基类
 * Created by WangQing on 2017/12/28.
 */

public abstract class ZKBaseView extends LinearLayout {

    public Activity activity;
    public Context context;
    public View rootView;

    public ZKBaseView(Context context) {
        super(context);
        init(context);
    }

    public ZKBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZKBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        if (context instanceof Activity)
            this.activity = (Activity) context;

        int layoutId = getLayoutId();

        if (0 != layoutId) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rootView = inflater.inflate(layoutId, null);
            initViews(rootView);

            addView(rootView);
        }
    }

    //获取资源ID
    protected abstract int getLayoutId();
    protected abstract void initViews(View rootView);

    @Nullable
    public final <T extends View> T findView(@IdRes int id) {
        return rootView.findViewById(id);
    }

    public void setViewText(TextView textView, String defaultText) {
        if (textView != null && defaultText != null
                && textView.getVisibility() == VISIBLE
                && !textView.getText().equals(defaultText)) {
            textView.setText(defaultText);
        }

        if (defaultText == null) {
            setVisibility(textView,false);
        }
    }

    public void setVisibility(View view, boolean isShow) {
        if (view != null) {
            view.setVisibility(isShow ? VISIBLE : GONE);
        }
    }
}
