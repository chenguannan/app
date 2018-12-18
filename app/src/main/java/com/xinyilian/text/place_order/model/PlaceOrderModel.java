package com.xinyilian.text.place_order.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface PlaceOrderModel {

    /**
     * 下单
     *
     * @param faceUuid           人脸识别UUID
     * @param qrcodeContent      二维码内容
     * @param accountNumber      结算卡号
     * @param tradePassword      交易密码
     * @param srcAmt             交易金额
     * @param placeOrderCallBack 下单回调
     */
    void placeOrder(String faceUuid, String qrcodeContent, String accountNumber, String tradePassword, String srcAmt, PlaceOrderModel.IPlaceOrderCallBack placeOrderCallBack);

    interface IPlaceOrderCallBack {
        void onSuccess(String data);
    }


}
