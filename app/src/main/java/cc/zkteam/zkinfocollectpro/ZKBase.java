package cc.zkteam.zkinfocollectpro;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.util.List;

import cc.zkteam.zkinfocollectpro.bd.ZKBDIDCardManager;
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
        ZKBDIDCardManager.getInstance().autoRefreshToken();
    }

    public static void init(@NonNull Context context, boolean debug) {
        ZKBase.context = context.getApplicationContext();
        ZKBase.isDebug = debug;
        init((Application) context);
    }

    /**
     * 获取 SD 卡的路径，如果获取失败，就获取内置路径
     */
    public static String initSDCardPath() {
        if (SDCardUtils.isSDCardEnable()) {
            if (SDCardUtils.getSDCardPaths(true).isEmpty()) {
                sdCardPath = getSdDirPathForList(SDCardUtils.getSDCardPaths(false));
            } else {
                sdCardPath = getSdDirPathForList(SDCardUtils.getSDCardPaths(true));
            }
        }

        if (TextUtils.isEmpty(sdCardPath)) {
            //获取 app 私有目录
            File file = ZKBase.getContext().getFilesDir();
            if (file != null) {
                sdCardPath = file.getPath();
            }
        }

        sdCardPath = sdCardPath + File.separator + BASE_DIR;
        FileUtils.createOrExistsDir(sdCardPath);

//        ToastUtils.showLong("当前获取的存储路径是：" + sdCardPath);
        L.d("当前获取的存储路径是：" + sdCardPath);
        return sdCardPath;
    }

    /**
     * 检测 SD 卡的一组数据是否有可用的
     * @return 返回可用的路径
     */
    private static String getSdDirPathForList(List<String> sdCardPaths) {
        String path = null;
        for (String sdCardPath : sdCardPaths) {
            String sdDirPath = checkSDPath(sdCardPath);
            if (!TextUtils.isEmpty(sdCardPath)) {
                path = sdDirPath;
                break;
            }
        }
        return path;
    }

    /**
     * 检测 SD 卡是否可用
     * @param sdCardDirPath  SD 卡的路径
     * @return  如可用返回 SD 卡的路径，否则返回 null。
     */
    private static String checkSDPath(String sdCardDirPath) {
        String tempFile = sdCardDirPath + "/temp";
        boolean b = FileUtils.createOrExistsFile(tempFile);
        if (b) {
            boolean deleteState = FileUtils.deleteFile(tempFile);
            if (deleteState)
                return sdCardDirPath;
        }
        return null;
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
