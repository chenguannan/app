package com.sl_group.jinyuntong_oem.brokerage_details.view;

import com.sl_group.jinyuntong_oem.bean.DealBrokerageDetailsBean;
import com.sl_group.jinyuntong_oem.bean.ExtractBrokerageDetailsBean;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金详情UI
 */
public interface BrokerageDetailsView {
    //佣金详情
    void brokerageDetails(ExtractBrokerageDetailsBean.DataBean data);
    //交易详情
    void dealDetails(DealBrokerageDetailsBean.DataBean data);
}
