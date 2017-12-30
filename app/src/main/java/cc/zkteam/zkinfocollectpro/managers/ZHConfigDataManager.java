package cc.zkteam.zkinfocollectpro.managers;

import java.util.List;

import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZKSettingBean;
import cc.zkteam.zkinfocollectpro.retrofit2.ZHCallback;
import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * 从 配置接口获取 相关的  网站配置接口数据。
 * Created by WangQing on 2017/12/30.
 */

public class ZHConfigDataManager {

    private static final String TAG = "ZHConfigDataManager";

    private ZKSettingBean zkSettingBean;

    private static ZHConfigDataManager instance = null;

    private ZHConfigDataManager() {
    }

    public static ZHConfigDataManager getInstance() {
        if (instance == null) {
            synchronized (ZHConfigDataManager.class) {
                ZHConfigDataManager temp = instance;
                if (temp == null) {
                    temp = new ZHConfigDataManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }

    public void refreshConfigData() {
        ZHApi zhApi = ZHConnectionManager.getInstance().getZHApi();
        zhApi.setting(null).enqueue(new ZHCallback<ZKSettingBean>() {
            @Override
            public void onResponse(ZHBaseBean<ZKSettingBean> baseBean, ZKSettingBean result) {
                if (result != null) {
                    L.d("onResponse: " + result.toString());
                    zkSettingBean = result;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                L.e(throwable.getMessage());
            }
        });
    }

    public ZKSettingBean getZKSettingBean() {
        if (zkSettingBean != null) {
            return zkSettingBean;
        }
        return null;
    }


    /**
     * "一档",
     * "二档",
     * "三档",
     * "四档",
     * "五档",
     * "补差"
     */
    public List<String> getPoorType() {
        return getZKSettingBean().getPoorType();
    }

    /**
     * "疾病",
     * "灾害",
     * "残疾",
     * "缺乏劳动力",
     * "失业",
     * "失地",
     * "其他"
     */
    public List<String> getLowHomeType() {
        return getZKSettingBean().getLowHomeType();
    }

    /**
     * "养老保险",
     * "医疗保险",
     * "失业保险",
     * "工伤保险",
     * "生育保险",
     * "住房公积金",
     * "均没有"
     */
    public List<String> getWxyjbox() {
        return getZKSettingBean().getWxyjbox();
    }

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
    public List<String> getHandicapped() {
        return getZKSettingBean().getHandicapped();
    }


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
    public List<String> getBloodType() {
        return getZKSettingBean().getBloodType();
    }


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
    public List<String> getPolitics() {
        return getZKSettingBean().getPolitics();
    }


    /**
     * "",
     * "未婚",
     * "初婚",
     * "离异",
     * "复婚",
     * "丧偶",
     * "再婚"
     */
    public List<String> getMarital() {
        return getZKSettingBean().getMarital();
    }


    /**
     * "",
     * "在职",
     * "离职",
     * "无业",
     * "退休"
     */
    public List<String> getWorkStatus() {
        return getZKSettingBean().getWorkStatus();
    }


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
    public List<String> getNation() {
        return getZKSettingBean().getNation();
    }


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
    public List<String> getStandard() {
        return getZKSettingBean().getStandard();
    }


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
    public List<String> getWorkType() {
        return getZKSettingBean().getWorkType();
    }


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
    public List<String> getCertificate() {
        return getZKSettingBean().getCertificate();
    }


    /**
     * ""
     */
    public List<String> getHealth() {
        return getZKSettingBean().getHealth();
    }

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
    public List<String> getEducation() {
        return getZKSettingBean().getEducation();
    }


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
    public List<String> getKeyPerson() {
        return getZKSettingBean().getKeyPerson();
    }

}
