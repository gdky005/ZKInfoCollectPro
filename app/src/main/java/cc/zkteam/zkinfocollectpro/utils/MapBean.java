package cc.zkteam.zkinfocollectpro.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * MapBean
 * Created by WangQing on 2018/1/6.
 */

public class MapBean implements Serializable {
    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
