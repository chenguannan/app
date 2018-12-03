package com.sl_group.jinyuntong_oem.merchant_info.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface MerchantinfoModel {
    void merchantInfo(MerchantinfoModel.IMerchantInfoCallBack merchantInfoCallBack);

    interface IMerchantInfoCallBack{
        void onSuccess(String data);
    }
}
