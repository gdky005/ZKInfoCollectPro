package cc.zkteam.zkinfocollectpro.managers;

import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;

/**
 * ZHMemoryCacheManager
 * Created by WangQing on 2018/1/2.
 */

public class ZHMemoryCacheManager {

    private static ZHMemoryCacheManager instance = null;

    private ZHLoginBean loginBean;


    private ZHMemoryCacheManager() {
    }

    public static ZHMemoryCacheManager getInstance() {
        if (instance == null) {
            synchronized (ZHMemoryCacheManager.class) {
                ZHMemoryCacheManager temp = instance;
                if (temp == null) {
                    temp = new ZHMemoryCacheManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }

    public void setUserInfo(ZHLoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public ZHLoginBean getUserInfo() {
        return loginBean;
    }

    /**
     * 获取当前用户的 Id
     * @return  当前用户的 Id
     */
    public String getUserId() {
        if (loginBean != null) {
            return loginBean.getId();
        }
        return "";
    }
}
