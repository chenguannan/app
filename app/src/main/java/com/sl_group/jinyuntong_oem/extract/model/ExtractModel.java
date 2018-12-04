package com.sl_group.jinyuntong_oem.extract.model;

/**
 * Created by 马天 on 2018/11/24.
 * description：提现
 */
public interface ExtractModel {

    /**
     *
     * @param extractMoney 提现金额
     * @param holderName 持卡人姓名
     * @param accountNumber 卡号
     */
    void extract(String extractMoney, String holderName, String accountNumber, ExtractModel.IApplyExtractCallBack applyExtractCallBack);

    interface IApplyExtractCallBack{
        void onSuccess(String data);
    }
}
