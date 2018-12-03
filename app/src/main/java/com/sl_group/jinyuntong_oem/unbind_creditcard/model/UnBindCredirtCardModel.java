package com.sl_group.jinyuntong_oem.unbind_creditcard.model;

/**
 * Created by 马天 on 2018/11/29.
 * description：
 */
public interface UnBindCredirtCardModel {
    void unBindCreditCard(String fastpayBankAccountInfoId,UnBindCredirtCardModel.IUnBindCreditCardCallBack unBindCreditCardCallBack);

    interface IUnBindCreditCardCallBack{
        void onSuccess(String data);
    }
}
