package cc.zkteam.zkinfocollectpro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicinfoFragment30 extends BaseFragment {


    @BindView(R.id.rel1_image)
    ImageView rel1Image;
    @BindView(R.id.rel1)
    RelativeLayout rel1;
    @BindView(R.id.id1)
    ZKFiled id1;
    @BindView(R.id.id2)
    ZKFiled id2;
    @BindView(R.id.id3)
    ZKFiled id3;
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
    @BindView(R.id.id22)
    ZKFiled id22;
    @BindView(R.id.id23)
    ZKFiled id23;
    @BindView(R.id.id24)
    ZKFiled id24;
    @BindView(R.id.rel1_item)
    LinearLayout rel1Item;
    @BindView(R.id.rel2_image)
    ImageView rel2Image;
    @BindView(R.id.rel2)
    RelativeLayout rel2;
    @BindView(R.id.id2_1)
    ZKFiled id2_1;
    @BindView(R.id.id2_2)
    ZKFiled id2_2;
    @BindView(R.id.id2_3)
    ZKFiled id2_3;
    @BindView(R.id.id2_4)
    ZKFiled id2_4;
    @BindView(R.id.id2_5)
    ZKFiled id25;
    @BindView(R.id.id2_6)
    ZKFiled id26;
    @BindView(R.id.id2_7)
    ZKFiled id27;
    @BindView(R.id.id2_8)
    ZKFiled id28;
    @BindView(R.id.rel3_item)
    LinearLayout rel3Item;
    @BindView(R.id.rel3_image)
    ImageView rel3Image;
    @BindView(R.id.rel3)
    RelativeLayout rel3;
    @BindView(R.id.id3_1)
    ZKFiled id31;
    @BindView(R.id.id3_2)
    ZKFiled id32;
    @BindView(R.id.id3_3)
    ZKFiled id33;
    @BindView(R.id.id3_4)
    ZKFiled id34;
    @BindView(R.id.id3_5)
    ZKFiled id35;
    @BindView(R.id.id3_6)
    ZKFiled id36;
    @BindView(R.id.id3_7)
    ZKFiled id37;
    @BindView(R.id.id3_8)
    ZKFiled id38;
    @BindView(R.id.rel4_item)
    LinearLayout rel4Item;
    @BindView(R.id.rel4_image)
    ImageView rel4Image;
    @BindView(R.id.rel4)
    RelativeLayout rel4;
    @BindView(R.id.id4_1)
    ZKFiled id41;
    @BindView(R.id.id4_2)
    ZKFiled id42;
    @BindView(R.id.id4_3)
    ZKFiled id43;
    @BindView(R.id.id4_4)
    ZKFiled id44;
    @BindView(R.id.id4_5)
    ZKFiled id45;
    @BindView(R.id.id4_6)
    ZKFiled id46;
    @BindView(R.id.id4_7)
    ZKFiled id47;
    @BindView(R.id.id4_8)
    ZKFiled id48;
    @BindView(R.id.id4_9)
    ZKFiled id49;
    @BindView(R.id.rel5_item)
    LinearLayout rel5Item;
    @BindView(R.id.rel5_image)
    ImageView rel5Image;
    @BindView(R.id.rel5)
    RelativeLayout rel5;
    @BindView(R.id.id5_1)
    ZKFiled id51;
    @BindView(R.id.id5_2)
    ZKFiled id52;
    @BindView(R.id.id5_3)
    ZKFiled id53;
    @BindView(R.id.id5_4)
    ZKFiled id54;
    @BindView(R.id.id5_5)
    ZKFiled id55;
    @BindView(R.id.id5_6)
    ZKFiled id56;
    @BindView(R.id.id5_7)
    ZKFiled id57;
    @BindView(R.id.id5_8)
    ZKFiled id58;
    @BindView(R.id.id5_9)
    ZKFiled id59;
    @BindView(R.id.id5_10)
    ZKFiled id510;
    @BindView(R.id.rel6_item)
    LinearLayout rel6Item;
    @BindView(R.id.rel6_image)
    ImageView rel6Image;
    @BindView(R.id.rel6)
    RelativeLayout rel6;
    @BindView(R.id.id6_1)
    ZKFiled id61;
    @BindView(R.id.id6_2)
    ZKFiled id62;
    @BindView(R.id.id6_3)
    ZKFiled id63;
    @BindView(R.id.id6_4)
    ZKFiled id64;
    @BindView(R.id.id6_5)
    ZKFiled id65;
    @BindView(R.id.id6_6)
    ZKFiled id66;
    @BindView(R.id.id6_7)
    ZKFiled id67;
    @BindView(R.id.id6_8)
    ZKFiled id68;
    @BindView(R.id.id6_9)
    ZKFiled id69;
    @BindView(R.id.rel2_item)
    LinearLayout rel2Item;
    @BindView(R.id.savecommit)
    Button savecommit;
    Unbinder unbinder;
    Unbinder unbinder1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basicinfo_fragment6;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        id1.setEditText("1", "身份证号");
        id2.setEditText("2", "姓名");
        id3.setSelectData("3", "性别", new String[]{"男", "女"});
        id4.setEditText("4", "车牌号码");
        id5.setEditText("5", "车辆类型");
        id6.setEditText("6", "使用性质");
        id7.setTime("7", "购车时间");
        id8.setEditText("8", "购买价格");
        id9.setEditText("9", "卖车单位");
        id10.setEditText("10", "品牌型号");
        id11.setEditText("11", "发动机排量");
        id12.setEditText("12", "油箱容积");
        id13.setEditText("13", "整备质量");
        id14.setEditText("14", "核定载人数");
        id15.setEditText("15", "车体颜色");
        id16.setEditText("16", "总质量");
        id17.setEditText("17", "变速器形式");
        id18.setSelectData("18", "涡轮增压", new String[]{"是", "否"});
        id19.setEditText("19", "发动机号码");
        id20.setEditText("20", "保养周期");
        id21.setEditText("21", "车辆识别代码");
        id22.setTime("22", "注册登记日期");
        id23.setTime("23", "发证日期");
        id24.setTime("24", "年检时间");
        id2_1.setEditText("1", "身份证号");
        id2_2.setEditText("2", "姓名");
        id2_3.setSelectData("3", "性别", new String[]{"男", "女"});
        id2_4.setTime("4", "保险时间");
        id25.setTime("5", "保险种类");
        id26.setTime("6", "保险费用");
        id27.setTime("7", "保险单号");
        id28.setTime("8", "保险公司");
        id31.setEditText("1", "身份证号");
        id32.setEditText("2", "姓名");
        id33.setSelectData("3", "性别", new String[]{"男", "女"});
        id34.setTime("4", "年检时间");
        id35.setEditText("5", "年检代理人(上一次)");
        id36.setTime("6", "本车应检日期(下一次应该捡车的时期)");
        id37.setEditText("7", "年检的有效期");
        id38.setEditText("8", "年检费用");
        id41.setEditText("1", "身份证号");
        id42.setEditText("2", "姓名");
        id43.setSelectData("3", "性别", new String[]{"男", "女"});
        id44.setTime("4", "保养时间");
        id45.setEditText("5", "保养人(上一次送保的车主)");
        id46.setEditText("6", "保养里程数");
        id47.setEditText("7", "保养项目");
        id48.setEditText("8", "保养费用");
        id49.setEditText("9", "保养服务单位");
        id51.setEditText("1", "身份证号");
        id52.setEditText("2", "姓名");
        id53.setSelectData("3", "性别", new String[]{"男", "女"});
        id54.setTime("4", "维修时间");
        id55.setEditText("5", "送修人");
        id56.setEditText("6", "维修费");
        id57.setEditText("7", "维修项目");
        id58.setEditText("8", "修理厂");
        id59.setEditText("9", "送修里程");
        id510.setEditText("10", "送修原因");
        id61.setEditText("1", "身份证号");
        id62.setEditText("2", "姓名");
        id63.setSelectData("3", "性别", new String[]{"男", "女"});
        id64.setTime("4", "事故发生时间");
        id65.setEditText("5", "交通事故发生地点 ");
        id66.setEditText("6", "交通事故性质");
        id67.setEditText("7", "驾驶人");
        id68.setEditText("8", "交通事故责任");
        id69.setEditText("9", "车辆损坏情况");
    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.rel1_image, R.id.rel1, R.id.rel2_image, R.id.rel2, R.id.rel3_image, R.id.rel3, R.id.rel4_image, R.id.rel4, R.id.rel5_image, R.id.rel5, R.id.rel6_image, R.id.rel6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel1_image:
            case R.id.rel1:
                rel1Item.setVisibility(rel1Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.rel2:
            case R.id.rel2_image:
                rel2Item.setVisibility(rel2Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.rel3:
            case R.id.rel3_image:
                rel3Item.setVisibility(rel3Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.rel4:
            case R.id.rel4_image:
                rel4Item.setVisibility(rel4Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.rel5:
            case R.id.rel5_image:
                rel5Item.setVisibility(rel5Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.rel6:
            case R.id.rel6_image:
                rel6Item.setVisibility(rel6Item.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                break;

        }
    }



    @OnClick(R.id.savecommit)
    public void onViewClicked() {
        ToastUtils.showShort("保存成功");

    }
}
