package com.sl_group.jinyuntong_oem.login.gesture;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.MainActivity;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.login.LoginActivity;
import com.sl_group.jinyuntong_oem.login.login_password.view.LoginPasswordView;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/13.
 * description：手势密码登录
 */
public class GestureLoginActivity extends BaseActivity implements LoginPasswordView {
    private TextView mTvGestureTitle;
    private GestureLockView mGlvLogin;
    private TextView mTvGestureLoginInputPasswordLogin;
    private TextView mTvGestureLoginForgetGesturePassword;

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
        if (bundle!=null){
            gesturePassword = bundle.getString("gesturePassword");
        }

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
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtils.showToast("手势错误");
                    mGlvLogin.showErrorStatus(500);
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
        switch (v.getId()){
            case R.id.tv_gesture_login_input_password_login:
                startActivity(LoginActivity.class);
                finish();
                break;
            case R.id.tv_gesture_login_forget_gesture_password:
                Bundle bundle = new Bundle();
                bundle.putString("action","forgetGesturePassword");
                startActivity(SMSActivity.class,bundle);
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void skipMainActivity() {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void skipLoginActivity() {
        startActivity(LoginActivity.class);
        finish();
    }


}
