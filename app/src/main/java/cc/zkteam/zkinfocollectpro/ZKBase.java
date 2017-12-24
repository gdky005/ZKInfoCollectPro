package cc.zkteam.zkinfocollectpro;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

import cc.zkteam.zkinfocollectpro.exception.ZKBaseNullPointerException;

/**
 * JMBase 初始化相关
 * Created by WangQing on 2017/5/12.
 */
public final class ZKBase {

  @SuppressLint("StaticFieldLeak")
  private static Context context;
  public static boolean isDebug = BuildConfig.DEBUG;

  private ZKBase() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 初始化工具类
   */
  public static void init(@NonNull Application app) {
      Fresco.initialize(app.getApplicationContext());
      ZKBase.context = app.getApplicationContext();
      Utils.init(app);
  }

  public static void init(@NonNull Context context, boolean debug) {
    ZKBase.context = context.getApplicationContext();
    ZKBase.isDebug = debug;
  }

  @CheckResult
  public static Context getContext() {
    if (context != null) {
      return context;
    }
    throw new ZKBaseNullPointerException("context is null!");
  }

  /**
   * 当前是否是  debug 的状态
   *
   * @return boolean
   */
  @CheckResult
  public static boolean isDebug() {
    return isDebug;
  }
}
