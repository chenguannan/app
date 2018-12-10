package com.sl_group.jinyuntong_oem.pay_password_set.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface PayPasswordSetModel {
    /**
     * @param cellPhone              手机号
     * @param checkCode              验证码
     * @param uuid                   验证码UUID
     * @param tradePassword          支付密码
     * @param iPayPasswordSetCallBack 成功回调
     */
    void payPasswordSet(String cellPhone, String checkCode, String uuid, String tradePassword, IPayPasswordSetCallBack iPayPasswordSetCallBack);

    interface IPayPasswordSetCallBack {
        void onSuccess(String data);
    }
}
