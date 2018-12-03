package com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface ChangePayPasswordModel {
    boolean checkParams(String tradePassword);

    void changePayPassword(String tradePassword,ChangePayPasswordModel.IChangePayPasswordCallBack changePayPasswordCallBack);

    interface   IChangePayPasswordCallBack{
        void onSuccess(String data);
    }
}
