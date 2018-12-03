package com.sl_group.jinyuntong_oem.pay_bill.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface PayBillModel {

    void getPayBill(boolean isShowPeogress, int curPage, String pageSize, String beginDate, String endDate, PayBillModel.IPayBillCallBack payBillCallBack);

    interface IPayBillCallBack{
        void onSuccess(String data);
    }
}
