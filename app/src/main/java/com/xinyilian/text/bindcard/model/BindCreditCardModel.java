package com.xinyilian.text.bindcard.model;

/**
 * Created by 马天 on 2018/11/15.
 * description：绑定信用卡接口
 */
public interface BindCreditCardModel {


    /**
     * @param accountNumber    银行卡号
     * @param bankcardTel      银行预留手机号
     * @param bindCallBack 绑卡回调接口
     */
    void bindCreditCard(String accountNumber, String bankcardTel, IBindCreditCardCallBack bindCallBack);


    /**
     * 绑卡回调接口及回调方法
     */
    interface IBindCreditCardCallBack {
        void onSuccess(String data);
    }

}
