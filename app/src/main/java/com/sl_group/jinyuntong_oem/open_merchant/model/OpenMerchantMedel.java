package com.sl_group.jinyuntong_oem.open_merchant.model;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public interface OpenMerchantMedel {

    boolean checkOpenMerchantParams(String businessUUID,String docUUID,String businessPicUUID,String shopname,String shopaddress,String accountNumber,String tel);

    void openMerchant(String businessUUID,String docUUID,String businessPicUUID,String shopname,String shopaddress,String accountNumber,String tel ,OpenMerchantMedel.IOpenMerchantCallBack openMerchantCallBack);


    interface IOpenMerchantCallBack{
        void onSuccess(String data);
    }

}
