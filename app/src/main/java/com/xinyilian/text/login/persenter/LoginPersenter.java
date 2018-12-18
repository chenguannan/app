package com.xinyilian.text.login.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.bean.LoginBean;
import com.xinyilian.text.bean.LoginSMSBean;
import com.xinyilian.text.login.model.LoginModel;
import com.xinyilian.text.login.model.LoginModelImpl;
import com.xinyilian.text.login.view.LoginView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginPersenter {
    private Activity mActivity;
    private LoginModelImpl mLoginTelModel;
    private LoginView mLoginTelView;

    public LoginPersenter(Activity activity, LoginView loginTelView) {
        this.mActivity = activity;
        mLoginTelView = loginTelView;
        mLoginTelModel = new LoginModelImpl(activity);
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param tel 手机号码
     */
    public void getSMSCode(final String tel) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return;
        }
        mLoginTelModel.getLoginSMS(tel, new LoginModel.ILoginSMSCallBack() {
            @Override
            public void onSuccess(String data) {

                LogUtils.i("登录短信验证码：" + data);
                LoginSMSBean loginSMSBean = new Gson().fromJson(data, LoginSMSBean.class);
                if ("000000".equals(loginSMSBean.getCode())) {
                    mLoginTelView.loginSMSSuccess(loginSMSBean.getData());
                }
                ToastUtils.showToast(loginSMSBean.getMessage());

            }
        });
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param tel     用户名
     * @param smsCode 短信验证码
     */
    public void login(String loginType, final String tel, String password, String smsCode, String uuid) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号码");
            return;
        }
        if (StringUtils.isEmpty(loginType) && StringUtils.isEmpty(smsCode)) {
            ToastUtils.showToast("请输入短信验证码");
        }
        if (!StringUtils.isEmpty(loginType) && (StringUtils.isEmpty(password) || password.length() < 8)) {
            ToastUtils.showToast("请输入8-14位登录密码");
        }

        mLoginTelModel.login(loginType, tel, password, smsCode, uuid, new LoginModel.ILoginCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("登录：" + data);
                LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
                if ("000000".equals(loginBean.getCode())) {
                    LoginBean.DataBean dataBean = loginBean.getData();
                    SPUtil.clear(mActivity);
                    SPUtil.put(mActivity, "loginSuccess", true);
                    SPUtil.put(mActivity, "usertel", tel);
                    SPUtil.put(mActivity, "cellPhone", dataBean.getCellPhone());
                    SPUtil.put(mActivity, "agencyId", String.valueOf(dataBean.getAgencyId()));
                    SPUtil.put(mActivity, "encryptId", dataBean.getEncryptId());
                    SPUtil.put(mActivity, "inviteCode", dataBean.getInviteCode());
                    SPUtil.put(mActivity, "key", dataBean.getKey());
                    SPUtil.put(mActivity, "merchantName", dataBean.getMerchantName());
                    SPUtil.put(mActivity, "merchantStatus", dataBean.getMerchantStatus());
                    SPUtil.put(mActivity, "mid", dataBean.getMid());
                    SPUtil.put(mActivity, "participantId", dataBean.getParticipantId());
                    SPUtil.put(mActivity, "qualifiedState", dataBean.getQualifiedState());
                    mLoginTelView.loginSuccess();
                } else if ("210004".equals(loginBean.getCode())) {
                    mLoginTelView.compelLogin();
                } else {
                    ToastUtils.showToast(loginBean.getMessage());
                }
            }
        });
    }
}
