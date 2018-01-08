package cc.zkteam.zkinfocollectpro.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKICApplication;
import cc.zkteam.zkinfocollectpro.activity.LoginActivity;
import cc.zkteam.zkinfocollectpro.base.BaseFragment;
import cc.zkteam.zkinfocollectpro.bean.ZHBaseBean;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by loong on 2017/12/30.
 */

public class ResetPwdFragment extends BaseFragment {
    @BindView(R.id.title_reset_pwd)
    ZKTitleView titleResetPwd;
    @BindView(R.id.et_count)
    TextView etCount;
    @BindView(R.id.et_pwd_old)
    EditText etPwdOld;
    @BindView(R.id.et_pwd_new)
    EditText etPwdNew;
    @BindView(R.id.et_pwd_new_agin)
    EditText etPwdNewAgin;
    @BindView(R.id.btn_reset_pwd)
    Button btnResetPwd;
    @BindView(R.id.pb_collect_loading)
    ProgressBar mLoading;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    public void initView(View rootView) {
        titleResetPwd.setCenterTVText("修改密码");
        titleResetPwd.rightIV.setVisibility(View.GONE);
        titleResetPwd.rightTextTV.setVisibility(View.INVISIBLE);
//        titleResetPwd.rightTextTV.setText("登录");
//        titleResetPwd.rightTextTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
//            }
//        });

        etCount.setText(ZKICApplication.zhLoginBean.getName());
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
        String user = etCount.getText().toString();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwdOld = etPwdOld.getText().toString();
        if (TextUtils.isEmpty(pwdOld)) {
            Toast.makeText(mContext, "原密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwdNew = etPwdNew.getText().toString();
        String pwdAgain = etPwdNewAgin.getText().toString();
        if (TextUtils.isEmpty(pwdNew) || TextUtils.isEmpty(pwdAgain)) {
            Toast.makeText(mContext, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pwdNew.equals(pwdAgain)) {
            Toast.makeText(mContext, "密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading();
        zhApiInstance.resetPwd(user, EncryptUtils.encryptMD5ToString(pwdOld), EncryptUtils.encryptMD5ToString(pwdNew)).enqueue(new Callback<ZHBaseBean>() {
            @Override
            public void onResponse(Call<ZHBaseBean> call, Response<ZHBaseBean> response) {
                hideLoading();
                if (getActivity() == null) return;
                if (response.body() == null) return;
                if (response.body().getStatus() == 0) {
                    Toast.makeText(mContext, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    etPwdOld.setText("");
                    etPwdNew.setText("");
                    etPwdNewAgin.setText("");
                    Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ZHBaseBean> call, Throwable t) {
                hideLoading();
                Toast.makeText(mContext, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoading() {
        if (null != mLoading)
            mLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (null != mLoading)
            mLoading.setVisibility(View.GONE);
    }
}
