package com.xinyilian.text.bank_fee.model;

/**
 * Created by 马天 on 2018/12/10.
 * description：
 */
public interface BankFeeModel {
    void bankFee(BankFeeModel.IBankFeeCallBack iBankFeeCallBack);

    interface IBankFeeCallBack{
        void onSuccess(String data);
    }
}
