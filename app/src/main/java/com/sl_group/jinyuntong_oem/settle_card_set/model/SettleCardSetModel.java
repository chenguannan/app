package com.sl_group.jinyuntong_oem.settle_card_set.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface SettleCardSetModel {

    /**
     * @param accountNumber 结算卡号
     * @param tel           预留手机号
     */
    void settleCardSet(String accountNumber, String tel, ISettleCardSetCallBack iSettleCardSetCallBack);

    interface ISettleCardSetCallBack {
        void onSuccess(String data);
    }
}
