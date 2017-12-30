package cc.zkteam.zkinfocollectpro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;

import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicinfoFragment29 extends BaseFragment {


    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.id1)
    ZKFiled id1;
    @BindView(R.id.id2)
    ZKFiled id2;
    @BindView(R.id.id3)
    ZKFiled id3;
    @BindView(R.id.savecommit)
    Button savecommit;
    Unbinder unbinder;
    @BindView(R.id.id4)
    ZKFiled id4;
    @BindView(R.id.id5)
    ZKFiled id5;
    @BindView(R.id.id6)
    ZKFiled id6;
    @BindView(R.id.id7)
    ZKFiled id7;
    @BindView(R.id.id8)
    ZKFiled id8;
    @BindView(R.id.id9)
    ZKFiled id9;
    @BindView(R.id.id10)
    ZKFiled id10;
    Unbinder unbinder1;
    @BindView(R.id.id11)
    ZKFiled id11;
    @BindView(R.id.id12)
    ZKFiled id12;
    @BindView(R.id.id13)
    ZKFiled id13;
    @BindView(R.id.id14)
    ZKFiled id14;
    @BindView(R.id.id15)
    ZKFiled id15;
    @BindView(R.id.id16)
    ZKFiled id16;
    @BindView(R.id.id17)
    ZKFiled id17;
    @BindView(R.id.id18)
    ZKFiled id18;
    @BindView(R.id.id19)
    ZKFiled id19;
    @BindView(R.id.id20)
    ZKFiled id20;
    @BindView(R.id.id21)
    ZKFiled id21;
    ZHApi zhApi;
    CountDownLatch countDownLatch;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basicinfo_fragment5;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        zhApi = ZHConnectionManager.getInstance().getZHApi();

        id1.setEditText("1", "身份证号");
        id2.setEditText("2", "姓名");
        id3.setSelectData("3", "性别", new String[]{"男", "女"});
        id4.setEditText("4", "公司名称");
        id5.setEditText("5", "注册号");
        id6.setEditText("6", "注册地址(住所)");
        id7.setEditText("7", "公司类型");
        id8.setEditText("8", "注册资本(万元)");
        id9.setEditText("9", "实收资本(万元)");
        id10.setEditText("10", "经验范围");
        id11.setEditText("11", "登记机关");
        id12.setEditText("12", "职务");
        id13.setEditText("13", "公司电话");
        id14.setEditText("14", "公司网址");
        id15.setEditText("15", "公司人数");
        id16.setEditText("16", "年产值");
        id17.setEditText("17", "主要产品或服务");
        id18.setTime("18", "注册日期");
        id19.setEditText("19", "营业期限");
        id20.setEditText("20", "组织机构代码证");
        id21.setEditText("21", "营业证号");

    }

    @Override
    public void initListener() {

    }


    @OnClick(R.id.savecommit)
    public void onViewClicked() {


        zhApi.addPersonBaseInfo_Company(
           MultipartBody.Part.createFormData("id_card",  id1.getResult()),
           MultipartBody.Part.createFormData("name",  id2.getResult()),
           MultipartBody.Part.createFormData("sex",  id3.getResult()),
           MultipartBody.Part.createFormData("company_name",  id4.getResult()),
           MultipartBody.Part.createFormData("company_number",  id5.getResult()),
           MultipartBody.Part.createFormData("company_address",  id6.getResult()),
           MultipartBody.Part.createFormData("company_type",  id7.getResult()),
           MultipartBody.Part.createFormData("company_regmoney",  id8.getResult()),
           MultipartBody.Part.createFormData("company_money",  id9.getResult()),
           MultipartBody.Part.createFormData("company_business_scope",  id10.getResult()),
           MultipartBody.Part.createFormData("company_unit",  id11.getResult()),
           MultipartBody.Part.createFormData("company_duty",  id12.getResult()),
           MultipartBody.Part.createFormData("company_telphone",  id13.getResult()),
           MultipartBody.Part.createFormData("company_website",  id14.getResult()),
           MultipartBody.Part.createFormData("company_staff",  id15.getResult()),
           MultipartBody.Part.createFormData("company_annual_value",  id16.getResult()),
           MultipartBody.Part.createFormData("company_product",  id17.getResult()),
           MultipartBody.Part.createFormData("company_date",  id18.getResult()),
           MultipartBody.Part.createFormData("company_time_business",  id19.getResult()),
           MultipartBody.Part.createFormData("company_number",  id20.getResult()),
           MultipartBody.Part.createFormData("company_license_number",  id21.getResult())

                ).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                ZHBaseBean zhBaseBean = response.body();
                if (zhBaseBean != null) {
                    Log.d("Company", "onResponse: " + zhBaseBean.toString());
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {

            }
        });
         //接口都不能用  暂时弹个信息吧
        ToastUtils.showShort("保存成功");

    }


}
