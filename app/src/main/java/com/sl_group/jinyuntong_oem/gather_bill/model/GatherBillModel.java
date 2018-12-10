package com.sl_group.jinyuntong_oem.gather_bill.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface GatherBillModel {

    /**
     * @param isShowPeogress     是否显示加载进度条
     * @param curPage            当前页码
     * @param pageSize           每页条数
     * @param beginDate          开始时间
     * @param endDate            结束时间
     * @param gatherBillCallBack 查询收款账单回调
     */
    void gatherBill(boolean isShowPeogress, int curPage, String pageSize, String beginDate, String endDate, GatherBillModel.IGatherBillCallBack gatherBillCallBack);

    interface IGatherBillCallBack {
        void onSuccess(String data);
    }
}
