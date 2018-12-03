package com.sl_group.jinyuntong_oem.vip.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface VipCenterModel {
    void queryBankFee(VipCenterModel.IQueryBankFeeCallBack queryBankFeeCallBack);

    interface IQueryBankFeeCallBack{
        void onSuccess(String data);
    }

    void buyVip(String accountNumber,VipCenterModel.IBuyVipCallBack buyVipCallBack);

    interface IBuyVipCallBack{
        void onSuccess(String data);
    }
}
