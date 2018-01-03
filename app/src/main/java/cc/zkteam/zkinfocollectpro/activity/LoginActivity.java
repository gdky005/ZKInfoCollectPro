package cc.zkteam.zkinfocollectpro.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.networkbench.agent.impl.NBSAppAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.api.ZHApi;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.bean.ZHLoginBean;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.managers.ZHMemoryCacheManager;
import cc.zkteam.zkinfocollectpro.retrofit2.ZHCallback;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

/**
 * Created by Administrator on 2017/12/15.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.et_userNum)
    EditText etUserNum;
    @BindView(R.id.Ll_userNum)
    LinearLayout LlUserNum;
    @BindView(R.id.et_userPwd)
    EditText et_pwd;
    @BindView(R.id.Ll_userPwd)
    LinearLayout LlUserPwd;
    @BindView(R.id.textviewlayout)
    RelativeLayout textviewlayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ZHApi zhApi;
    private boolean isHidePwd = true;// 输入框密码是否是隐藏的，默认为true


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initViews() {
        progressBar.setVisibility(View.GONE);
//        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
//        systemBarTintManager.setStatusBarTintEnabled(false);
//        systemBarTintManager.setNavigationBarTintEnabled(false);


        hideOrShowPwd();
    }

    /**
     * 点击眼睛控制密码显示或隐藏
     */
    private void hideOrShowPwd() {
        final Drawable drawableEyeOpen = getResources().getDrawable(R.mipmap.login_eyes);
        drawableEyeOpen.setBounds(0,0,60,60);//这一步不能省略
        et_pwd.setCompoundDrawables(null,null,drawableEyeOpen,null);
        final Drawable[] drawables = et_pwd.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();// 眼睛图标的宽度
        et_pwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // getWidth,getHeight必须在这里处理
                    float et_pwdMinX = v.getWidth() - eyeWidth - et_pwd.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();
                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHidePwd = !isHidePwd;
                        if (isHidePwd) {

                            et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        NBSAppAgent.setLicenseKey("b8f35200fae249148ccac88c105c5bcd").withLocationServiceEnabled(true).start(this.getApplicationContext());

//        // TODO: 2017/12/23  这是测试代码哦
//        btnSubmit.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                PageCtrl.startActivity( LoginActivity.this, HomeActivity.class);
//                finish();
//            }
//        }, 3000);
//        ToastUtils.showLong("默认三秒后自动进入主页 [调试状态]");

        zhApi = ZHConnectionManager.getInstance().getZHApi();


    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        progressBar.setVisibility(View.VISIBLE);

        String userNum = etUserNum.getText().toString();
        String userPwd = EncodeUtils.urlEncode(et_pwd.getText().toString());
//        String userPwd = et_pwd.getText().toString();

        zhApi.login(userNum, userPwd).enqueue(new ZHCallback<ZHLoginBean>() {
            @Override
            public void onResponse(ZHBaseBean<ZHLoginBean> baseBean, ZHLoginBean result) {
                progressBar.setVisibility(View.GONE);
                if (result != null) {
                    L.d("onResponse: " + result.toString());


                    if (1 == baseBean.getStatus()) {
                        ZKICApplication.zhLoginBean = result;
                        ZHMemoryCacheManager.getInstance().setUserInfo(result);

                        PageCtrl.startActivity(LoginActivity.this, HomeActivity.class);
                        finish();
                        return;
                    }
                }

                ToastUtils.showShort(baseBean.getMsg());
            }

            @Override
            public void onFailure(Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.showShort(throwable.getMessage());
            }
        });

    }
}
