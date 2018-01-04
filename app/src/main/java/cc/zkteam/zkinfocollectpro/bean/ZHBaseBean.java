package cc.zkteam.zkinfocollectpro.bean;

/**
 * ZHBaseBean
 * Created by WangQing on 2017/12/20.
 */

public class ZHBaseBean<T> {

    /**
     * status : state
     * msg : msg
     * data : 数据体
     */

    private int status;
    private String msg;
    private int type;
    private T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "status:" + status +
                ", msg:'" + msg + '\'' +
                ", data:" + data +
                '}';
    }
}
