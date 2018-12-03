package com.sl_group.jinyuntong_oem.login.login_password.model;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface LoginPasswordModel {

    /**
      * 检查参数
      * @param tel 用户名
      * @param password 密码
      * @return boolean
      */
    boolean checkParams(String tel, String password);

    /**
     * @param tel      用户名
     * @param password      密码
     * @param loginCallBack 登陆回调接口
     */
    void login(String tel, String password, ILoginCallBack loginCallBack);

    /**
     * 登陆回调接口及回调方法
     */
    interface ILoginCallBack {
        void onSuccess(String data);
    }
}
