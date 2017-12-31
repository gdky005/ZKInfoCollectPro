package cc.zkteam.zkinfocollectpro.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.activity.LoginActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;

/**
 * Created by loong on 2017/12/30.
 */

public class ResetPwdFragment extends BaseFragment {
    @BindView(R.id.title_reset_pwd)
    ZKTitleView titleResetPwd;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.et_pwd_old)
    EditText etPwdOld;
    @BindView(R.id.et_pwd_new)
    EditText etPwdNew;
    @BindView(R.id.et_pwd_new_agin)
    EditText etPwdNewAgin;
    @BindView(R.id.btn_reset_pwd)
    Button btnResetPwd;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    public void initView(View rootView) {
        titleResetPwd.setCenterTVText("修改密码");
        titleResetPwd.rightIV.setVisibility(View.GONE);
        titleResetPwd.rightTextTV.setVisibility(View.VISIBLE);
        titleResetPwd.rightTextTV.setText("登录");
        titleResetPwd.rightTextTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        titleResetPwd.leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.btn_reset_pwd)
    public void onViewClicked() {
    }
}
