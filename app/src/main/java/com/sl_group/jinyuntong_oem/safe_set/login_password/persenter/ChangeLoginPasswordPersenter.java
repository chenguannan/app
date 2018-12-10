package com.sl_group.jinyuntong_oem.safe_set.login_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.ForgetLoginPasswordBean;
import com.sl_group.jinyuntong_oem.safe_set.login_password.model.ChangeLoginPasswordModel;
import com.sl_group.jinyuntong_oem.safe_set.login_password.model.ChangeLoginPasswordModelImpl;
import com.sl_group.jinyuntong_oem.safe_set.login_password.view.ChangeLoginPasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class ChangeLoginPasswordPersenter {

    private Activity mActivity;
    private ChangeLoginPasswordView mChangeLoginPasswordView;
    private ChangeLoginPasswordModelImpl mForgetLoginPasswordModel;

    public ChangeLoginPasswordPersenter(Activity activity, ChangeLoginPasswordView changeLoginPasswordView) {
        mActivity = activity;
        mChangeLoginPasswordView = changeLoginPasswordView;
        mForgetLoginPasswordModel = new ChangeLoginPasswordModelImpl(activity);
    }


    public void forgetLoginPassword(String cellPhone,String checkCode,String uuid,String password, String passswordAgain) {
        if (!mForgetLoginPasswordModel.checkForgetLoginPasswordParams(cellPhone, password, passswordAgain)) {
            return;
        }
        mForgetLoginPasswordModel.forgetLoginPassword( cellPhone, checkCode, uuid, password, new ChangeLoginPasswordModel.IForgetLoginPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("修改登录密码：" + data);
                ForgetLoginPasswordBean forgetLoginPasswordBean = new Gson().fromJson(data, ForgetLoginPasswordBean.class);
                if ("000000".equals(forgetLoginPasswordBean.getCode())) {
                    mActivity.finish();
                }else if ("888888".equals(forgetLoginPasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(forgetLoginPasswordBean.getMessage());
            }
        });
    }
}

