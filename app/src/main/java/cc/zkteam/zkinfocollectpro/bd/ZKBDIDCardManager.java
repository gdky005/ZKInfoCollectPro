package cc.zkteam.zkinfocollectpro.bd;

import android.support.annotation.CheckResult;
import android.text.TextUtils;

import java.io.File;
import java.net.URLEncoder;

import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardRequestBody;
import cc.zkteam.zkinfocollectpro.bean.BDTokenBean;
import cc.zkteam.zkinfocollectpro.exception.ZKIdCardException;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.baidu.Base64Util;
import cc.zkteam.zkinfocollectpro.utils.baidu.FileUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZKBDIDCardManager
 * Created by WangQing on 2017/12/24.
 */
public class ZKBDIDCardManager {

    private String accessToken = "";
    private long expiresIn = 0; //单位是秒

    private String charsetName = "UTF-8";
    private String mediaType = "application/x-www-form-urlencoded";

    private static ZKBDIDCardManager instance = null;

    private ZKBDIDCardManager() {
    }

    public static ZKBDIDCardManager getInstance() {
        if (instance == null) {
            synchronized (ZKBDIDCardManager.class) {
                ZKBDIDCardManager temp = instance;
                if (temp == null) {
                    temp = new ZKBDIDCardManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }


    /**
     * 获取当前可用的 accessToken
     * @return accessToken
     */
    @CheckResult
    public String getAccessToken() {
        if (TextUtils.isEmpty(accessToken)) {
            ZKBDAccessTokenSP accessTokenSP = new ZKBDAccessTokenSP();

            expiresIn = (long) accessTokenSP.get(ZKBDAccessTokenSP.KEY_EXPIRES_IN, 0L);
            long currentTime = System.currentTimeMillis();
            if (currentTime > expiresIn) {
                refreshAccessToken();
                return null;
            }

            accessToken = (String) accessTokenSP.get(ZKBDAccessTokenSP.KEY_ACCESS_TOKEN, "");
        }
        return accessToken;
    }

    /**
     * 自动刷新 Token, 如果在有效期以内就不用刷新，否则就刷新。
     */
    public void autoRefreshToken() {
        String token = getAccessToken();
        if (TextUtils.isEmpty(token)) {
            L.d("本次获取的 Token 为 null or '', 已经自动刷新一次 Token");
        } else {
            L.d("当前默认的 Token 是：" + token);
        }
    }

    /**
     * 参考地址：http://ai.baidu.com/docs#/Auth/top
     */
    public void refreshAccessToken() {
        ZHConnectionManager.getInstance().getZHApi().bdAccessToken(Constant.BD_GRANT_TYPE,
                Constant.BD_API_KEY, Constant.BD_SECRET_KEY).enqueue(new Callback<BDTokenBean>() {
            @Override
            public void onResponse(Call<BDTokenBean> call, Response<BDTokenBean> response) {
                L.d("onResponse() called with: call = [" + call + "], response = [" + response + "]");

                BDTokenBean bdTokenBean = response.body();
                if (bdTokenBean != null) {
                    accessToken = bdTokenBean.getAccess_token();
                    expiresIn = bdTokenBean.getExpires_in();

                    // 获取过期时间
                    long expiresTime = System.currentTimeMillis() + expiresIn;

                    ZKBDAccessTokenSP accessTokenSP = new ZKBDAccessTokenSP();
                    accessTokenSP.put(ZKBDAccessTokenSP.KEY_ACCESS_TOKEN, accessToken);
                    accessTokenSP.put(ZKBDAccessTokenSP.KEY_EXPIRES_IN, expiresTime);

                    L.d("获取成功 accessToken=" + accessToken);
                }
            }

            @Override
            public void onFailure(Call<BDTokenBean> call, Throwable t) {
                L.e("获取 Token 失败 onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    /**
     * 获取身份证识别的信息
     * 参考：http://ai.baidu.com/docs#/OCR-API/top
     */
    public void getIdCardInfo(String localPicPath, Callback<BDIdCardBean> callback) throws ZKIdCardException {
        // 2017/12/24 Check 文件大小是否正常
        if (callback == null){
            throw new ZKIdCardException("callback is not null!");
        }

        if (TextUtils.isEmpty(localPicPath)){
            throw new ZKIdCardException("localPicPath is not null!");
        }

        File file = new File(localPicPath);
        if (!file.exists()) {
            throw new ZKIdCardException("file is not exist!");
        }

        if (file.length() > 4 * 1024 * 1024) {
            throw new ZKIdCardException("File is too large");
        }

        String accessToken = ZKBDIDCardManager.getInstance().getAccessToken();
        if (TextUtils.isEmpty(accessToken)) {
            throw new ZKIdCardException("accessToken is not null, it will auto refresh");
        }

        try {
            byte[] imgData = FileUtil.readFileByBytes(localPicPath);
            String imgStr = Base64Util.encode(imgData);
            String base64EncodeImage = URLEncoder.encode(imgStr, charsetName);

            BDIdCardRequestBody requestBody = BDIdCardRequestBody.BDIDBardRequestBodyFactory
                    .getDefaultRequestBody(true, base64EncodeImage);
            RequestBody body = RequestBody.create(MediaType.parse(mediaType), requestBody.toString());

            ZHConnectionManager.getInstance().getZHApi().bdIDCard(body, accessToken).enqueue(callback);
        } catch (Exception e) {
            throw new ZKIdCardException("generate bd params error", e);
        }
    }

}
