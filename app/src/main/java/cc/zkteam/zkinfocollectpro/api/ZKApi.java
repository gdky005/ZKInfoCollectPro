package cc.zkteam.zkinfocollectpro.api;

import java.util.List;

import cc.zkteam.zkinfocollectpro.bean.BaseBean;
import cc.zkteam.zkinfocollectpro.bean.CategoryBean;
import retrofit2.Call;
import retrofit2.http.GET;
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

}
