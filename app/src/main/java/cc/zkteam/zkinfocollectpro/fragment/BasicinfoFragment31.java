package cc.zkteam.zkinfocollectpro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicinfoFragment31 extends BaseFragment {


    @BindView(R.id.rel)
    RelativeLayout rel;
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

    @BindView(R.id.savecommit)
    Button savecommit;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basicinfo_fragment7;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        id1.setEditText("1", "身份证号");
        id2.setEditText("2", "姓名");
        id3.setSelectData("3", "性别", new String[]{"男", "女"});
        id4.setTime("4", "出生日期");
        id5.setSelectData("5", "所在省",new String[]{"男", "女"});
        id6.setSelectData("6", "所在市",new String[]{"男", "女"});
        id7.setSelectData("7", "所在区县",new String[]{"男", "女"});
        id8.setSelectData("8", "所在乡镇",new String[]{"男", "女"});
        id9.setEditText("9", "房屋详细地址");
        id10.setEditText("10", "权属证书编号");
        id11.setEditText("11", "房屋性质");
        id12.setEditText("12", "规划用地");
        id13.setEditText("13", "丘(地)号");
        id14.setEditText("14", "建筑面积(㎡)");
        id15.setEditText("15", "室内面积(㎡)");
        id16.setTime("15", "登记日期");
        id17.setEditText("17", "户型");
        id18.setSelectData("18", "房屋状态",new String[]{"空置","自住","出租","商用","其他"});


    }

    @Override
    public void initListener() {

    }




    @OnClick(R.id.savecommit)
    public void onViewClicked() {
        ToastUtils.showShort("保存成功");

    }
}
