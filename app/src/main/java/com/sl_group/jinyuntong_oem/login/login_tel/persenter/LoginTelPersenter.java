package com.sl_group.jinyuntong_oem.login.login_tel.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.LoginBean;
import com.sl_group.jinyuntong_oem.bean.LoginSMSBean;
import com.sl_group.jinyuntong_oem.login.login_tel.model.LoginTelModel;
import com.sl_group.jinyuntong_oem.login.login_tel.model.LoginTelModelImpl;
import com.sl_group.jinyuntong_oem.login.login_tel.view.LoginTelView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginTelPersenter {
    private Activity mActivity;
    private LoginTelModelImpl mLoginTelModel;
    private LoginTelView mLoginTelView;

    public LoginTelPersenter(Activity activity, LoginTelView loginTelView) {
        this.mActivity = activity;
        mLoginTelView = loginTelView;
        mLoginTelModel = new LoginTelModelImpl(activity);
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param tel 手机号码
     */
    public void getSMSCode(final String tel) {
        if (!mLoginTelModel.checkGetSMSParams(tel)) {
            return;
        }
        mLoginTelModel.getLoginSMS(tel, new LoginTelModel.ILoginSMSCallBack() {
            @Override
            public void onSuccess(String data) {

                LogUtils.i("ChangeLoginPasswordModelImpl--46--登录短信：\n" + data);
                LoginSMSBean loginSMSBean = new Gson().fromJson(data, LoginSMSBean.class);
                if ("000000".equals(loginSMSBean.getCode())) {
                    mLoginTelView.getSMSUUID(loginSMSBean.getData().getUuid());
                    mLoginTelView.startCount();
                }
                ToastUtils.showToast(loginSMSBean.getMessage());

            }
        });
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param tel     用户名
     * @param smsCode 密码
     */
    public void login(final String tel, final String smsCode, final String uuid) {
        if (!mLoginTelModel.checkLoginParams(tel, smsCode)) {
            return;
        }
        mLoginTelModel.login(tel, smsCode, uuid, new LoginTelModel.ILoginCallBack() {
            @Override
            public void onSuccess(String data) {
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
                    mLoginTelView.skipMainActivity();
                } else if ("210004".equals(loginBean.getCode())) {
                    mLoginTelView.skipLoginActivity();
                } else {
                    ToastUtils.showToast(loginBean.getMessage());
                }

                LogUtils.i("ChangeLoginPasswordModelImpl--46--登录：\n" + data);
            }
        });
    }
}
