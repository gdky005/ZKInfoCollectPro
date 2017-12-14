package cc.zkteam.zkinfocollectpro;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * ZKICApplication
 * Created by WangQing on 2017/12/14.
 */

public class ZKICApplication extends Application {

    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        ZKBase.init(this);
    }
}
