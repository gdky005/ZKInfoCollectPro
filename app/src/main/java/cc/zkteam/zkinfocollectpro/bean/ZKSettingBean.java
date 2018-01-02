package cc.zkteam.zkinfocollectpro.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * ZKSettingBean
 * Created by WangQing on 2017/12/30.
 */
public class ZKSettingBean {

    /**
     * "疾病",
     * "灾害",
     * "残疾",
     * "缺乏劳动力",
     * "失业",
     * "失地",
     * "其他"
     */
    @SerializedName("POORTYPE")
    private List<String> poorType;
    /**
     * "一档",
     * "二档",
     * "三档",
     * "四档",
     * "五档",
     * "补差"
     */
    @SerializedName("LOWHOMETYPE")
    private List<String> lowHomeType;
    /**
     * "养老保险",
     * "医疗保险",
     * "失业保险",
     * "工伤保险",
     * "生育保险",
     * "住房公积金",
     * "均没有"
     */
    @SerializedName("WXYJBOX")
    private List<String> wxyjbox;
    /**
     * "",
     * "精神",
     * "智力",
     * "语言",
     * "视力",
     * "听力",
     * "肢体",
     * "其它"
     */
    @SerializedName("HANDICAPPED")
    private List<String> handicapped;

    /**
     * "未知",
     * "A型",
     * "B型",
     * "AB型",
     * "O型",
     * "Rh型",
     * "MN型",
     * "MNSs型"
     */
    @SerializedName("BLOODTYPE")
    private List<String> bloodType;
    /**
     * "",
     * "群众",
     * "共产党党员",
     * "共产党预备党员",
     * "团员",
     * "民革",
     * "民盟",
     * "民建",
     * "民进",
     * "农工党",
     * "致公党",
     * "九三学社",
     * "台盟",
     * "无党派民主人士",
     * "未知"
     */
    @SerializedName("POLITICS")
    private List<String> politics;
    /**
     * "",
     * "未婚",
     * "初婚",
     * "离异",
     * "复婚",
     * "丧偶",
     * "再婚"
     */
    @SerializedName("MARITAL")
    private List<String> marital;
    /**
     * "",
     * "在职",
     * "离职",
     * "无业",
     * "退休"
     */
    @SerializedName("WORKSTATUS")
    private List<String> workStatus;
    /**
     * "",
     * "汉族",
     * "壮族",
     * "满族",
     * "回族",
     * "苗族",
     * "维吾尔族",
     * "土家族",
     * "彝族",
     * "蒙古族",
     * "藏族",
     * "布依族",
     * "侗族",
     * "瑶族",
     * "朝鲜族",
     * "白族",
     * "哈尼族",
     * "哈萨克族",
     * "黎族",
     * "傣族",
     * "畲族",
     * "傈僳族",
     * "仡佬族",
     * "东乡族",
     * "高山族",
     * "拉祜族",
     * "水族",
     * "佤族",
     * "纳西族",
     * "羌族",
     * "土族",
     * "仫佬族",
     * "锡伯族",
     * "柯尔克孜族",
     * "达斡尔族",
     * "景颇族",
     * "毛南族",
     * "撒拉族",
     * "布朗族",
     * "塔吉克族",
     * "阿昌族",
     * "普米族",
     * "鄂温克族",
     * "怒族",
     * "京族",
     * "基诺族",
     * "德昂族",
     * "保安族",
     * "俄罗斯族",
     * "裕固族",
     * "乌孜别克族",
     * "门巴族",
     * "鄂伦春族",
     * "独龙族",
     * "塔塔尔族",
     * "赫哲族",
     * "珞巴族",
     * "革家人",
     * "穿青族",
     * "其他"
     */
    @SerializedName("NATION")
    private List<String> nation;
    /**
     * "",
     * "文盲与半文盲",
     * "小学",
     * "初中",
     * "高中",
     * "中专中技",
     * "大专",
     * "本科",
     * "研究生",
     * "硕士",
     * "博士"
     */
    @SerializedName("STANDARD")
    private List<String> standard;
    /**
     * "",
     * "国家机关，党群组织，企事业单位的负责人",
     * "专业技术人员",
     * "办事人员和有关人员",
     * "商业，服务业人员",
     * "农，林，牧，鱼，水利业人员",
     * "生产，运输设备操作人员及有关人员",
     * "军人",
     * "不便分类的其他从业人员"
     */
    @SerializedName("WORKTYPE")
    private List<String> workType;
    /**
     * ""
     */
    @SerializedName("HEALTH")
    private List<String> health;
    /**
     * "身份证",
     * "社保卡",
     * "准生证",
     * "出生证明",
     * "户口本",
     * "结婚证",
     * "离婚证",
     * "驾驶证",
     * "警察证",
     * "军人证",
     * "护照",
     * "港澳内地通行证",
     * "台湾通行证"
     */
    @SerializedName("CERTIFICATE")
    private List<String> certificate;
    /**
     * "",
     * "文盲与半文盲",
     * "小学",
     * "初中",
     * "中专/高中",
     * "专科",
     * "本科",
     * "硕士研究生",
     * "博士",
     * "博士后"
     */
    @SerializedName("EDUCATION")
    private List<String> education;
    /**
     * "涉恐或涉稳人员",
     * "重大刑事犯罪前科人员",
     * "涉毒人员",
     * "在逃人员",
     * "肇事肇祸精神病人",
     * "重点上访人员",
     * "退出现役的残疾军人",
     * "烈士遗属、因公牺牲军人遗属、病故军人遗属",
     * "在乡复员军人",
     * "带病回乡退伍军人",
     * "参战退役人员等"
     */
    @SerializedName("KEYPERSON")
    private List<String> keyPerson;

