package cc.zkteam.zkinfocollectpro.bean;

/**
 * Created by loong on 2018/1/4.
 */

public class PersonalSimpleInfoBean {
    /**
     * status : 1
     * data : {"name":"王美星","id_card_type":"0","id_card":"身份证","sex":"男","nation":"汉族","img":"","caijixiang_lei":"31类","caijixiang_xiang":"655项","caiji_state":"未采集"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 王美星
         * id_card_type : 0
         * id_card : 身份证
         * sex : 男
         * nation : 汉族
         * img :
         * caijixiang_lei : 31类
         * caijixiang_xiang : 655项
         * caiji_state : 未采集
         */

        private String name;
        private String id_card_type;
        private String id_card;
        private String sex;
        private String nation;
        private String img;
        private String caijixiang_lei;
        private String caijixiang_xiang;
        private String caiji_state;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId_card_type() {
            return id_card_type;
        }

        public void setId_card_type(String id_card_type) {
            this.id_card_type = id_card_type;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCaijixiang_lei() {
            return caijixiang_lei;
        }

        public void setCaijixiang_lei(String caijixiang_lei) {
            this.caijixiang_lei = caijixiang_lei;
        }

        public String getCaijixiang_xiang() {
            return caijixiang_xiang;
        }

        public void setCaijixiang_xiang(String caijixiang_xiang) {
            this.caijixiang_xiang = caijixiang_xiang;
        }

        public String getCaiji_state() {
            return caiji_state;
        }

        public void setCaiji_state(String caiji_state) {
            this.caiji_state = caiji_state;
        }
    }
}