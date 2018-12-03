package com.sl_group.jinyuntong_oem.scan_input_money.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface ScanQrcodeInputMoneyModel  {

    void placeOrder(String faceUuid,String uuid,String accountNumber, String tradePassword,String srcAmt,ScanQrcodeInputMoneyModel.IPlaceOrderCallBack placeOrderCallBack);

    interface IPlaceOrderCallBack{
        void onSuccess(String data);
    }

    void setPayPassword(String tradePassword,ScanQrcodeInputMoneyModel.ISetPayPasswordCallBack setPayPasswordCallBack);

    interface ISetPayPasswordCallBack{
        void onSuccess(String data);
    }

}
