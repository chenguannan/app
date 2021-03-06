package com.xinyilian.text.safe_set;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.login.LoginActivity;
import com.xinyilian.text.gesture_password.GesturePasswordActivity;
import com.xinyilian.text.pay_password.PayPasswordActivity;
import com.xinyilian.text.sms.view.SMSActivity;
import com.xinyilian.text.utils.DisplayUtils;
import com.xinyilian.text.utils.PopupWindowUtils;
import com.xinyilian.text.utils.SPUtil;

/**
 * Created by 马天 on 2018/11/18.
 * description：安全设置
 */
public class SafeSetActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvSafeSetLoginPassword;
    private TextView mTvSafeSetPayPassword;
    private TextView mTvSafeSetGesturePassword;
    private Button mBtnSafeSetExitLogin;


    @Override
    public int bindLayout() {
        return R.layout.activity_safe_set;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvSafeSetLoginPassword = findViewById(R.id.tv_safe_set_login_password);
        mTvSafeSetPayPassword = findViewById(R.id.tv_safe_set_pay_password);
        mTvSafeSetGesturePassword = findViewById(R.id.tv_safe_set_gesture_password);
        mBtnSafeSetExitLogin = findViewById(R.id.btn_safe_set_exit_login);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("安全设置");
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvSafeSetLoginPassword.setOnClickListener(this);
        mTvSafeSetPayPassword.setOnClickListener(this);
        mTvSafeSetGesturePassword.setOnClickListener(this);
        mBtnSafeSetExitLogin.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_safe_set_login_password:
                //修改登录密码
                Bundle bundle = new Bundle();
                bundle.putString("action","changeLoginPassword");
                startActivity(SMSActivity.class,bundle);
                break;
            case R.id.tv_safe_set_pay_password:
                //支付密码
                startActivity(PayPasswordActivity.class);
                break;
            case R.id.tv_safe_set_gesture_password:
                //手势密码
                startActivity(GesturePasswordActivity.class);
                break;
            case R.id.btn_safe_set_exit_login:
                //退出登录
                popExitLogin();
                break;

        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
    /**
      * 退出登录弹窗
      */
    private void popExitLogin(){
        View view = LayoutInflater.from(this).inflate(R.layout.pop_exit_login, null);
        Button cancel = view.findViewById(R.id.btn_pop_exit_login_cancel);
        Button sure = view.findViewById(R.id.btn_pop_exit_login_sure);
        final PopupWindow popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                SPUtil.clear(SafeSetActivity.this);
                startActivity(LoginActivity.class);
                finish();
            }
        });
    }
}
