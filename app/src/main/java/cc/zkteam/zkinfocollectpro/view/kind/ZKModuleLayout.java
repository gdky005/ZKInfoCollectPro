package cc.zkteam.zkinfocollectpro.view.kind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ZKModuleLayout
 * Created by WangQing on 2017/12/29.
 */

public class ZKModuleLayout extends ZKBaseView implements IZKResult {
    public ZKModuleLayout(Context context) {
        super(context);
    }

    public ZKModuleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKModuleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(View rootView) {

    }
}
