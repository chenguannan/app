package com.sl_group.jinyuntong_oem.realname_sms.model;


/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface RealnameSMSModel {
    /**
      *
      * @param tel 获取验证码手机号
      * @param realnameSMSCallBack 实名短信回调
      */
    void realnameSMS(String tel, RealnameSMSModel.IRealnameSMSCallBack realnameSMSCallBack);

    interface IRealnameSMSCallBack{
        void onSuccess(String data);
    }
}
