package cc.zkteam.zkinfocollectpro.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bd.ZKBDIDCardManager;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.dialog.OnZKDialogCancelListener;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragment;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;
import cc.zkteam.zkinfocollectpro.exception.ZKIdCardException;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKEditFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledFormView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledLayoutView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKFiledView;
import cc.zkteam.zkinfocollectpro.view.kind.ZKKindTitle;
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
        ZKFiledView filedView = findViewById(R.id.filed_view);
        ZKFiledLayoutView fileLayoutView = findViewById(R.id.filed_layout_view);
        ZKEditFiledView editFiledView = findViewById(R.id.eidt_filed_layout_view);
        ZKFiledFormView zkFiledFormView = findViewById(R.id.edit_form_filed_view);
        ZKKindTitle zkKindTitle = findViewById(R.id.zk_kind_title);

//        zkKindTitle.setTextTitle("小小新的笔记");
//        zkKindTitle.setButtonTitle("小小新的笔记");
//        zkKindTitle.setButtonTitle("小小新的笔记", "SayHello");
        zkKindTitle.setSingleSelectTitle("小小新的笔记", "否");
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

        editFiledView.setData("1", "姓名", "小小兔", 0, ZKEditFiledView.TYPE_FILED_FORM_TIME);

        Map<Integer, Integer> map = new HashMap<>();

        map.put(1, ZKEditFiledView.TYPE_FILED_FORM_EDIT_TEXT);
        map.put(2, ZKEditFiledView.TYPE_FILED_FORM_SELECT_DATA);
        map.put(3, ZKEditFiledView.TYPE_FILED_FORM_TIME);

        zkFiledFormView.setJsonArray(jsonArray, map);

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
                PageCtrl.startActivity(MainActivity.this, RentPersonInfoActivity.class);
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
                PageCtrl.startActivity(MainActivity.this, MyProblemListActivity.class);
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


}
