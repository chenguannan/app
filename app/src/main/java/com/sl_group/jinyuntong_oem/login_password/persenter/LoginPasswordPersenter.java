package com.sl_group.jinyuntong_oem.login_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.ForgetLoginPasswordBean;
import com.sl_group.jinyuntong_oem.login_password.model.LoginPasswordModel;
import com.sl_group.jinyuntong_oem.login_password.model.LoginPasswordModelImpl;
import com.sl_group.jinyuntong_oem.login_password.view.LoginPasswordView;
import com.sl_group.jinyuntong_oem.utils.IsNumberUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class LoginPasswordPersenter {

    private Activity mActivity;
    private LoginPasswordView mLoginPasswordView;
    private LoginPasswordModelImpl mForgetLoginPasswordModel;

    public LoginPasswordPersenter(Activity activity, LoginPasswordView loginPasswordView) {
        mActivity = activity;
        mLoginPasswordView = loginPasswordView;
        mForgetLoginPasswordModel = new LoginPasswordModelImpl(activity);
    }


    public void loginPassword(String cellPhone, String checkCode, String uuid, String password, String passwordAgain) {
        if (StringUtils.isEmpty(cellPhone)) {
            ToastUtils.showToast("请输入手机号码");
            return;
        }
        if (cellPhone.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号码");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showToast("请输入8-14位数字和字母组合的新密码");
            return;
        }
        if (!IsNumberUtils.isLetterDigit(password)) {
            ToastUtils.showToast("请输入数字和字母组合的密码");
            return;
        }

        if (StringUtils.isEmpty(passwordAgain)) {
            ToastUtils.showToast("请再次输入密码");
            return;
        }
        if (!password.equals(passwordAgain)) {
            ToastUtils.showToast("两次密码输入不一致");
            return;
        }
        mForgetLoginPasswordModel.loginPassword(cellPhone, checkCode, uuid, password, new LoginPasswordModel.ILoginPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("登录密码：" + data);
                ForgetLoginPasswordBean forgetLoginPasswordBean = new Gson().fromJson(data, ForgetLoginPasswordBean.class);
                if ("000000".equals(forgetLoginPasswordBean.getCode())) {
                    mLoginPasswordView.loginPasswordSuccess();
                } else if ("888888".equals(forgetLoginPasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(forgetLoginPasswordBean.getMessage());
            }
        });
    }
}

