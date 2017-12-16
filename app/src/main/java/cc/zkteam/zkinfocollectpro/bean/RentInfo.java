package cc.zkteam.zkinfocollectpro.bean;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RentInfo {

    public String num;
    public String name;
    public String relation;
    public String update;
    public String operate;

    public RentInfo() {
    }

    public RentInfo(String num, String name, String relation, String update, String operate) {
        this.num = num;
        this.name = name;
        this.relation = relation;
        this.update = update;
        this.operate = operate;
    }
}
