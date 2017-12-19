package cc.zkteam.zkinfocollectpro.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/12/15.
 */

public class CommonUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void initViewColor(int color, View...a){
        for (int i = 0; i < a.length; i++) {
            a[0].setBackgroundColor(color);
        }
    }
}
