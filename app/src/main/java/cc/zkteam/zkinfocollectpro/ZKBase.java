package cc.zkteam.zkinfocollectpro;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cc.zkteam.zkinfocollectpro.exception.ZKBaseNullPointerException;
import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * JMBase 初始化相关
 * Created by WangQing on 2017/5/12.
 */
public final class ZKBase {

  @SuppressLint("StaticFieldLeak")
  private static Context context;
  public static boolean isDebug = BuildConfig.DEBUG;
  public static final String BASE_DIR = "ZHInfo";
  public static String sdCardPath;

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
      initSDCardPath();
  }

  public static void init(@NonNull Context context, boolean debug) {
    ZKBase.context = context.getApplicationContext();
    ZKBase.isDebug = debug;
    init((Application) context);
  }

    /**
     * 获取 SD 卡的路径，如果获取失败，就获取内置路径
     */
  public static void initSDCardPath() {
      String path;
      if (SDCardUtils.getSDCardPaths(true).isEmpty()) {
          path = SDCardUtils.getSDCardPaths(false).get(0);
      } else {
          path = SDCardUtils.getSDCardPaths(true).get(0);
      }

      sdCardPath = path + File.separator + BASE_DIR;
      FileUtils.createOrExistsDir(sdCardPath);

      L.d("当前获取的存储路径是：" + sdCardPath);
  }

  public static String getSDCardPath() {
      L.d("当前获取的存储路径是：" + sdCardPath);
      return sdCardPath;
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
