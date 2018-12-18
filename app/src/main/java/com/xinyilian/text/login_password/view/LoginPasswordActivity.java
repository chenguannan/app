package com.xinyilian.text.login_password.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.login_password.persenter.LoginPasswordPersenter;
import com.xinyilian.text.utils.StringUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：忘记登录密码
 */
public class LoginPasswordActivity extends BaseActivity implements LoginPasswordView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtLoginPasswordNewPassword;
    private EditText mEtLoginPasswordNewPasswordAgain;
    private Button mBtnLoginPasswordFinsh;
    //登录密码persenter
    private LoginPasswordPersenter mLoginPasswordPersenter;
    //手机号
    private String cellPhone;
    //验证码
    private String checkCode;
    //验证码UUID
    private String uuid;
    //类型
    private String type;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_login_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mEtLoginPasswordNewPassword = findViewById(R.id.et_login_password_new_password);
        mEtLoginPasswordNewPasswordAgain = findViewById(R.id.et_login_password_new_password_again);
        mBtnLoginPasswordFinsh = findViewById(R.id.btn_login_password_finsh);
    }

    @Override
    public void initData() {

        //初始化persenter
        mLoginPasswordPersenter = new LoginPasswordPersenter(this, this);
        //获取传递数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type", "");
            cellPhone = bundle.getString("cellPhone", "");
            checkCode = bundle.getString("checkCode", "");
            uuid = bundle.getString("uuid", "");
        }
        //设置标题
        if (!StringUtils.isEmpty(type) && type.equals("forget")) {
            mTvActionbarTitle.setText("忘记登录密码");
        } else if (!StringUtils.isEmpty(type) && type.equals("change")) {
            mTvActionbarTitle.setText("修改登录密码");
        } else {
            mTvActionbarTitle.setText("登录密码");
        }
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnLoginPasswordFinsh.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_login_password_finsh:
                //忘记登录密码
                String password = mEtLoginPasswordNewPassword.getText().toString().trim();
                String passwordAgain = mEtLoginPasswordNewPasswordAgain.getText().toString().trim();
                mLoginPasswordPersenter.loginPassword(cellPhone, checkCode, uuid, password, passwordAgain);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void loginPasswordSuccess() {
        finish();
    }
}
