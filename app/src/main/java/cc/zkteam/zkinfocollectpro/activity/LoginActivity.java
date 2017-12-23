package cc.zkteam.zkinfocollectpro.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.home.HomeActivity;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.utils.PageCtrl;

/**
 * Created by Administrator on 2017/12/15.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.right_icon)
    ImageView rightIcon;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.et_userNum)
    EditText etUserNum;
    @BindView(R.id.Ll_userNum)
    LinearLayout LlUserNum;
    @BindView(R.id.et_userPwd)
    EditText etUserPwd;
    @BindView(R.id.Ll_userPwd)
    LinearLayout LlUserPwd;
    @BindView(R.id.textviewlayout)
    RelativeLayout textviewlayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
//        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
//        systemBarTintManager.setStatusBarTintEnabled(false);
//        systemBarTintManager.setNavigationBarTintEnabled(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        // TODO: 2017/12/23  这是测试代码哦
        btnSubmit.postDelayed(new Runnable() {
            @Override
            public void run() {
                PageCtrl.startActivity( LoginActivity.this, HomeActivity.class);
                finish();
            }
        }, 3000);
        ToastUtils.showLong("默认三秒后自动进入主页 [调试状态]");

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        PageCtrl.startActivity(this,HomeActivity.class);
        finish();
    }
}
