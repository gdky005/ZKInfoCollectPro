package cc.zkteam.zkinfocollectpro.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * BDIdCardBean
 * Created by WangQing on 2017/12/15.
 */

public class BDIdCardBean implements Serializable {

    /**
     * log_id : 2648325511
     * direction : 0
     * image_status : normal
     * idcard_type : normal
     * edit_tool : Adobe Photoshop CS3 Windows
     * words_result : {"住址":{"location":{"left":267,"top":453,"width":459,"height":99},"words":"南京市江宁区弘景大道3889号"},"公民身份号码":{"location":{"left":443,"top":681,"width":589,"height":45},"words":"330881199904173914"},"出生":{"location":{"left":270,"top":355,"width":357,"height":45},"words":"19990417"},"姓名":{"location":{"left":267,"top":176,"width":152,"height":50},"words":"伍云龙"},"性别":{"location":{"left":269,"top":262,"width":33,"height":52},"words":"男"},"民族":{"location":{"left":492,"top":279,"width":30,"height":37},"words":"汉"}}
     * words_result_num : 6
     */

    private long log_id;
    private int direction;
    private String image_status;
    private String idcard_type;
    private String edit_tool;
    private WordsResultBean words_result;
    private int words_result_num;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public String getIdcard_type() {
        return idcard_type;
    }

    public void setIdcard_type(String idcard_type) {
        this.idcard_type = idcard_type;
    }

    public String getEdit_tool() {
        return edit_tool;
    }

    public void setEdit_tool(String edit_tool) {
        this.edit_tool = edit_tool;
    }

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public static class WordsResultBean implements Serializable {
        /**
         * 住址 : {"location":{"left":267,"top":453,"width":459,"height":99},"words":"南京市江宁区弘景大道3889号"}
         * 公民身份号码 : {"location":{"left":443,"top":681,"width":589,"height":45},"words":"330881199904173914"}
         * 出生 : {"location":{"left":270,"top":355,"width":357,"height":45},"words":"19990417"}
         * 姓名 : {"location":{"left":267,"top":176,"width":152,"height":50},"words":"伍云龙"}
         * 性别 : {"location":{"left":269,"top":262,"width":33,"height":52},"words":"男"}
         * 民族 : {"location":{"left":492,"top":279,"width":30,"height":37},"words":"汉"}
         */

        @SerializedName("住址")
        private AddressBean address;
        @SerializedName("公民身份号码")
        private IdCardNumberBean idCardNumber;
        @SerializedName("出生")
        private BirthdayBean birthday;
        @SerializedName("姓名")
        private NameBean name;
        @SerializedName("性别")
        private SexBean sex;
        @SerializedName("民族")
        private NationBean nation;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public IdCardNumberBean getIdCardNumber() {
            return idCardNumber;
        }

        public void setIdCardNumber(IdCardNumberBean idCardNumber) {
            this.idCardNumber = idCardNumber;
        }

        public BirthdayBean getBirthday() {
            return birthday;
        }

        public void setBirthday(BirthdayBean birthday) {
            this.birthday = birthday;
        }

        public NameBean getName() {
            return name;
        }

        public void setName(NameBean name) {
            this.name = name;
        }

        public SexBean getSex() {
            return sex;
        }

        public void setSex(SexBean sex) {
            this.sex = sex;
        }

        public NationBean getNation() {
            return nation;
        }

        public void setNation(NationBean nation) {
            this.nation = nation;
        }

    }

    public static class AddressBean extends IdentifyBean {
    }

    public static class IdCardNumberBean extends IdentifyBean {
    }

    public static class BirthdayBean extends IdentifyBean {
    }

    public static class NameBean extends IdentifyBean {
    }

    public static class SexBean extends IdentifyBean {
    }

    public static class NationBean extends IdentifyBean {
    }

    public static class IdentifyBean implements Serializable {
        /**
         * location : {"left":267,"top":453,"width":459,"height":99}
         * words : 南京市江宁区弘景大道3889号
         */

        private LocationBean location;
        private String words;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }

    public static class LocationBean implements Serializable {
        /**
         * left : 267
         * top : 453
         * width : 459
         * height : 99
         */

        private int left;
        private int top;
        private int width;
        private int height;

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
