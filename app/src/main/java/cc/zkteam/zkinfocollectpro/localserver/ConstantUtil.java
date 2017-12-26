package cc.zkteam.zkinfocollectpro.localserver;

/**
 * 常量工具类
 *
 * Created by WangQing on 2016/10/16.
 */
public class ConstantUtil {

    /**
     * // 2017/3/31   暂时为 false
     * 是否使用新版本中的 localhost 方案
     */
    public static boolean isUsedLocalHost = false;

    /**
     * 更新数据接口
     */
    public static final String UPDATE_URL = "%sCommon/Hybrid?platform=android&client_v=%s&os_ver=%s";


    public static final String UTF8 = "utf-8";
    public static final String REGEX = "regex";

    public static final String TEMP = "/temp/";


    @SuppressWarnings("unused")
    public static final String CACHE_INFO_FILE = "/cache_info.json";

    public static final String UPDATE_FILE = "/update.json";


    public static final String JM_H5_CONTAINER = "JMH5Container";
    public static final String JM_PER_H5_CONTAINER_ZIP = "/per_h5_container.zip";
    /**
     * FE zip 文件的根目录
     */
    public static final String HOME_DIR = "/jumei/" + JM_H5_CONTAINER;

    @SuppressWarnings("unused")
    public static final String SEPARATOR_TAG = "defaultHost";


    //Hybrid 中相关从字段
    public static final String TEST_LOCAL_SERVER_VERSION_DIR = "/";
    public static final String TEST_LOCAL_SERVER_INDEX_HTML = "/index.html";
    public static final String TEXT_FILED_REFERER = "referer";
    public static final String TEXT_FILED_USER_AGENT = "user-agent";
    public static final String TEXT_FILED_USER_MAC = "Mac";
    public static final String TEXT_FILED_USER_PORT = ":10000";
    //  2017/3/7 V2
    public static final String TEMPLATE_FILE2 = "/template_v2.html";
    public static final String TEMPLATE_FILE = "/template.html";


}
