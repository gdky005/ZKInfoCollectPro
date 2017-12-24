package cc.zkteam.zkinfocollectpro.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bd.ZKBDIDCardManager;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.dialog.OnZKDialogCancelListener;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragment;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogFragmentHelper;
import cc.zkteam.zkinfocollectpro.dialog.ZKDialogResultListener;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_btn)
    Button homeBtn;
    @BindView(R.id.bd_access_token)
    Button bdAccessToken;
    @BindView(R.id.map)
    Button map;
    @BindView(R.id.id_card)
    Button idCard;
    @BindView(R.id.camera_btn)
    Button cameraBtn;
    @BindView(R.id.dialog)
    Button dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
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
    }


    @OnClick({R.id.toolbar, R.id.home_btn, R.id.map, R.id.bd_access_token,
            R.id.id_card, R.id.camera_btn, R.id.btn_problem_list, R.id.dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                break;
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
                getIdCardInfo();
                break;
            case R.id.camera_btn:

//                身份证识别地址
//                http://ai.baidu.com/tech/ocr/cards

                PageCtrl.startActivity(MainActivity.this, IDCardScanActivity.class);
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

    private void getIdCardInfo() {
//        准备添加 身份证失败：http://ai.baidu.com/docs#/OCR-API/top
        String localPicPath = Environment.getExternalStorageDirectory().getPath() + "/xiaotieie_id_card.png";

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
