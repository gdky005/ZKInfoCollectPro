package cc.zkteam.zkinfocollectpro.api;

import java.util.List;

import cc.zkteam.zkinfocollectpro.Constant;
import cc.zkteam.zkinfocollectpro.bean.ZK31Bean;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.BDTokenBean;
import cc.zkteam.zkinfocollectpro.bean.CheckIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.CollectItemBean;
import cc.zkteam.zkinfocollectpro.bean.MarriageBean;
import cc.zkteam.zkinfocollectpro.bean.PersonalSimpleInfoBean;
import cc.zkteam.zkinfocollectpro.bean.ProblemPreview;
import cc.zkteam.zkinfocollectpro.bean.RentPersoner;
import cc.zkteam.zkinfocollectpro.bean.ZHAddhosePersonBean;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHCommunityBean;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.bean.ZHTongJiBean;
import cc.zkteam.zkinfocollectpro.bean.ZKSettingBean;
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
    Call<BDTokenBean> bdAccessToken(@Query("grant_type") String type,
                                    @Query("client_id") String client_id,
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

    /**
     * 修改密码
     */
    @POST("datamanage.php/Admin/AppInterface/editpassword")
    Call<ZHBaseBean> resetPwd(@Query("username") String username, @Query("oldpassword") String oldpassword, @Query("newpassword") String newpassword);


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
     * 接收表单数据录入接口
     */
    @Deprecated
    @FormUrlEncoded
    @POST("datamanage.php/Admin/AppInterface/addpersonbaseinfo")
    Call<ZHBaseBean> addPersonBaseInfo(@Part("community") String community,
                                       @Part("cunjuid") String cunjuid,
                                       @Part("gridding") String gridding,
                                       @Part("hsid") String hsid,
                                       @Part("house_serial") String house_serial,
                                       @Part("louceng") String address,
                                       @Part("house_number") String house_number);


//    @Headers({"Content-Type: application/x-www-form-urlencoded"})
//    @POST(Constant.BD_ID_CARD_URL)
//    Call<BDIdCardBean> bdIDCard(@Body RequestBody requestBody, @Query("access_token") String access_token);

    /**
     * 接收表单数据录入接口
     */
    @Headers({"Content-Type: application/json"})
    @POST("datamanage.php/Admin/AppInterface/addpersonbaseinfo")
    Call<ZHBaseBean> update31Data(@Body RequestBody requestBody);


    /**
     * 新增住户接口
     */
    @Multipart
    @POST("datamanage.php/Admin/AppInterface/addhouse")
    Call<RentPersoner> addHouse(@Part MultipartBody.Part community,
                                @Part MultipartBody.Part cunjuid,
                                @Part MultipartBody.Part gridding,
                                @Part MultipartBody.Part houseid,
                                @Part MultipartBody.Part house_serial,
                                @Part MultipartBody.Part louceng,
                                @Part MultipartBody.Part house_number);

    /**
     * 问题上报接口已经提交
     */
    @Multipart
    @POST("Datamanage.php/Admin/AppInterface/wenti_submit.html")
    Call<ZHBaseBean> report(@Part MultipartBody.Part number,
                            @Part MultipartBody.Part reporter,
                            @Part MultipartBody.Part problemposition,
                            @Part MultipartBody.Part problemcontent,
                            @Part MultipartBody.Part remarks,
                            @Part MultipartBody.Part type,
                            @Part MultipartBody.Part[] path,
                            @Part MultipartBody.Part[] filetype);

    /**
     * 用户迁出
     */
    @GET("Datamanage.php/Admin/AppInterface/qianchu")
    Call<ZHBaseBean> emigration(@Query("personid") String personid,
                                @Query("houseid") String houseid,
                                @Query("buildid") String buildid);

    /**
     * 获取服务器配置的接口
     */
    @GET("datamanage.php/Admin/AppshowInterface/setting")
    Call<ZHBaseBean<ZKSettingBean>> setting(@Query("para") String para);


    /**
     * 获取婚姻信息接口
     */
    @GET("datamanage.php/Admin/AppshowInterface/marriage")
    Call<MarriageBean> getMarriageInfo(@Query("id") String id);


    /**
     * 接收表单数据录入接口
     */
    @Multipart
    @POST("datamanage.php/Admin/AppInterface/addpersonbaseinfo")
    Call<ZHBaseBean> addPersonBaseInfo_Company( /*法人信息字段开始,为了和网页的名字一样 前面大写就先保留了*/
                                                @Part MultipartBody.Part Company1,
                                                @Part MultipartBody.Part Company2,
                                                @Part MultipartBody.Part Company3,
                                                @Part MultipartBody.Part Company4,
                                                @Part MultipartBody.Part Company5,
                                                @Part MultipartBody.Part Company6,
                                                @Part MultipartBody.Part Company7,
                                                @Part MultipartBody.Part Company8,
                                                @Part MultipartBody.Part Company9,
                                                @Part MultipartBody.Part Company10,
                                                @Part MultipartBody.Part Company11,
                                                @Part MultipartBody.Part Company12,
                                                @Part MultipartBody.Part Company13,
                                                @Part MultipartBody.Part Company14,
                                                @Part MultipartBody.Part Company15,
                                                @Part MultipartBody.Part Company16,
                                                @Part MultipartBody.Part Company17,
                                                @Part MultipartBody.Part Company18,
                                                @Part MultipartBody.Part Company19,
                                                @Part MultipartBody.Part Company20,
                                                @Part MultipartBody.Part Company21)
    ;


    /**
     * 获取 31 项目数据 接口
     */
    @GET("datamanage.php/Admin/AppInterface/getfieldlist")
    Call<ZK31Bean> get31Data(@Query("pagename") String pagename, @Query("id") String id);

    /**
     * 问题上报列表
     */
    @GET("datamanage.php/Admin/AppInterface/wentishangbao_list")
    Call<ProblemPreview> getProblemList(@Query("personid") String personid);

    /**
     * 数据采集界面人员基本信息获取
     */
    @GET("datamanage.php/Admin/AppInterface/persondetail")
    Call<PersonalSimpleInfoBean> getPersonalSimpleInfo(@Query("personid") String personid);

    /**
     * 数据采集界面信息列表
     */
    @GET("datamanage.php/Admin/AppInterface/getformnamelist")
    Call<CollectItemBean> getPersonalInfoList();

    /**
     * 获取或者修改采集状态
     * 0.未采集 1.采集中 2.审核中 3.采集完成 4.申请激活
     *
     * @param personid
     * @param act
     * @param memo
     * @return
     */
    @FormUrlEncoded
    @POST("datamanage.php/Admin/AppInterface/gatherstatus")
    Call<ZHBaseBean> changeCollectionStatus(@Field("user") String user, @Field("personid") String personid, @Field("act") String act, @Field("memo") String memo);

    /**
     * 新增住户接口
     */
    @Multipart
    @POST("datamanage.php/Admin/AppInterface/addhouseperson")
    Call<ZHAddhosePersonBean> addhouseperson(
            @Part MultipartBody.Part Company1,
            @Part MultipartBody.Part Company2,
            @Part MultipartBody.Part Company3,
            @Part MultipartBody.Part Company4,
            @Part MultipartBody.Part Company5,
            @Part MultipartBody.Part Company6,
            @Part MultipartBody.Part Company7,
            @Part MultipartBody.Part Company8,
            @Part MultipartBody.Part Company9,
            @Part MultipartBody.Part Company10,
            @Part MultipartBody.Part Company11
    )
    ;

    /**
     * 更新住户绑定房屋接口
     */
    @Multipart
    @POST("datamanage.php/Admin/AppInterface/addhousepersonconfirm")
    Call<ZHAddhosePersonBean> addhousepersonconfirm(
            @Part MultipartBody.Part Company1,
            @Part MultipartBody.Part Company2,
            @Part MultipartBody.Part Company3,
            @Part MultipartBody.Part Company4,
            @Part MultipartBody.Part Company5
    )
    ;

    /**
     * 查询身份证功能
     */
    @GET("datamanage.php/Admin/AppInterface/check_cardid")
    Call<CheckIdCardBean> checkIdCard(@Query("card_id") String card_id);

}
