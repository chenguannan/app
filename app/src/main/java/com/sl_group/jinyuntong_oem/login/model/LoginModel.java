package com.sl_group.jinyuntong_oem.login.model;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface LoginModel {

    /**
     * @param tel   手机号
     * @param loginSMSCallBack 登陆短信回调接口
     */
    void getLoginSMS(String tel,  ILoginSMSCallBack loginSMSCallBack);
    /**
     * @param loginType      登录类型
     * @param tel      手机号
     * @param password      登录密码
     * @param smsCode      短信
     * @param uuid      短信
     * @param loginCallBack 登陆回调接口
     */
    void login(String loginType,String tel,String password, String smsCode,String uuid, ILoginCallBack loginCallBack);

    /**
     * 登录获取验证码回调接口及回调方法
     */
    interface ILoginSMSCallBack {
        void onSuccess(String data);
    }
    /**
     * 登陆回调接口及回调方法
     */
    interface ILoginCallBack {
        void onSuccess(String data);
    }

}
