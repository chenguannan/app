package com.xinyilian.text.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinyilian.text.MainActivity;
import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.sms.view.SMSActivity;
import com.xinyilian.text.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/13.
 * description：手势密码登录
 */
public class GestureLoginActivity extends BaseActivity{
    private TextView mTvGestureTitle;
    private GestureLockView mGlvLogin;
    private TextView mTvGestureLoginInputPasswordLogin;
    private TextView mTvGestureLoginForgetGesturePassword;
    //手势密码
    private String gesturePassword;

    @Override
    public int bindLayout() {
        return R.layout.activity_gesture_login;
    }

    @Override
    public void initView(View view) {
        mTvGestureTitle = findViewById(R.id.tv_gesture_title);
        mGlvLogin = findViewById(R.id.glv_login);
        mTvGestureLoginInputPasswordLogin = findViewById(R.id.tv_gesture_login_input_password_login);
        mTvGestureLoginForgetGesturePassword = findViewById(R.id.tv_gesture_login_forget_gesture_password);
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //获取传递的手势密码
            gesturePassword = bundle.getString("gesturePassword");
        }else {
            startActivity(LoginActivity.class);
            finish();
        }
        //手势监听
        mGlvLogin.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(String progress) {

            }

            @Override
            public void onComplete(String result) {
                mTvGestureTitle.setText("滑动手势密码登录");
                if (gesturePassword.equals(result)) {
                    mGlvLogin.clearView();
                    ToastUtils.showToast("登录成功");
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtils.showToast("手势错误");
                    mGlvLogin.showErrorStatus(300);
                }


            }
        });
    }

    @Override
    public void setListener() {
        mTvGestureLoginInputPasswordLogin.setOnClickListener(this);
        mTvGestureLoginForgetGesturePassword.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            //输入登录密码登录
            case R.id.tv_gesture_login_input_password_login:
                startActivity(LoginActivity.class);
                finish();
                break;
            //忘记手势密码
            case R.id.tv_gesture_login_forget_gesture_password:
                Bundle bundle = new Bundle();
                bundle.putString("action", "forgetGesturePassword");
                startActivity(SMSActivity.class, bundle);
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }

}
