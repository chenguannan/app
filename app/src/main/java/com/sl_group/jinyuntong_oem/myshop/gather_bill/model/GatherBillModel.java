package com.sl_group.jinyuntong_oem.myshop.gather_bill.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface GatherBillModel {

    void getGatherBill(boolean isShowPeogress,int curPage,String pageSize,String beginDate,String endDate,GatherBillModel.IGatherBillCallBack gatherBillCallBack);

    interface IGatherBillCallBack{
        void onSuccess(String data);
    }
}
