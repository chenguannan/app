package com.sl_group.jinyuntong_oem.safe_set.login_password.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.safe_set.login_password.persenter.ChangeLoginPasswordPersenter;
import com.sl_group.jinyuntong_oem.utils.SPUtil;

/**
 * Created by 马天 on 2018/11/17.
 * description：忘记登录密码
 */
public class ChangeLoginPasswordActivity extends BaseActivity implements ChangeLoginPasswordView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtForgetLoginPasswordNewPassword;
    private EditText mEtForgetLoginPasswordNewPasswordAgain;
    private Button mBtnForgetLoginPasswordNext;


    private ChangeLoginPasswordPersenter mChangeLoginPasswordPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_login_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mEtForgetLoginPasswordNewPassword = findViewById(R.id.et_forget_login_password_new_password);
        mEtForgetLoginPasswordNewPasswordAgain = findViewById(R.id.et_forget_login_password_new_password_again);
        mBtnForgetLoginPasswordNext = findViewById(R.id.btn_forget_login_password_next);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("登录密码");
        mChangeLoginPasswordPersenter = new ChangeLoginPasswordPersenter(this,this);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnForgetLoginPasswordNext.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;

            case R.id.btn_forget_login_password_next:

                String tel = getIntent().getExtras().getString("tel", (String) SPUtil.get(ChangeLoginPasswordActivity.this,"cellPhone",""));
                String password = mEtForgetLoginPasswordNewPassword.getText().toString().trim();
                String passwordAgain = mEtForgetLoginPasswordNewPasswordAgain.getText().toString().trim();
                mChangeLoginPasswordPersenter.forgetLoginPassword(tel,password,passwordAgain);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

}
