package com.sl_group.jinyuntong_oem.pay_bill.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：支付账单
 */
public interface PayBillModel {

    /**
     * @param isShowPeogress  是否显示加载进度条
     * @param curPage         当前页码
     * @param pageSize        每页条数
     * @param beginDate       开始时间
     * @param endDate         结束时间
     * @param payBillCallBack 查询成功回调
     */
    void payBill(boolean isShowPeogress, int curPage, String pageSize, String beginDate, String endDate, PayBillModel.IPayBillCallBack payBillCallBack);

    interface IPayBillCallBack {
        void onSuccess(String data);
    }
}
