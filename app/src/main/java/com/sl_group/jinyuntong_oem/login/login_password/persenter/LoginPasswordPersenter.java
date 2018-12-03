package com.sl_group.jinyuntong_oem.login.login_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.LoginBean;
import com.sl_group.jinyuntong_oem.login.login_password.model.LoginPasswordModel;
import com.sl_group.jinyuntong_oem.login.login_password.model.LoginPasswordModelImpl;
import com.sl_group.jinyuntong_oem.login.login_password.view.LoginPasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginPasswordPersenter {
    private Activity mActivity;
    private LoginPasswordModelImpl mLoginModel;
    private LoginPasswordView mLoginPasswordView;

    public LoginPasswordPersenter(Activity activity, LoginPasswordView loginPasswordView) {
        this.mActivity = activity;
        mLoginPasswordView = loginPasswordView;
        mLoginModel = new LoginPasswordModelImpl(activity);
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param tel      用户名
     * @param password 密码
     */
    public void login(final String tel, final String password) {
        if (!mLoginModel.checkParams(tel, password)) {
            return;
        }
        mLoginModel.login(tel, password, new LoginPasswordModel.ILoginCallBack() {
            @Override
            public void onSuccess(String data) {
                LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                if ("000000".equals(loginBean.getCode())) {
                    LoginBean.DataBean dataBean = loginBean.getData();
                    SPUtil.clear(mActivity);
                    SPUtil.put(mActivity,"loginSuccess",true);
                    SPUtil.put(mActivity, "usertel", tel);
                    SPUtil.put(mActivity, "password", password);
                    SPUtil.put(mActivity, "agencyId", String.valueOf(dataBean.getAgencyId()));
                    SPUtil.put(mActivity, "encryptId", dataBean.getEncryptId());
                    SPUtil.put(mActivity, "inviteCode", dataBean.getInviteCode());
                    SPUtil.put(mActivity, "key", dataBean.getKey());
                    SPUtil.put(mActivity, "merchantName", dataBean.getMerchantName());
                    SPUtil.put(mActivity, "merchantStatus", dataBean.getMerchantStatus());
                    SPUtil.put(mActivity, "mid", dataBean.getMid());
                    SPUtil.put(mActivity, "participantId", dataBean.getParticipantId());
                    SPUtil.put(mActivity, "qualifiedState", dataBean.getQualifiedState());
                    mLoginPasswordView.skipMainActivity();
                    ToastUtils.showToast(loginBean.getMessage());
                } else if ("210004".equals(loginBean.getCode())) {
                    mLoginPasswordView.skipLoginActivity();
                }else {
                    ToastUtils.showToast(loginBean.getMessage());
                }


                LogUtils.i("ChangeLoginPasswordModelImpl--46--登录：\n" + data);
            }
        });
    }
}
