package cc.zkteam.zkinfocollectpro.bd;

import cc.zkteam.zkinfocollectpro.exception.ZKSharePreferencesException;
import cc.zkteam.zkinfocollectpro.sharedpreferences.ZKSharedPreferences;

/**
 * ZKBDAccessTokenSP
 * Created by WangQing on 2017/12/24.
 */

public class ZKBDAccessTokenSP extends ZKSharedPreferences {

    private final String SP_NAME = "zk_bd_access_token";
    public static final String KEY_ACCESS_TOKEN = "key_access_token";
    public static final String KEY_EXPIRES_IN = "key_expires_in";

    @Override
    public String sharedPreferencesFileName() {
        return SP_NAME;
    }

    @Override
    public void put(String key, Object object) {
        try {
            super.put(key, object);
        } catch (ZKSharePreferencesException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String key, Object defaultObject) {
        try {
            return super.get(key, defaultObject);
        } catch (ZKSharePreferencesException e) {
            e.printStackTrace();
        }
        return null;
    }
}
