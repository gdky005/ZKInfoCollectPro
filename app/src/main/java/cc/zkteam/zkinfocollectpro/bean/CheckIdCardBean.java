package cc.zkteam.zkinfocollectpro.bean;

/**
 * CheckIdCardBean
 * Created by WangQing on 2018/1/6.
 */

public class CheckIdCardBean {

    /**
     * status : 1
     * msg : 身份证不存在
     * result : false
     */

    private int status;
    private String msg;
    private boolean result;

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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
