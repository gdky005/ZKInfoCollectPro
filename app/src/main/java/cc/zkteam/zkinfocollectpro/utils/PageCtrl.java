package cc.zkteam.zkinfocollectpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cc.zkteam.zkinfocollectpro.ZKBase;

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
}
