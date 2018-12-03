package com.sl_group.jinyuntong_oem.sms.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface SMSModel {
    boolean checkParams(String tel);

    void getSMS(String tel,SMSModel.IChangeGesturePasswordSMSCallBack changeGesturePasswordSMSCallBack);

    interface IChangeGesturePasswordSMSCallBack{
        void onSuccess(String data);
    }
}
