package com.sl_group.jinyuntong_oem.merchant_info.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface MerchantinfoModel {
    /**
      * 商户信息
      * @param merchantInfoCallBack 商户信息回调
      */
    void merchantInfo(MerchantinfoModel.IMerchantInfoCallBack merchantInfoCallBack);

    /**
      * 商户信息回调
      */
    interface IMerchantInfoCallBack{
        void onSuccess(String data);
    }
}
