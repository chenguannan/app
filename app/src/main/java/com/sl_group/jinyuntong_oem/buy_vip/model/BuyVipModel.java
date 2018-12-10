package com.sl_group.jinyuntong_oem.buy_vip.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface BuyVipModel {

    /**
     * @param accountNumber 结算卡
     */
    void buyVip(String accountNumber, BuyVipModel.IBuyVipCallBack buyVipCallBack);

    interface IBuyVipCallBack {
        void onSuccess(String data);
    }
}
