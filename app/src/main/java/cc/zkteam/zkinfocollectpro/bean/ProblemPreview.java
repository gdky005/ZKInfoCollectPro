package cc.zkteam.zkinfocollectpro.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPreview {
    private String problemNo;
    private String problemDesc;
    private String problemReportTime;
    /**
     * status : 1
     * data : [{"id":"1","number":"10086","problemcontent":"污染严重","time":"1514515823"},{"id":"2","number":"10087","problemcontent":"经常被践踏","time":"1514515823"},{"id":"3","number":"10088","problemcontent":"废气乱排","time":"1514515823"},{"id":"4","number":"23456","problemcontent":"","time":"1514515823"},{"id":"5","number":"23456e","problemcontent":"dfdf","time":"1514390400"},{"id":"6","number":"234567","problemcontent":"dfdf","time":"1514390400"},{"id":"7","number":"1234567","problemcontent":"dfdf","time":"1514390400"},{"id":"8","number":"wenti1514199907","problemcontent":"这是测试数据哦","time":"1514390400"},{"id":"9","number":"wenti1514205530785","problemcontent":"这是测试数据哦","time":"1514515823"},{"id":"10","number":"wenti1514205587751","problemcontent":"这是测试数据哦","time":"1514515823"},{"id":"11","number":"wenti1514206492858","problemcontent":"这是测试数据哦","time":"1514515823"},{"id":"12","number":"wenti1514215684958","problemcontent":"干干净净","time":"1514515823"},{"id":"13","number":"wenti1514277768149","problemcontent":"呵呵不能","time":"1514515823"},{"id":"14","number":"wenti1514373989098","problemcontent":"非常不不不","time":"1514515823"},{"id":"15","number":"wenti1514374026563","problemcontent":"非常不不不","time":"1511798400"},{"id":"36","number":"","problemcontent":"这是测试数据4","time":"1513872000"},{"id":"37","number":"","problemcontent":"这是测试数据3","time":"1513872000"},{"id":"38","number":"","problemcontent":"这是测试数据2","time":"1513872000"},{"id":"39","number":"","problemcontent":"这是测试数据1","time":"1513872000"},{"id":"40","number":"wenti15141999073","problemcontent":"这是测试数据哦","time":"1514610534"},{"id":"41","number":"wenti1514877965285","problemcontent":"呵呵就那么","time":null},{"id":"42","number":"wenti1514878783462","problemcontent":"根本不能","time":null},{"id":"43","number":"","problemcontent":"","time":"1514653200"},{"id":"44","number":"","problemcontent":"","time":"1514307600"},{"id":"45","number":"","problemcontent":"","time":"1514221200"},{"id":"46","number":"","problemcontent":"","time":"1514221200"},{"id":"47","number":"","problemcontent":"","time":"1514739600"}]
     */

    private int status;
    private List<DataBean> data;

    public ProblemPreview() {
    }

    public ProblemPreview(String problemNo, String problemDesc, String problemReportTime) {
        this.problemNo = problemNo;
        this.problemDesc = problemDesc;
        this.problemReportTime = problemReportTime;
    }

    public String getProblemReportTime() {
        return problemReportTime;
    }

    public void setProblemReportTime(String problemReportTime) {
        this.problemReportTime = problemReportTime;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getProblemNo() {
        return problemNo;
    }

    public void setProblemNo(String problemNo) {
        this.problemNo = problemNo;
    }

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
         * id : 1
         * number : 10086
         * problemcontent : 污染严重
         * time : 1514515823
         */

        private String id;
        private String number;
        private String problemcontent;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getProblemcontent() {
            return problemcontent;
        }

        public void setProblemcontent(String problemcontent) {
            this.problemcontent = problemcontent;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
