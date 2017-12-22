package cc.zkteam.zkinfocollectpro.bean;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ProblemPreview {
    private String problemNo;
    private String problemDesc;
    private String problemReportTime;

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
}
