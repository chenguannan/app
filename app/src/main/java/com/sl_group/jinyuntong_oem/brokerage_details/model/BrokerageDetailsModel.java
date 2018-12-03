package com.sl_group.jinyuntong_oem.brokerage_details.model;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金详情业务接口
 */
public interface BrokerageDetailsModel {
    /**
      * 佣金详情
      * @param intoType 佣金类型
      * @param logId 记录日志ID
      * @param brokerageDetailsCallBack 查询成功回调接口
      */
    void brokerageDetails(String intoType,String logId,BrokerageDetailsModel.IBrokerageDetailsCallBack brokerageDetailsCallBack);

    //佣金详情查询成功回调
    interface IBrokerageDetailsCallBack{
        void onSuccess(String data);
    }
}