    @SerializedName("WTSB_TYPE")
    private Map<String, Integer> wtsbType;



    public List<String> getPoorType() {
        return poorType;
    }

    public void setPoorType(List<String> poorType) {
        this.poorType = poorType;
    }

    public List<String> getLowHomeType() {
        return lowHomeType;
    }

    public void setLowHomeType(List<String> lowHomeType) {
        this.lowHomeType = lowHomeType;
    }

    public List<String> getWxyjbox() {
        return wxyjbox;
    }

    public void setWxyjbox(List<String> wxyjbox) {
        this.wxyjbox = wxyjbox;
    }

    public List<String> getHandicapped() {
        return handicapped;
    }

    public void setHandicapped(List<String> handicapped) {
        this.handicapped = handicapped;
    }

    public List<String> getBloodType() {
        return bloodType;
    }

    public void setBloodType(List<String> bloodType) {
        this.bloodType = bloodType;
    }

    public List<String> getPolitics() {
        return politics;
    }

    public void setPolitics(List<String> politics) {
        this.politics = politics;
    }

    public List<String> getMarital() {
        return marital;
    }

    public void setMarital(List<String> marital) {
        this.marital = marital;
    }

    public List<String> getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(List<String> workStatus) {
        this.workStatus = workStatus;
    }

    public List<String> getNation() {
        return nation;
    }

    public void setNation(List<String> nation) {
        this.nation = nation;
    }

    public List<String> getStandard() {
        return standard;
    }

    public void setStandard(List<String> standard) {
        this.standard = standard;
    }

    public List<String> getWorkType() {
        return workType;
    }

    public void setWorkType(List<String> workType) {
        this.workType = workType;
    }

    public List<String> getHealth() {
        return health;
    }

    public void setHealth(List<String> health) {
        this.health = health;
    }

    public List<String> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<String> certificate) {
        this.certificate = certificate;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public List<String> getKeyPerson() {
        return keyPerson;
    }

    public void setKeyPerson(List<String> keyPerson) {
        this.keyPerson = keyPerson;
    }

    public Map<String, Integer> getWtsbType() {
        return wtsbType;
    }

    public void setWtsbType(Map<String, Integer> wtsbType) {
        this.wtsbType = wtsbType;
    }
}
