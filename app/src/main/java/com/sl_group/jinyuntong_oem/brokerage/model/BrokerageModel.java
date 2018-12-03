package com.sl_group.jinyuntong_oem.brokerage.model;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金业务接口
 */
public interface BrokerageModel {

    /**
      * 查询佣金
      * @param intoType 佣金类型
      * @param isShowProgress 是否显示加载进度条
      * @param curPage 当前页码
      * @param pageSize 每页条数
      * @param queryDate 查询日期
      * @param brokerageCallBack 查询成功回调
      */
    void brokerage(String intoType, boolean isShowProgress, int curPage, String pageSize, String queryDate, IBrokerageCallBack brokerageCallBack);

    /**
      * 查询佣金成功回调接口
      */
    interface IBrokerageCallBack {
        void onSuccess(String data);
    }
}
