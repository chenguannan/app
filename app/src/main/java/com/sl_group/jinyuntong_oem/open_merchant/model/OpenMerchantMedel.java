package com.sl_group.jinyuntong_oem.open_merchant.model;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public interface OpenMerchantMedel {

    /**
     * @param businessUUID         营业场所上传照片UUID
     * @param docUUID              门店上传照片UUID
     * @param businessPicUUID      营业执照上传照片UUID
     * @param shopname             店铺名称
     * @param shopaddress          店铺地址
     * @param accountNumber        结算卡号
     * @param tel                  银行预留手机号
     * @param openMerchantCallBack 开通商户权限请求成功回调
     */
    void openMerchant(String businessUUID, String docUUID, String businessPicUUID, String shopname, String shopaddress, String accountNumber, String tel, OpenMerchantMedel.IOpenMerchantCallBack openMerchantCallBack);

    /**
     * 请求成功回调
     */
    interface IOpenMerchantCallBack {
        void onSuccess(String data);
    }

}
