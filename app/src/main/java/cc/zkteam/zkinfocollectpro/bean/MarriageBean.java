package cc.zkteam.zkinfocollectpro.bean;

/**
 * Created by loong on 2017/12/31.
 */

public class MarriageBean {

    /**
     * status : 1
     * data : {"id":"2","p_id":"1","type":"3","newtype":"2","marriage_number":"23123213213","marriage_registration_authority":"zxxxxxxx","marriage_registration_person":"zzzz","marriage_registration_date":"2012-12-05","marriage_remark":"vvcccv","consort_id":"2","consort_name":"测试","consort_sex":"1","consort_id_card":"522125198705053234","consort_birthday":"1991年01月08","image":"/Uploads/Picture/2017-12-13/5a310a6c92cfd.jpg","marriage_change_date":"","createtime":"1513163390","updatetime":"1513165241","name":"李明","id_card":"522128198002023234","birthday":"1980-02-02","sex":"1"}
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
         * id : 2
         * p_id : 1
         * type : 3
         * newtype : 2
         * marriage_number : 23123213213
         * marriage_registration_authority : zxxxxxxx
         * marriage_registration_person : zzzz
         * marriage_registration_date : 2012-12-05
         * marriage_remark : vvcccv
         * consort_id : 2
         * consort_name : 测试
         * consort_sex : 1
         * consort_id_card : 522125198705053234
         * consort_birthday : 1991年01月08
         * image : /Uploads/Picture/2017-12-13/5a310a6c92cfd.jpg
         * marriage_change_date :
         * createtime : 1513163390
         * updatetime : 1513165241
         * name : 李明
         * id_card : 522128198002023234
         * birthday : 1980-02-02
         * sex : 1
         */

        private String id;
        private String p_id;
        private String type;
        private String newtype;
        private String marriage_number;
        private String marriage_registration_authority;
        private String marriage_registration_person;
        private String marriage_registration_date;
        private String marriage_remark;
        private String consort_id;
        private String consort_name;
        private String consort_sex;
        private String consort_id_card;
        private String consort_birthday;
        private String image;
        private String marriage_change_date;
        private String createtime;
        private String updatetime;
        private String name;
        private String id_card;
        private String birthday;
        private String sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNewtype() {
            return newtype;
        }

        public void setNewtype(String newtype) {
            this.newtype = newtype;
        }

        public String getMarriage_number() {
            return marriage_number;
        }

        public void setMarriage_number(String marriage_number) {
            this.marriage_number = marriage_number;
        }

        public String getMarriage_registration_authority() {
            return marriage_registration_authority;
        }

        public void setMarriage_registration_authority(String marriage_registration_authority) {
            this.marriage_registration_authority = marriage_registration_authority;
        }

        public String getMarriage_registration_person() {
            return marriage_registration_person;
        }

        public void setMarriage_registration_person(String marriage_registration_person) {
            this.marriage_registration_person = marriage_registration_person;
        }

        public String getMarriage_registration_date() {
            return marriage_registration_date;
        }

        public void setMarriage_registration_date(String marriage_registration_date) {
            this.marriage_registration_date = marriage_registration_date;
        }

        public String getMarriage_remark() {
            return marriage_remark;
        }

        public void setMarriage_remark(String marriage_remark) {
            this.marriage_remark = marriage_remark;
        }

        public String getConsort_id() {
            return consort_id;
        }

        public void setConsort_id(String consort_id) {
            this.consort_id = consort_id;
        }

        public String getConsort_name() {
            return consort_name;
        }

        public void setConsort_name(String consort_name) {
            this.consort_name = consort_name;
        }

        public String getConsort_sex() {
            return consort_sex;
        }

        public void setConsort_sex(String consort_sex) {
            this.consort_sex = consort_sex;
        }

        public String getConsort_id_card() {
            return consort_id_card;
        }

        public void setConsort_id_card(String consort_id_card) {
            this.consort_id_card = consort_id_card;
        }

        public String getConsort_birthday() {
            return consort_birthday;
        }

        public void setConsort_birthday(String consort_birthday) {
            this.consort_birthday = consort_birthday;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMarriage_change_date() {
            return marriage_change_date;
        }

        public void setMarriage_change_date(String marriage_change_date) {
            this.marriage_change_date = marriage_change_date;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
