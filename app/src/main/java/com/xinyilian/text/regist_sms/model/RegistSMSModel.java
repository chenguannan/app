package com.xinyilian.text.regist_sms.model;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public interface RegistSMSModel {
    /**
     * @param inviteCode 邀请码
     * @param tel 手机号
     * @param registSMSCallBack 注册获取验证码回调接口
     */
    void registSMS(String inviteCode, String tel, IRegistSMSCallBack registSMSCallBack);

    interface IRegistSMSCallBack {
        void onSuccess(String data);
    }
}
