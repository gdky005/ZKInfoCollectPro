package cc.zkteam.zkinfocollectpro.exception;

/**
 * 必须处理 的异常，不处理，就 crash 给你看
 *
 * Created by WangQing on 2017/5/12.
 */
public class ZKIdCardException extends Exception {

    public ZKIdCardException() {
    }

    public ZKIdCardException(String message) {
        super(message);
    }

    public ZKIdCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
