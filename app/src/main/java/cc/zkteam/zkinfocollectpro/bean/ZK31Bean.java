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
     * data : [{"id":"1","name":"人员基础数据","type":"1","list":"","data":[{"number":"1","type":"2","name":"是否是新生儿","default":"否","default_time":"","first_time":"","end_time":"","default_list_data":"是|是,否|否","table_name":"newbaby"},{"number":"2","type":"1","name":"身份证号","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"id_card"},{"number":"3","type":"2","name":"性别","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"男|1,女|2","table_name":"sex"},{"number":"4","type":"3","name":"出生日期","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"birthday"},{"number":"5","type":"1","name":"联系电话","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"phone"},{"number":"6","type":"2","name":"民族","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"nation"},{"number":"7","type":"2","name":"政治面貌","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"群众|1,共产党党员|2,共产党预备党员|3,团员|4,民革|5,民盟|6,民建|7,民进|8,农工党|9,致公党|10,九三学社|11,台盟|12,无党派民主人士|13,未知|14","table_name":"politics"},{"number":"8","type":"2","name":"血型","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"未知|0,A型|1,B型|2,AB型|3,O型|4,Rh型|5,MN型|6,MNSs型|7","table_name":"bloodtype"}]}]
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
         * name : 人员基础数据
         * type : 1
         * list :
         * data : [{"number":"1","type":"2","name":"是否是新生儿","default":"否","default_time":"","first_time":"","end_time":"","default_list_data":"是|是,否|否","table_name":"newbaby"},{"number":"2","type":"1","name":"身份证号","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"","table_name":"id_card"},{"number":"3","type":"2","name":"性别","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"男|1,女|2","table_name":"sex"},{"number":"4","type":"3","name":"出生日期","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"birthday"},{"number":"5","type":"1","name":"联系电话","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"phone"},{"number":"6","type":"2","name":"民族","default":"","default_time":"","first_time":"","end_time":"","default_list_data":null,"table_name":"nation"},{"number":"7","type":"2","name":"政治面貌","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"群众|1,共产党党员|2,共产党预备党员|3,团员|4,民革|5,民盟|6,民建|7,民进|8,农工党|9,致公党|10,九三学社|11,台盟|12,无党派民主人士|13,未知|14","table_name":"politics"},{"number":"8","type":"2","name":"血型","default":"","default_time":"","first_time":"","end_time":"","default_list_data":"未知|0,A型|1,B型|2,AB型|3,O型|4,Rh型|5,MN型|6,MNSs型|7","table_name":"bloodtype"}]
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
             * type : 2
             * name : 是否是新生儿
             * default : 否
             * default_time :
             * first_time :
             * end_time :
             * default_list_data : 是|是,否|否
             * table_name : newbaby
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
