package cc.zkteam.zkinfocollectpro.api;

import java.util.List;

import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.BDTokenBean;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.bean.ZHTongJiBean;
import cc.zkteam.zkinfocollectpro.bean.ZKTestBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZKTestCategoryBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * ZKService retrofit2
 * Created by WangQing on 2017/10/27.
 */

public interface ZHApi {

    /**
     * 获取百度的 AccessToken
     */
    @POST(Constant.BD_ACCESS_TOKEN_URL)
    Call<BDTokenBean> bdAccessToken(@Query("grant_type") String type, @Query("client_id") String client_id,
                                    @Query("client_secret") String client_secret);

    /**
     * 准备添加 身份证：http://ai.baidu.com/docs#/OCR-API/top
     */
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST(Constant.BD_ID_CARD_URL)
    Call<BDIdCardBean> bdIDCard(@Body RequestBody requestBody, @Query("access_token") String access_token);

    /**
     * 请求分类接口
     * test
     *
     * @return 分类的数据
     */
    @GET(Constant.ZK_CE_SHI_URL)
    Call<ZKTestBaseBean<List<ZKTestCategoryBean>>> categoryData(@Query("PAGE_COUNT") int count);


    /**
     * **********************************************************************************************
     * *******************************以下是智慧＋的接口*********************************************
     * **********************************************************************************************
     */


    /**
     * 登录接口
     */
    @GET("datamanage.php/Admin/AppInterface/login")
    Call<ZHBaseBean<ZHLoginBean>> login(@Query("username") String username,
                                        @Query("password") String password);

    @GET("datamanage.php/Admin/AppInterface/get_sign")
    Call<ZHBaseBean> getSignStatus(@Query("userid") String userId);

    /**
     * 统计接口
     */
    @GET("datamanage.php/Admin/AppInterface/tongji")
    Call<ZHTongJiBean> tongji(@Query("userid") String userid);


    /**
     * 数据采集-联动菜单接口
     */
    @GET("datamanage.php/Admin/AppInterface/shequliandong")
    Call<ZHCommunityBean> shequliandong(@Query("id") String id,
                                        @Query("type") String type);

    /**
     * 生成楼房住户明细的接口
     */
    @GET("datamanage.php/Admin/AppInterface/gethouses")
    Call<ZHBaseBean> getHouses(@Query("jiedao") String jiedao,
                               @Query("shequ") String shequ,
                               @Query("xiaoqu") String xiaoqu,
                               @Query("lou") String lou,
                               @Query("danyuan") String danyuan);

    /**
     * 首页-签到接口
     */
    @GET("datamanage.php/Admin/AppInterface/sign")
    Call<ZHBaseBean> sign(@Query("username") String username,
                          @Query("userid") String userid,
                          @Query("long") String lon,
                          @Query("lat") String lat,
                          @Query("address") String address);

    /**
     * 新增住户接口
     */
    @FormUrlEncoded
    @POST("datamanage.php/Admin/AppInterface/addhouse")
    Call<ZHBaseBean> addHouse(@Field("community") String community,
                              @Field("cunjuid") String cunjuid,
                              @Field("gridding") String gridding,
                              @Field("hsid") String hsid,
                              @Field("house_serial") String house_serial,
                              @Field("louceng") String address,
                              @Field("house_number") String house_number);

    /**
     * 接收表单数据录入接口
     */
    @FormUrlEncoded
    @POST("datamanage.php/Admin/AppInterface/addpersonbaseinfo")
    Call<ZHBaseBean> addPersonBaseInfo(@Field("community") String community,
                                       @Field("cunjuid") String cunjuid,
                                       @Field("gridding") String gridding,
                                       @Field("hsid") String hsid,
                                       @Field("house_serial") String house_serial,
                                       @Field("louceng") String address,
                                       @Field("house_number") String house_number);

    /**
     * 接收表单数据录入接口
     */
    @Multipart
    @POST("Datamanage.php/Admin/AppInterface/wenti_submit.html")
    Call<ZHBaseBean> report(@Part MultipartBody.Part file);

}
