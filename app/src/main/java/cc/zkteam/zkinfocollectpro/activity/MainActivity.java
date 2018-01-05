package cc.zkteam.zkinfocollectpro.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.activity.familyPlanningInfo.ConceptionControlActivity;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.activity.rentpersoninfo.mvp.test.ZK31Bean;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bd.ZKBDIDCardManager;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.dialog.OnZKDialogCancelListener;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragment;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;
import cc.zkteam.zkinfocollectpro.exception.ZKIdCardException;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiled;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFormLayout;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKeyValueLayout;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;
import cc.zkteam.zkinfocollectpro.view.kind.ZKModuleLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @BindView(R.id.btn_personal)
    Button btnPersonal;
    @BindView(R.id.home_btn)
    Button homeBtn;
    @BindView(R.id.map)
    Button map;
    @BindView(R.id.bd_access_token)
    Button bdAccessToken;
    @BindView(R.id.id_card)
    Button idCard;
    @BindView(R.id.camera_btn)
    Button cameraBtn;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.btn_problem_list)
    Button btnProblemList;
    @BindView(R.id.dialog)
    Button dialog;
    @BindView(R.id.zk_title_view)
    ZKTitleView zkTitleView;


    String picPatch;
    String pics1;
    String pics2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        testLayout();


        zkTitleView.setLeftIVSrc(R.drawable.icon_back);
        zkTitleView.setRightIVSrc(R.drawable.icon_about);

        zkTitleView.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("点击了标题栏左边的按钮");
            }
        });

        zkTitleView.centerTextTV.setText("旧主页面");

        zkTitleView.rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("点击了标题栏右边的按钮");
            }
        });
    }

    private void testLayout() {

        // TODO: 2017/12/29 请先把 aessts 目录下面的文件都放到 SD 卡的根目录下测试。
        String sdPath = Environment.getExternalStorageDirectory().getPath() + "/pics";
        copyFilesFassets(this, "pics", sdPath);


        String[] list = new String[]{"哈哈哈", "第一单位", "第二单位", "第三单位"};

        // 用户图像测试数据
        picPatch = sdPath + "/user_icon.jpeg";

        // 身份证测试数据

        pics1 = sdPath + "/id_card_z.png";
        pics2 = sdPath + "/id_card_f.png";

        String[] pics = new String[]{pics1, pics2};


        //——————————————————————————————————————————————
        //———————————————接口生成 ZKModuleLayout 模块控件———————————————————
        //——————————————————————————————————————————————
        LinearLayout linearLayout = findViewById(R.id.zk_root_view);

        showZKModuleInterface(linearLayout);


        //——————————————————————————————————————————————
        //———————————————ZKModuleLayout 模块控件———————————————————
        //——————————————————————————————————————————————
        ZKModuleLayout zkModuleLayout = findViewById(R.id.zk_test_module_layout);

        JSONArray moduleArray = null;
        try {
            moduleArray = new JSONArray();

            for (int i = 0; i < 8; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("item" + i, "hello" + i);
                moduleArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<Integer, Object> objectHashMap = new HashMap<>();

        List datas1 = new ArrayList();
        datas1.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
        datas1.add(list);
        objectHashMap.put(1, datas1);
        objectHashMap.put(3, ZKFiled.TYPE_FILED_FORM_TIME);

        List list2 = new ArrayList();
        list2.add(ZKFiled.TYPE_FILED_FORM_IMAGE);
        list2.add(picPatch);
        objectHashMap.put(5, list2);
        objectHashMap.put(7, ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON);

//        Map<Integer, Object> kindHashMap = new HashMap<>();
//        List titleList = new ArrayList();
//        titleList.add(ZKKindTitle.TYPE_FILED_FORM_IMAGE);
//        titleList.add(list);
//        kindHashMap.put(ZKKindTitle.TYPE_SINGLE_SELECT, titleList);

        List titleList = new ArrayList();
        titleList.add("我是小标题");
        titleList.add(ZKKindTitle.TYPE_BUTTON);
        titleList.add(list);

        zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);






        //——————————————————————————————————————————————
        //———————————————ZKFormLayout 自动填充列表控件———————————————————
        //——————————————————————————————————————————————
        JSONArray formArray = null;
        try {
            formArray = new JSONArray();

            for (int i = 0; i < 15; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("item" + i, "hello" + i);
                formArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<Integer, Object> formMap = new HashMap<>();


        formMap.put(1, ZKFiled.TYPE_FILED_FORM_EDIT_TEXT);

        List datas = new ArrayList();
        datas.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
        datas.add(list);
        formMap.put(10, datas);
        formMap.put(3, ZKFiled.TYPE_FILED_FORM_TIME);

        List imageData = new ArrayList();
        imageData.add(ZKFiled.TYPE_FILED_FORM_IMAGE);
        imageData.add(picPatch);
        formMap.put(13, imageData);
        formMap.put(5, ZKFiled.TYPE_FILED_FORM_DOUBLE_BUTTON);
        formMap.put(11, ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON);

        List idCardData = new ArrayList();
        idCardData.add(ZKFiled.TYPE_FILED_FORM_ID_CARD);
        idCardData.add(pics);
        formMap.put(7, idCardData);
        formMap.put(0, ZKFiled.TYPE_FILED_FORM_ID_CARD_NUMBER);

        ZKFormLayout zkTestFormLayout = findViewById(R.id.zk_test_form_layout);
        zkTestFormLayout.setJsonArray(formArray, formMap);


        //——————————————————————————————————————————————
        //———————————————KeyValueLayout 自动填充控件———————————————————
        //——————————————————————————————————————————————
        JSONArray keyValueArray = null;
        try {
            keyValueArray = new JSONArray();

            for (int i = 0; i < 5; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("姓名" + i, "小智" + i);
                keyValueArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ZKKeyValueLayout zkTestValueLayout = findViewById(R.id.zk_test_value_layout);
        zkTestValueLayout.setJsonArray(keyValueArray);


        //——————————————————————————————————————————————
        //———————————————Key-Value 控件———————————————————
        //——————————————————————————————————————————————
        ZKKeyValueFiledView zkTestKeyValueView = findViewById(R.id.zk_test_key_value_view);
        zkTestKeyValueView.setKeyValue("姓名", "小黑");


        //——————————————————————————————————————————————
        //———————————————大标题控件———————————————————
        //——————————————————————————————————————————————
        ZKKindTitle zkTestTitleView = findViewById(R.id.zk_test_title_view);

        zkTestTitleView.setFocusableInTouchMode(true);
//        zkTestTitleView.setTextTitle("我的大标题哦！");
        zkTestTitleView.setSingleSelectTitle("选择框标题", list);
        zkTestTitleView.setButtonTitle("按钮标题");


        //——————————————————————————————————————————————
        //———————————————数据收集控件———————————————————
        //——————————————————————————————————————————————
        ZKFiled zkTestView = findViewById(R.id.zk_test_view);
        zkTestView.setFocusableInTouchMode(true);

//        zkTestView.setData("1", "姓名", "", 1);
//        zkTestView.setData("2", "姓名", null, 2, ZKFiled.TYPE_FILED_FORM_TIME); //不需要默认值，如果需要，请重新设置
//        zkTestView.setData("3", "姓名", list, 3, ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
//        zkTestView.setData("4", "姓名", picPatch, 4, ZKFiled.TYPE_FILED_FORM_IMAGE);
//        zkTestView.setData("5", "姓名", "小Q", 5, ZKFiled.TYPE_FILED_FORM_DOUBLE_BUTTON);
//        zkTestView.setData("6", "姓名", null, 6, ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON);
//        zkTestView.setData("7", "姓名", pics, 7, ZKFiled.TYPE_FILED_FORM_ID_CARD);
//        zkTestView.setData("8", "姓名", "小Q", 8, ZKFiled.TYPE_FILED_FORM_ID_CARD_NUMBER);

//        zkTestView.setEditText("输入框");
//        zkTestView.setTime("时间表");
//        zkTestView.setSelectData("选择数据", list);
//        zkTestView.setImage("用户图片", picPatch);
//        zkTestView.setDoubleBtn("这是双按钮");
//        zkTestView.setDoubleTime("双时间显示");
//        zkTestView.setIdCard("身份证正反面", pics);
        zkTestView.setIdCardNumber("8", "身份证信息");


        //——————————————————————————————————————————————
        //———————————————获取以上结果的按钮———————————————————
        //——————————————————————————————————————————————
        Button btn = findViewById(R.id.btn);
        btn.setFocusableInTouchMode(true);
        btn.setOnClickListener(view -> {
            Map map = zkTestView.getResult();

            String result = (String) map.get(ZKFiled.ZK_FILED_NEW_VALUE);

            if (TextUtils.isEmpty(result)) {
                result = zkTestTitleView.getResult();
            }

            if (TextUtils.isEmpty(result)) {
                List<Map>  list1 = zkTestFormLayout.getResult();

                StringBuilder sb = new StringBuilder();
                for (Map map1: list1) {
                    sb.append((String) map.get(ZKFiled.ZK_FILED_NEW_VALUE));
                    sb.append(",");
                }

                result = sb.toString();
            }

            ToastUtils.showShort(result);
        });


        //——————————————————————————————————————————————
        //———————————————通用数据基本测试———————————————————
        //——————————————————————————————————————————————
        ZKKeyValueFiledView filedView = findViewById(R.id.filed_view);
        ZKKeyValueLayout fileLayoutView = findViewById(R.id.filed_layout_view);
        ZKFiled editFiledView = findViewById(R.id.eidt_filed_layout_view);
        ZKFormLayout zkFiledFormView = findViewById(R.id.edit_form_filed_view);
        ZKKindTitle zkKindTitle = findViewById(R.id.zk_kind_title);

//        zkKindTitle.setTextTitle("小小新的笔记");
//        zkKindTitle.setButtonTitle("小小新的笔记");
//        zkKindTitle.setButtonTitle("小小新的笔记", "SayHello");
//        zkKindTitle.setSingleSelectTitle("小小新的笔记", "否");
//        zkKindTitle.setSingleSelectTitle("小小新的笔记", null);

        filedView.setKeyValue("姓名", "小Q");

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();

            for (int i = 0; i < 5; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Key" + i, "Value" + i);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        fileLayoutView.setJsonArray(jsonArray);

        editFiledView.setData("1", "姓名", "小小兔", 0, ZKFiled.TYPE_FILED_FORM_TIME);

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, ZKFiled.TYPE_FILED_FORM_EDIT_TEXT);
        map.put(2, ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
        map.put(3, ZKFiled.TYPE_FILED_FORM_TIME);

        zkFiledFormView.setJsonArray(jsonArray, map);

    }

    private void showZKModuleInterface(LinearLayout linearLayout) {

        Context context = this;
        ZHConnectionManager.getInstance().getZHApi().get31Data("zhengzhaoxinxi_type", "2").enqueue(new Callback<ZK31Bean>() {

            @Override
            public void onResponse(Call<ZK31Bean> call, Response<ZK31Bean> result) {

                ZK31Bean zk31Bean = result.body();

                if (zk31Bean == null) {
                    L.e("zk31Bean is null!");
                    return;
                }

                List<ZK31Bean.DataBeanX> dataBeanList = zk31Bean.getData();




                for (ZK31Bean.DataBeanX dataBeanX :
                        dataBeanList) {

                    JSONArray moduleArray = new JSONArray();
                    Map<Integer, Object> objectHashMap = new HashMap<>();
                    ZKModuleLayout zkModuleLayout = new ZKModuleLayout(context);

                    for (ZK31Bean.DataBeanX.DataBean dataBean :
                            dataBeanX.getData()) {


                        ArrayList list = new ArrayList();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(dataBean.getNumber(), dataBean.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        moduleArray.put(jsonObject);

                        int type = Integer.parseInt(dataBean.getType());

//                    二级子数据，每一个 item。map 表示 右边需要的数据：
//     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
//     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
//     *                  --------第0项表示 当前右边的类型 的 type;
//     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。


                        list.add(type);
                        list.add(dataBean.getName());
                        // TODO: 2018/1/2 test
                        list.add(new String[] {pics1, pics2, "file://" + picPatch});

                        objectHashMap.put(0, list);


                    }


                    List titleList = new ArrayList();

                    titleList.add(dataBeanX.getName());
                    titleList.add(dataBeanX.getType());
//                    titleList.add(dataBean.getList()); // 这里如果有值的话，就是列表 String[]
                    //                zkModuleLayout.setJsonArray(moduleArray, null, titleList);
                    zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);


//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    linearLayout.addView(zkModuleLayout);
                }


//                add1View(result);
//                add2View(result);
//                add3View(result);
            }

//            private void add2View(ZK31Bean result) {
//                JSONArray moduleArray = new JSONArray();
//                ZK31Bean._$2Bean bean = result.get_$2();
//                ZKModuleLayout zkModuleLayout = new ZKModuleLayout(context);
//
//                Map<Integer, Object> objectHashMap = new HashMap<>();
//                for (int i = 0; i < bean.getData().size(); i++) {
//                    ArrayList list = new ArrayList();
//
//                    ZK31Bean._$2Bean.DataBeanX dataBeans = bean.getData().get(i);
//
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put(dataBeans.getNumber(), dataBeans.getName());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    moduleArray.put(jsonObject);
//
//                    int type = 0;
//
//                    List datas1 = new ArrayList();
//
//                    if (dataBeans.getType().equals("TYPE_FILED_FORM_EDIT_TEXT")) {
//                        type = ZKFiled.TYPE_FILED_FORM_SELECT_DATA;
//                    }
//
//                    switch (dataBeans.getType()) {
//                        case "TYPE_FILED_FORM_EDIT_TEXT":
//                            type = ZKFiled.TYPE_FILED_FORM_EDIT_TEXT;
//                            break;
//                        case "TYPE_FILED_FORM_SELECT_DATA":
//                            type = ZKFiled.TYPE_FILED_FORM_SELECT_DATA;
//                            break;
//                        case "TYPE_FILED_FORM_TIME":
//                            type = ZKFiled.TYPE_FILED_FORM_TIME;
//                            break;
//                        case "TYPE_FILED_FORM_IMAGE":
//                            type = ZKFiled.TYPE_FILED_FORM_IMAGE;
//                            break;
//                        case "TYPE_FILED_FORM_DOUBLE_BUTTON":
//                            type = ZKFiled.TYPE_FILED_FORM_DOUBLE_BUTTON;
//                            break;
//                        case "TYPE_FILED_FORM_TWO_TIME_BUTTON":
//                            type = ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON;
//                            break;
//                        case "TYPE_FILED_FORM_ID_CARD":
//                            type = ZKFiled.TYPE_FILED_FORM_ID_CARD;
//                            String sdPath = Environment.getExternalStorageDirectory().getPath() + "/pics";
//                            String picPatch = sdPath + "/user_icon.jpeg";
//                            String pics1 = sdPath + "/id_card_z.png";
//                            String pics2 = sdPath + "/id_card_f.png";
//
//                            list.add(type);
//                            list.add(dataBeans.getName());
//                            list.add(new String[]{pics1, pics2});
//                            break;
//                        case "TYPE_FILED_FORM_ID_CARD_NUMBER":
//                            type = ZKFiled.TYPE_FILED_FORM_ID_CARD_NUMBER;
//                            break;
//                    }
////                    二级子数据，每一个 item。map 表示 右边需要的数据：
////     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
////     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
////     *                  --------第0项表示 当前右边的类型 的 type;
////     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。
//
//
//                    list.add(type);
//                    list.add(dataBeans.getName());
//
//                    objectHashMap.put(i, list);
//                }
//
//
////                datas1.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
//////                datas1.add(list);
////                objectHashMap.put(1, datas1);
////                objectHashMap.put(3, ZKFiled.TYPE_FILED_FORM_TIME);
//
//                List titleList = new ArrayList();
//
//                titleList.add(bean.getName());
//                if (bean.getType().equals("text")) {
//                    titleList.add("1");
//                }
////                titleList.add(bean.getList()); // 这里如果有值的话，就是列表 String[]
//
//
////                zkModuleLayout.setJsonArray(moduleArray, null, titleList);
//                zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                linearLayout.addView(zkModuleLayout, params);
//            }
//
//            private void add3View(ZK31Bean result) {
//                JSONArray moduleArray = new JSONArray();
//                ZK31Bean._$3Bean bean = result.get_$3();
//                ZKModuleLayout zkModuleLayout = new ZKModuleLayout(context);
//
//                Map<Integer, Object> objectHashMap = new HashMap<>();
//                for (int i = 0; i < bean.getData().size(); i++) {
//                    ArrayList list = new ArrayList();
//
//                    ZK31Bean._$3Bean.DataBeanXX dataBeans = bean.getData().get(i);
//
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put(dataBeans.getNumber(), dataBeans.getName());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    moduleArray.put(jsonObject);
//
//                    int type = 0;
//
//                    List datas1 = new ArrayList();
//
//                    if (dataBeans.getType().equals("TYPE_FILED_FORM_EDIT_TEXT")) {
//                        type = ZKFiled.TYPE_FILED_FORM_SELECT_DATA;
//                    }
//
//                    switch (dataBeans.getType()) {
//                        case "TYPE_FILED_FORM_EDIT_TEXT":
//                            type = ZKFiled.TYPE_FILED_FORM_EDIT_TEXT;
//                            break;
//                        case "TYPE_FILED_FORM_SELECT_DATA":
//                            type = ZKFiled.TYPE_FILED_FORM_SELECT_DATA;
//                            break;
//                        case "TYPE_FILED_FORM_TIME":
//                            type = ZKFiled.TYPE_FILED_FORM_TIME;
//                            break;
//                        case "TYPE_FILED_FORM_IMAGE":
//                            type = ZKFiled.TYPE_FILED_FORM_IMAGE;
//                            break;
//                        case "TYPE_FILED_FORM_DOUBLE_BUTTON":
//                            type = ZKFiled.TYPE_FILED_FORM_DOUBLE_BUTTON;
//                            break;
//                        case "TYPE_FILED_FORM_TWO_TIME_BUTTON":
//                            type = ZKFiled.TYPE_FILED_FORM_TWO_TIME_BUTTON;
//                            break;
//                        case "TYPE_FILED_FORM_ID_CARD":
//                            type = ZKFiled.TYPE_FILED_FORM_ID_CARD;
//                            String sdPath = Environment.getExternalStorageDirectory().getPath() + "/pics";
//                            String picPatch = sdPath + "/user_icon.jpeg";
//                            String pics1 = sdPath + "/id_card_z.png";
//                            String pics2 = sdPath + "/id_card_f.png";
//
//                            list.add(type);
//                            list.add(dataBeans.getName());
//                            list.add(new String[]{pics1, pics2});
//                            break;
//                        case "TYPE_FILED_FORM_ID_CARD_NUMBER":
//                            type = ZKFiled.TYPE_FILED_FORM_ID_CARD_NUMBER;
//                            break;
//                    }
////                    二级子数据，每一个 item。map 表示 右边需要的数据：
////     *                  ----1. 直接是数字，表示 右边的控件类型的 type.
////     *                  ----2. ArrayList 表示右边的控件类型需要默认数据：
////     *                  --------第0项表示 当前右边的类型 的 type;
////     *                  --------第1项表示 当前右边的类型 需要的默认数据，是一组数据列表。
//
//
//                    list.add(type);
//                    list.add(dataBeans.getName());
//
//                    objectHashMap.put(i, list);
//                }
//
//
////                datas1.add(ZKFiled.TYPE_FILED_FORM_SELECT_DATA);
//////                datas1.add(list);
////                objectHashMap.put(1, datas1);
////                objectHashMap.put(3, ZKFiled.TYPE_FILED_FORM_TIME);
//
//                List titleList = new ArrayList();
//
//                titleList.add(bean.getName());
//                if (bean.getType().equals("text")) {
//                    titleList.add("1");
//                }
////                titleList.add(bean.getList()); // 这里如果有值的话，就是列表 String[]
//
//
////                zkModuleLayout.setJsonArray(moduleArray, null, titleList);
//                zkModuleLayout.setJsonArray(moduleArray, objectHashMap, titleList);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                linearLayout.addView(zkModuleLayout, params);
//            }

            @Override
            public void onFailure(Call<ZK31Bean> call, Throwable throwable) {

                L.e("onFailure: ", throwable);
            }
        });
    }

    @Override
    protected void initListener() {
        findViewById(R.id.btn_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PersonalInfoCollectActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

        verifyStoragePermissions(this);

        ZKBase.getSDCardPath();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (data != null && requestCode == 100) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        BDIdCardBean.WordsResultBean wordsResultBean = (BDIdCardBean.WordsResultBean) bundle.getSerializable(IDCardScanActivity.KEY_ID_CARD_INFO_BEAN);

                        if (wordsResultBean != null) {
                            String name = wordsResultBean.getName().getWords();
                            L.i("扫描的姓名是；" + name);
                            ToastUtils.showShort("扫描的姓名是：" + name);
                        }
                    }
                }
                break;
        }

    }

    @OnClick({R.id.home_btn, R.id.map, R.id.bd_access_token,
            R.id.id_card, R.id.camera_btn, R.id.btn_problem_list, R.id.dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_btn:
                PageCtrl.startActivity(MainActivity.this, HomeActivity.class);
                break;
            case R.id.map:
                PageCtrl.startActivity(MainActivity.this, MapActivity.class);
                break;
            case R.id.bd_access_token:
                getBDAccessToken();
                break;
            case R.id.id_card:
                try {
                    getIdCardInfo();
                } catch (ZKIdCardException e) {
                    e.printStackTrace();
                    ToastUtils.showShort("出现异常原因：" + e.getMessage());
                }
                break;
            case R.id.camera_btn:
//                身份证识别地址
//                http://ai.baidu.com/tech/ocr/cards

                // TODO: 2017/12/24  启动身份证扫描页面，得到结果集合
                Intent intent = new Intent(this, IDCardScanActivity.class);
                startActivityForResult(intent, 100);

                break;
            case R.id.btn_problem_list:
                PageCtrl.startActivity(MainActivity.this, ConceptionControlActivity.class);
                break;
            case R.id.dialog:

//                ZKDialogFragment dialogFragment = ZKDialogFragmentHelper.showSingleBtnDialog(getSupportFragmentManager(),
//                        "hello",
//                        "我是内容",
//                        new ZKDialogResultListener<Integer>() {
//                            @Override
//                            public void onDataResult(Integer result) {
//
//                                switch (result) {
//                                    case DialogInterface.BUTTON_POSITIVE: //确定
//                                        ToastUtils.showShort("确定");
//                                        break;
//                                    case DialogInterface.BUTTON_NEGATIVE: // 取消
//                                        ToastUtils.showShort("取消");
//                                        break;
//                                }
//                            }
//                        }, new OnZKDialogCancelListener() {
//                            @Override
//                            public void onCancel() {
//                                ToastUtils.showShort("取消了本次操作");
//                            }
//                        });

                ZKDialogFragment dialogFragment = ZKDialogFragmentHelper.showDialog(getSupportFragmentManager(),
                        "hello",
                        "我是内容",
                        new ZKDialogResultListener<Integer>() {
                            @Override
                            public void onDataResult(Integer result) {

                                switch (result) {
                                    case DialogInterface.BUTTON_POSITIVE: //确定
                                        ToastUtils.showShort("确定");
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE: // 取消
                                        ToastUtils.showShort("取消");
                                        break;
                                }
                            }
                        }, new OnZKDialogCancelListener() {
                            @Override
                            public void onCancel() {
                                ToastUtils.showShort("取消了本次操作");
                            }
                        });


                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.dismissAllowingStateLoss();
                    }
                }, 3000);

//                TestDialogManager testDialogManager = TestDialogManager.getInstance(getSupportFragmentManager());
//                testDialogManager.showConfirmDialog();
//                testDialogManager.showDateDialog();
//                testDialogManager.showInsertDialog();
//                testDialogManager.showListDialog();
//                testDialogManager.showPasswordInsertDialog();
//                ZKDialogFragmentHelper.showProgress(getSupportFragmentManager(), "正在加载中");
//                testDialogManager.showTimeDialog();
//                ZKDialogFragmentHelper.showTips(getSupportFragmentManager(), "你进入了无网的异次元中");


                break;
        }
    }

    /**
     * 务必确保 SD 卡有这个测试身份证的文件：/xiaotieie_id_card.png
     */
    private void getIdCardInfo() throws ZKIdCardException {
//        准备添加 身份证失败：http://ai.baidu.com/docs#/OCR-API/top
        String localPicPath = Environment.getExternalStorageDirectory().getPath() + "/xiaotieie_id_card.png";

        ZKBDIDCardManager.getInstance().getIdCardInfo(localPicPath, new Callback<BDIdCardBean>() {
            @Override
            public void onResponse(Call<BDIdCardBean> call, Response<BDIdCardBean> response) {
                L.d("onResponse() called with: call = [" + call + "], response = [" + response + "]");
                BDIdCardBean bdIdCardBean = response.body();
                if (bdIdCardBean != null) {
                    BDIdCardBean.WordsResultBean wordsResultBean = bdIdCardBean.getWords_result();

                    if (wordsResultBean != null) {
                        // TODO: 2017/12/15  在这里处理数据格式，并返回给主界面
                    }
                }
            }

            @Override
            public void onFailure(Call<BDIdCardBean> call, Throwable t) {
                L.e("onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    public void getBDAccessToken() {
        ZKBDIDCardManager.getInstance().refreshAccessToken();
    }

    public void verifyStoragePermissions(Activity activity) {
        PermissionUtils.requestPermissions(activity, 200, PERMISSIONS_STORAGE, new PermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                ToastUtils.showShort("用户禁用了一些权限：" + deniedPermissions.toString());

            }
        });
    }


    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
