package cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 31 项目通用的数据bean
 * Created by WangQing on 2018/1/1.
 */

public class ZK31Bean {

    /**
     * status : 1
     * data : [{"id":"1","name":"身份证","type":"1","list":"","data":[{"number":"1","type":"1","name":"身份证地址","default":"","default_time":"","first_time":"","end_time":""},{"number":"2","type":"1","name":"签发机关","default":"","default_time":"","first_time":"","end_time":""},{"number":"3","type":"6","name":"有效期","default":"","default_time":"","first_time":"","end_time":""},{"number":"4","type":"7","name":"照片上传","default":"","default_time":"","first_time":"","end_time":""}]},{"id":"2","name":"社保卡信息","type":"1","list":"","data":[{"number":"1","type":"1","name":"社保卡卡号","default":"","default_time":"","first_time":"","end_time":""},{"number":"2","type":"3","name":"有效期","default":"","default_time":"","first_time":"","end_time":""},{"number":"3","type":"1","name":"社保银行卡卡号","default":"","default_time":"","first_time":"","end_time":""},{"number":"4","type":"4","name":"社保卡照片","default":"","default_time":"","first_time":"","end_time":""}]},{"id":"3","name":"准生证信息","type":"1","list":"","data":[{"number":"1","type":"1","name":"准生证号","default":"","default_time":"","first_time":"","end_time":""},{"number":"2","type":"3","name":"发证日期","default":"","default_time":"","first_time":"","end_time":""},{"number":"3","type":"1","name":"发证机构","default":"","default_time":"","first_time":"","end_time":""},{"number":"4","type":"1","name":"备注","default":"","default_time":"","first_time":"","end_time":""},{"number":"5","type":"4","name":"准生证图片","default":"","default_time":"","first_time":"","end_time":""}]}]
     */

    private int status;
    private List<DataBeanX> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * id : 1
         * name : 身份证
         * type : 1
         * list :
         * data : [{"number":"1","type":"1","name":"身份证地址","default":"","default_time":"","first_time":"","end_time":""},{"number":"2","type":"1","name":"签发机关","default":"","default_time":"","first_time":"","end_time":""},{"number":"3","type":"6","name":"有效期","default":"","default_time":"","first_time":"","end_time":""},{"number":"4","type":"7","name":"照片上传","default":"","default_time":"","first_time":"","end_time":""}]
         */

        private String id;
        private String name;
        private String type;
        private String list;
        private List<DataBean> data;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getList() {
            return list;
        }

        public void setList(String list) {
            this.list = list;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * number : 1
             * type : 1
             * name : 身份证地址
             * default :
             * default_time :
             * first_time :
             * end_time :
             */

            private String number;
            private String type;
            private String name;
            @SerializedName("default")
            private String defaultX;
            private String default_time;
            private String first_time;
            private String end_time;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDefaultX() {
                return defaultX;
            }

            public void setDefaultX(String defaultX) {
                this.defaultX = defaultX;
            }

            public String getDefault_time() {
                return default_time;
            }

            public void setDefault_time(String default_time) {
                this.default_time = default_time;
            }

            public String getFirst_time() {
                return first_time;
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }
        }
    }
}
