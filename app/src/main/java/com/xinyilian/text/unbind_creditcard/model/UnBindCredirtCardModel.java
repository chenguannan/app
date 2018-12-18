package com.xinyilian.text.unbind_creditcard.model;

/**
 * Created by 马天 on 2018/11/29.
 * description：解绑信用卡
 */
public interface UnBindCredirtCardModel {
    /**
     * @param fastpayBankAccountInfoId 银行卡ID
     */
    void unBindCreditCard(String fastpayBankAccountInfoId, UnBindCredirtCardModel.IUnBindCreditCardCallBack unBindCreditCardCallBack);

    interface IUnBindCreditCardCallBack {
        void onSuccess(String data);
    }
}
