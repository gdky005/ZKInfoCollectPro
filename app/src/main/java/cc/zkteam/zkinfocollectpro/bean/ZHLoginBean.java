package cc.zkteam.zkinfocollectpro.bean;

/**
 * ZHLoginBean
 * Created by WangQing on 2017/12/21.
 */

public class ZHLoginBean {

    /**
     * id : 2
     * name : admin
     * adddate : 23
     * jianjie : dsf
     */

    private String id;
    private String name;
    private String adddate;
    private String jianjie;

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

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", name:'" + name + '\'' +
                ", adddate:'" + adddate + '\'' +
                ", jianjie:'" + jianjie + '\'' +
                '}';
    }
}
