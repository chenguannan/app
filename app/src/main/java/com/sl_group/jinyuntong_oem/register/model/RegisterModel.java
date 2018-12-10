package com.sl_group.jinyuntong_oem.register.model;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public interface RegisterModel {
    /**
      *
      * @param inviteCode 邀请码
      * @param tel s手机号码
      * @return boolean
      */
    boolean checkSMSParams(String inviteCode,String tel);
    /**
     * 检查参数
     * @param inviteCode 邀请码
     * @param tel 手机号
     * @param vificationCode 验证码
     * @param password 密码
     * @param passwordAgain 重复密码
     * @param isSelected 是否勾选协议
     * @return boolean
     */
    boolean checkRegisterParams(String inviteCode, String tel,String vificationCode, String password,String passwordAgain,boolean isSelected);
    /**
     * @param inviteCode 邀请码
     * @param tel 手机号
     * @param registerGetSMSCallBack 注册获取验证码回调接口
     */
    void getSMS(String inviteCode, String tel,RegisterModel.IRegisterGetSMSCallBack registerGetSMSCallBack);
    /**
     * @param inviteCode 邀请码
     * @param tel 手机号
     * @param password 密码
     * @param checkCode 验证码
     * @param uuid uuid
     * @param registerCallBack 注册回调接口
     */
    void register(String inviteCode, String tel,String checkCode ,String uuid, String password,RegisterModel.IRegisterCallBack registerCallBack);
    /**
     * 注册获取验证码回调接口及回调方法
     */
    interface IRegisterCallBack {
        void onSuccess(String data);
    }
    /**
     * 注册回调接口及回调方法
     */
    interface IRegisterGetSMSCallBack {
        void onSuccess(String data);
    }
}
