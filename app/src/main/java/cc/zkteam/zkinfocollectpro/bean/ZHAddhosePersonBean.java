package cc.zkteam.zkinfocollectpro.bean;

import java.io.Serializable;


public class ZHAddhosePersonBean implements Serializable {


    /**
     * status : 2
     * msg : 该人员已经绑定过房屋，确定要重新绑定吗？
     * data : {"p_id":"162","b_id":"1","h_id":"47","r_type":"5","relation":null}
     */

    public int status;
    public String msg;
    /**
     * p_id : 162
     * b_id : 1
     * h_id : 47
     * r_type : 5
     * relation : null
     */

    public DataEntity data;

    public static class DataEntity {
        public String p_id = "";
        public String b_id = "";
        public String h_id = "";
        public String r_type = "";
        public String relation = "";
    }

    @Override
    public String toString() {
        return "ZHAddhosePersonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
