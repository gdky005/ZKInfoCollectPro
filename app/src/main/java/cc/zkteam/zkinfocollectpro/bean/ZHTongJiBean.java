package cc.zkteam.zkinfocollectpro.bean;

import java.util.List;

/**
 * Created by WangQing on 2017/12/21.
 */

public class ZHTongJiBean {

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 0
         * percent : 0%
         * wancheng : 50
         * caiji : 60
         * weicaiji : 55
         */

        private String total;
        private String percent;
        private String wancheng;
        private String caiji;
        private String weicaiji;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getWancheng() {
            return wancheng;
        }

        public void setWancheng(String wancheng) {
            this.wancheng = wancheng;
        }

        public String getCaiji() {
            return caiji;
        }

        public void setCaiji(String caiji) {
            this.caiji = caiji;
        }

        public String getWeicaiji() {
            return weicaiji;
        }

        public void setWeicaiji(String weicaiji) {
            this.weicaiji = weicaiji;
        }

        @Override
        public String toString() {
            return "{" +
                    "total:'" + total + '\'' +
                    ", percent:'" + percent + '\'' +
                    ", wancheng:'" + wancheng + '\'' +
                    ", caiji:'" + caiji + '\'' +
                    ", weicaiji:'" + weicaiji + '\'' +
                    '}';
        }
    }
}
