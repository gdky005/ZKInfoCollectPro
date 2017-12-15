package cc.zkteam.zkinfocollectpro.bean;

/**
 * BDIdCardRequestBody
 * Created by WangQing on 2017/12/15.
 */

public class BDIdCardRequestBody {
    /**
     * 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:
     - true：检测朝向；
     - false：不检测朝向
     */
    private boolean detect_direction;
    /**
     * front：身份证正面；back：身份证背面
     */
    private String id_card_side;
    /**
     * 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式
     */
    private String image;
    /**
     * 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启
     */
    private String detect_risk;

    public boolean isDetect_direction() {
        return detect_direction;
    }

    public void setDetect_direction(boolean detect_direction) {
        this.detect_direction = detect_direction;
    }

    public String getId_card_side() {
        return id_card_side;
    }

    public void setId_card_side(String id_card_side) {
        this.id_card_side = id_card_side;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetect_risk() {
        return detect_risk;
    }

    public void setDetect_risk(String detect_risk) {
        this.detect_risk = detect_risk;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("detect_direction=").append(detect_direction);
        sb.append("&id_card_side=").append(id_card_side);
        sb.append("&image=").append(image);
        sb.append("&detect_risk=").append(detect_risk);
        return sb.toString();
    }

    public static class BDIDBardRequestBodyFactory {
        /**
         *  获取默认的 BDIdCardRequestBody
         * @param isFront               是否中身份证正面
         * @param base64EncodeImage     图片内容必须经过 Base64 以后，再进行 encode 处理，并且大小不能大于 4MB
         * @return  BDIdCardRequestBody
         */
        public static BDIdCardRequestBody getDefaultRequestBody(boolean isFront, String base64EncodeImage) {
            BDIdCardRequestBody requestBody = new BDIdCardRequestBody();
            requestBody.setDetect_direction(false);
            requestBody.setId_card_side(isFront ? "front" : "back");
            requestBody.setImage(base64EncodeImage);
            requestBody.setDetect_risk("false");

            return requestBody;
        }
    }
}
