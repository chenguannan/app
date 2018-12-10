package com.sl_group.jinyuntong_oem.login_password.model;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public interface LoginPasswordModel {

    /**
     * @param cellPhone             手机号
     * @param checkCode             验证码
     * @param uuid                  验证码UUID
     * @param password              密码
     * @param loginPasswordCallBack 成功回调
     */
    void loginPassword(String cellPhone, String checkCode, String uuid, String password, ILoginPasswordCallBack loginPasswordCallBack);

    interface ILoginPasswordCallBack {
        void onSuccess(String data);
    }
}
