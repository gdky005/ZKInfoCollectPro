package cc.zkteam.zkinfocollectpro.api;

import java.util.List;

import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.bean.BDTokenBean;
import cc.zkteam.zkinfocollectpro.bean.BaseBean;
import cc.zkteam.zkinfocollectpro.bean.CategoryBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ZKService retrofit2
 * Created by WangQing on 2017/10/27.
 */

public interface ZKApi {

    /**
     * 请求分类接口
     *
     * @return 分类的数据
     */
    @GET("JueDiQiuSheng/categoryJson")
    Call<BaseBean<List<CategoryBean>>> categoryData(@Query("PAGE_COUNT") int count);


    @POST(Constant.BD_ACCESS_TOKEN_URL)
    Call<BDTokenBean> bdAccessToken(@Query("grant_type") String type, @Query("client_id") String client_id,
                                    @Query("client_secret") String client_secret);


    // 准备添加 身份证失败：http://ai.baidu.com/docs#/OCR-API/top
//    @Headers({
//            "Content-Type: application/x-www-form-urlencoded",
//    })
//    @POST(Constant.BD_ID_CARD_URL)
//    Call bdIDCard(@Query("access_token") String access_token);

}
