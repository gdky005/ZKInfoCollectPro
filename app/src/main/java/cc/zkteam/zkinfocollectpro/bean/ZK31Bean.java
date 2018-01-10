package cc.zkteam.zkinfocollectpro.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 31 项目通用的数据bean
 * Created by WangQing on 2018/1/1.
 */

public class ZK31Bean {

    /**
     * status : 1
     * one_to_many : 1
     * data : [{"id":"1","name":"避孕节育信息","type":"1","list":"","data":[{"number":"1","type":"1","name":"子女数","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_haschilds"},{"number":"2","type":"3","name":"最小子女出生日期","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_lastchild"},{"number":"3","type":"2","name":"避孕方法","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"输卵管结扎|输卵管结扎,宫内节育器|宫内节育器,内用避孕药|内用避孕药,外用避孕药|外用避孕药,皮下埋植避孕|皮下埋植避孕,安全期避孕|安全期避孕,避孕套|避孕套,紧急避孕|紧急避孕","table_name":"contraception_type"},{"number":"4","type":"3","name":"开始时间","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_startdate"},{"number":"5","type":"3","name":"结束时间","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"contraception_stopdate"},{"number":"6","type":"1","name":"提供服务单位","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_unit"},{"number":"7","type":"1","name":"停用原因","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_stopcause"}]},{"id":"2","name":"妇检情况信息","type":"1","list":null,"data":[{"number":"1","type":"3","name":"检查时间","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"checkdate[]"},{"number":"2","type":"1","name":"检查人","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"checkman[]"},{"number":"3","type":"1","name":"医院名称","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"hospital_name[]"},{"number":"4","type":"1","name":"检查结果","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"result[]"}]},{"id":"3","name":"妇检情况信息","type":"1","list":"","data":[{"number":"1","type":"3","name":"检查时间","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"checkdate[]"},{"number":"2","type":"1","name":"检查人","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"checkman[]"},{"number":"3","type":"1","name":"医院名称","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"hospital_name[]"},{"number":"4","type":"1","name":"检查结果","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"result[]"}]},{"id":"4","name":"妇检情况信息","type":"1","list":"","data":[{"number":"1","type":"3","name":"检查时间","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"checkdate[]"},{"number":"2","type":"1","name":"检查人","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"checkman[]"},{"number":"3","type":"1","name":"医院名称","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"hospital_name[]"},{"number":"4","type":"1","name":"检查结果","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"result[]"}]}]
     */

    private int status;
    private String one_to_many;
    private List<DataBeanX> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOne_to_many() {
        return one_to_many;
    }

    public void setOne_to_many(String one_to_many) {
        this.one_to_many = one_to_many;
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
         * name : 避孕节育信息
         * type : 1
         * list :
         * data : [{"number":"1","type":"1","name":"子女数","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_haschilds"},{"number":"2","type":"3","name":"最小子女出生日期","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_lastchild"},{"number":"3","type":"2","name":"避孕方法","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"输卵管结扎|输卵管结扎,宫内节育器|宫内节育器,内用避孕药|内用避孕药,外用避孕药|外用避孕药,皮下埋植避孕|皮下埋植避孕,安全期避孕|安全期避孕,避孕套|避孕套,紧急避孕|紧急避孕","table_name":"contraception_type"},{"number":"4","type":"3","name":"开始时间","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_startdate"},{"number":"5","type":"3","name":"结束时间","default":null,"default_time":null,"first_time":null,"end_time":null,"default_list_data":null,"table_name":"contraception_stopdate"},{"number":"6","type":"1","name":"提供服务单位","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_unit"},{"number":"7","type":"1","name":"停用原因","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"contraception_stopcause"}]
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
             * name : 子女数
             * default :
             * default_time :
             * first_time :
             * end_time :
             * default_list_data :
             * table_name : contraception_haschilds
             */

            private String number;
            private String type;
            private String name;
            @SerializedName("default")
            private String defaultX;
            private String default_time;
            private String first_time;
            private String end_time;
            private String default_list_data;
            private String table_name;

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

            public String getDefault_list_data() {
                return default_list_data;
            }

            public void setDefault_list_data(String default_list_data) {
                this.default_list_data = default_list_data;
            }

            public String getTable_name() {
                return table_name;
            }

            public void setTable_name(String table_name) {
                this.table_name = table_name;
            }
        }
    }
}
