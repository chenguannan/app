package com.sl_group.jinyuntong_oem.set_pay_password.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface SetPayPasswordModel {
    void setPayPassword(String cellPhone,String checkCode,String uuid,String tradePassword,SetPayPasswordModel.ISetPayPasswordCallBack setPayPasswordCallBack);

    interface ISetPayPasswordCallBack{
        void onSuccess(String data);
    }
}
