package cc.zkteam.zkinfocollectpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cc.zkteam.zkinfocollectpro.activity.BasicInfoActivity;
import cc.zkteam.zkinfocollectpro.fragment.New31InfoFragment;

/**
 * PageCtrl
 * Created by WangQing on 2017/12/15.
 */

public class PageCtrl {

    public static void startActivity(Activity context, Class cls) {
        if (context == null || cls == null) return;
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class cls) {
        if (context == null || cls == null) return;
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class cls, Intent extra) {
        if (context == null || cls == null) return;
        Intent intent = new Intent(context, cls);
        intent.putExtras(extra);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 启动 新31项 页面
     * @param pageName  当前页面的名称：例如 人员信息
     * @param pageType  当前页面的类型： 例如 renyuanxinxi_type
     */
    public static void startNew31InfoActivity(Context context, String pageName, String pageType) {
        if (context == null) return;
        Intent intent = new Intent(context, BasicInfoActivity.class);
        intent.putExtra(New31InfoFragment.NEW_31_INFO_NAME_KEY, pageName);
        intent.putExtra(New31InfoFragment.NEW_31_INFO_PAGE_TYPE_KEY, pageType);
        context.startActivity(intent);
    }

}
