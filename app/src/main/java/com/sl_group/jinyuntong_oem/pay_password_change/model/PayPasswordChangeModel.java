package com.sl_group.jinyuntong_oem.pay_password_change.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface PayPasswordChangeModel {

    /**
     * @param cellPhone                 手机号
     * @param checkCode                 验证码
     * @param uuid                      验证码UUID
     * @param tradePassword             支付密码
     * @param changePayPasswordCallBack 成功回调
     */
    void changePayPassword(String cellPhone, String checkCode, String uuid, String tradePassword, PayPasswordChangeModel.IChangePayPasswordCallBack changePayPasswordCallBack);

    interface IChangePayPasswordCallBack {
        void onSuccess(String data);
    }
}
