package com.sl_group.jinyuntong_oem.login.login_tel.model;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface LoginTelModel {
    /**
     * 检查参数
     * @param tel 手机号码
     * @return boolean
     */
    boolean checkGetSMSParams(String tel);
    /**
      * 检查参数
      * @param tel 手机号码
      * @param smsCode 短信验证码
      * @return boolean
      */
    boolean checkLoginParams(String tel, String smsCode);

    /**
     * @param tel   手机号
     * @param loginSMSCallBack 登陆短信回调接口
     */
    void getLoginSMS(String tel,  ILoginSMSCallBack loginSMSCallBack);
    /**
     * @param tel      手机号
     * @param smsCode      短信
     * @param uuid      短信
     * @param loginCallBack 登陆回调接口
     */
    void login(String tel, String smsCode,String uuid, ILoginCallBack loginCallBack);

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
