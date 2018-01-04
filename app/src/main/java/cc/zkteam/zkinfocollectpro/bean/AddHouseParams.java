package cc.zkteam.zkinfocollectpro.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/4.
 */

public class AddHouseParams implements Serializable {

    private HashMap<Integer, String> params;

    public AddHouseParams(HashMap<Integer, String> params) {
        this.params = params;
    }

    public HashMap<Integer, String> getParams() {
        return params;
    }

    public void setParams(HashMap<Integer, String> params) {
        this.params = params;
    }
}
