package cc.zkteam.zkinfocollectpro.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class RentPersoner implements Serializable{


    /**
     * status : 1
     * personlist : [{"p_id":"14","p_man_id":"14","relation":"","name":"李巧荣"},{"p_id":"15","p_man_id":"0","relation":"之夫","name":"郑跃平"},{"p_id":"16","p_man_id":"0","relation":"之子","name":"郑群"},{"p_id":"17","p_man_id":"0","relation":"之女","name":"郑林"},{"p_id":"18","p_man_id":"0","relation":"之女","name":"郑凤"}]
     */

    private int status;
    private List<PersonlistBean> personlist;
    /**
     * houseid : 7
     * personlist : []
     */

    private String houseid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PersonlistBean> getPersonlist() {
        return personlist;
    }

    public void setPersonlist(List<PersonlistBean> personlist) {
        this.personlist = personlist;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public static class PersonlistBean implements Serializable{
        /**
         * p_id : 14
         * p_man_id : 14
         * relation :
         * name : 李巧荣
         */

        private String personid;
        private String p_man_id;
        private String relation;
        private String name;
        private String houseid;

        public PersonlistBean(String p_id, String p_man_id, String relation, String name) {
            this.personid = p_id;
            this.p_man_id = p_man_id;
            this.relation = relation;
            this.name = name;
        }

        public PersonlistBean() {
        }

        public String getP_id() {
            return personid;
        }

        public void setP_id(String p_id) {
            this.personid = p_id;
        }

        public String getP_man_id() {
            return p_man_id;
        }

        public void setP_man_id(String p_man_id) {
            this.p_man_id = p_man_id;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "PersonlistBean{" +
                    "personid='" + personid + '\'' +
                    ", p_man_id='" + p_man_id + '\'' +
                    ", relation='" + relation + '\'' +
                    ", name='" + name + '\'' +
                    ", houseid='" + houseid + '\'' +
                    '}';
        }

        public String getHouseid() {
            return houseid;
        }

        public void setHouseid(String houseid) {
            this.houseid = houseid;
        }
    }

    @Override
    public String toString() {
        return "RentPersoner{" +
                "status=" + status +
                ", personlist=" + personlist +
                '}';
    }
}
