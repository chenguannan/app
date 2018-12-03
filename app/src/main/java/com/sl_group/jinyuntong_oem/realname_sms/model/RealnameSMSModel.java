package com.sl_group.jinyuntong_oem.realname_sms.model;


/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface RealnameSMSModel {
    boolean checkParams(String tel);

    void getRealnameSMS(String tel, RealnameSMSModel.IRealnameSMSCallBack realnameSMSCallBack);

    interface IRealnameSMSCallBack{
        void onSuccess(String data);
    }
}
