package cc.zkteam.zkinfocollectpro.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lmj on 2017/12/27.
 */

public class RentInfoParam implements Serializable{

    public RentInfoParam(Map<Integer, String> params) {
        this.params = params;
    }

    private Map<Integer,String> params;

    public Map<Integer, String> getParams() {
        return params;
    }

    public void setParams(Map<Integer, String> params) {
        this.params = params;
    }
}
