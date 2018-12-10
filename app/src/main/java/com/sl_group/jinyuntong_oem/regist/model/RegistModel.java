package com.sl_group.jinyuntong_oem.regist.model;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public interface RegistModel {


    /**
     * @param inviteCode       邀请码
     * @param tel              手机号
     * @param password         密码
     * @param checkCode        验证码
     * @param uuid             uuid
     * @param registerCallBack 注册回调接口
     */
    void register(String inviteCode, String tel, String checkCode, String uuid, String password, RegistModel.IRegisterCallBack registerCallBack);

    /**
     * 注册获取验证码回调接口及回调方法
     */
    interface IRegisterCallBack {
        void onSuccess(String data);
    }
}
