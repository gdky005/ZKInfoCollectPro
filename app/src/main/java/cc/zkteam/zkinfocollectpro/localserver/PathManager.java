package cc.zkteam.zkinfocollectpro.localserver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import cc.zkteam.zkinfocollectpro.ZKBase;

/**
 * 路径管理
 * <p>
 * Created by WangQing on 2016/10/12.
 */
public class PathManager {

    /**
     * 本地更新文件的路径
     */
    private String updateFilePath = null;
    private Context context;
    private String cacheInfoFilePath = null;

    @SuppressLint("StaticFieldLeak")
    private static PathManager instance = null;

    private PathManager(Context context) {
        this.context = context;
    }

    public static PathManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PathManager.class) {
                PathManager temp = instance;
                if (temp == null) {
                    temp = new PathManager(context);
                    instance = temp;
                }
            }
        }
        return instance;
    }

    public String getLocalUpdateFilePath() {
        if (TextUtils.isEmpty(updateFilePath))
            updateFilePath = ZKBase.getSDCardPath();

        return updateFilePath;
    }

    public String getCacheInfoFilePath() {
        return cacheInfoFilePath;
    }

    public void setCacheInfoFilePath(String cacheInfoFilePath) {
        this.cacheInfoFilePath = cacheInfoFilePath;
    }

    public String getHomeDir() {
        return ZKBase.getSDCardPath();
    }
}
