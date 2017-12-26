package cc.zkteam.zkinfocollectpro.bean;

import java.util.List;

/**
 * Created by WangQing on 2017/12/21.
 */

public class ZHCommunityBean {

    /**
     * status : 1
     * msg :
     * data : [{"id":"1","name":"城北社区"},{"id":"2","name":"葡萄井社区"},{"id":"3","name":"鹿鸣社区"},{"id":"4","name":"陵园社区"},{"id":"5","name":"青杠园社区"},{"id":"6","name":"东门社区"},{"id":"7","name":"火炉坎村"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * id : 1
         * name : 城北社区
         */



        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "{" +
                    "id:'" + id + '\'' +
                    ", name:'" + name + '\'' +
                    '}';
        }
    }
}
