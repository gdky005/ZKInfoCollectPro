package cc.zkteam.zkinfocollectpro.bean;

import java.util.List;

/**
 * Created by loong on 2018/1/4.
 */

public class CollectItemBean {
    /**
     * status : 1
     * data : [{"name":"人员信息","type":"renyuanxinxi_type","ico":"default.gif","num":11},{"name":"证照信息","type":"zhengzhaoxinxi_type","ico":"default.gif","num":11},{"name":"户籍信息","type":"hujixinxi_type","ico":"default.gif","num":11},{"name":"婚姻信息","type":"hunyinxinxi_type","ico":"default.gif","num":11},{"name":"人员关系信息","type":"renyuanguanxixinxi_type","ico":"default.gif","num":11},{"name":"死亡信息","type":"siwangxinxi_type","ico":"default.gif","num":11},{"name":"住址信息","type":"zhuzhixinxi_type","ico":"default.gif","num":11},{"name":"健康信息","type":"jiankangxinxi_type","ico":"default.gif","num":11},{"name":"网格信息","type":"wanggexinxi_type","ico":"default.gif","num":11},{"name":"诚信信息","type":"chengxinxinxi_type","ico":"default.gif","num":11},{"name":"社保卡信息","type":"shebaokaxinxi_type","ico":"default.gif","num":11},{"name":"工商保险信息","type":"gongshangbaoxianxinxi_type","ico":"default.gif","num":11},{"name":"医疗保险信息","type":"yiliaobaoxianxinxi_type","ico":"default.gif","num":11},{"name":"生育保险信息","type":"shengyubaoxianxinxi_type","ico":"default.gif","num":11},{"name":"养老保险信息","type":"yanglaobaoxianxinxi_type","ico":"default.gif","num":11},{"name":"新农合信息","type":"xinnonghexinxi_type","ico":"default.gif","num":11},{"name":"新农保信息","type":"xinnongbaoxinxi_type","ico":"default.gif","num":11},{"name":"住房公积金信息","type":"zhufanggongjijinxinxi_type","ico":"default.gif","num":11},{"name":"出生证信息","type":"chushengzhengxinxi_type","ico":"default.gif","num":11},{"name":"避孕节育信息","type":"biyunjieyuxinxi_type","ico":"default.gif","num":11},{"name":"育龄妇女信息","type":"yulingfunvxinxi_type","ico":"default.gif","num":11},{"name":"残疾人信息","type":"canjirenxinxi_type","ico":"default.gif","num":11},{"name":"低保信息","type":"dibaoxinxi_type","ico":"default.gif","num":11},{"name":"重点人员信息","type":"zhongdianrenyuanxinxi_type","ico":"default.gif","num":11},{"name":"教育经历信息","type":"jiaoyujinglixinxi_type","ico":"default.gif","num":11},{"name":"党员信息","type":"dangyuanxinxi_type","ico":"default.gif","num":11},{"name":"军人信息","type":"junrenxinxi_type","ico":"default.gif","num":11},{"name":"行政事业单位人员信息","type":"xingzhengshiyedanweixinxi_type","ico":"default.gif","num":11},{"name":"法人信息","type":"farenxinxi_type","ico":"default.gif","num":11},{"name":"车辆信息","type":"cheliangxinxi_type","ico":"default.gif","num":11},{"name":"屋权信息","type":"wuquanxinxi_type","ico":"default.gif","num":11}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 人员信息
         * type : renyuanxinxi_type
         * ico : default.gif
         * num : 11
         */

        private String name;
        private String type;
        private String ico;
        private int num;

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

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
