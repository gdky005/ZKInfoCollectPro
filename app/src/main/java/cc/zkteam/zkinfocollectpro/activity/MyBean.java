package cc.zkteam.zkinfocollectpro.activity;

/**
 * Created by WangQing on 2017/12/15.
 */

public class MyBean {

    private String name;
    private String age;

    public String getName() {
        if (name == null) {
            return "WangQing";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {

        if (age == null) {
            return "26";
        }
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
