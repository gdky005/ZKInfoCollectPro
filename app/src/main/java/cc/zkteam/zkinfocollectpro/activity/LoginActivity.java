package cc.zkteam.zkinfocollectpro.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;

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

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
