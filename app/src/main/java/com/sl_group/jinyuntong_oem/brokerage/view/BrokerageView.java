package com.sl_group.jinyuntong_oem.brokerage.view;

import com.sl_group.jinyuntong_oem.bean.DealRecordBean;
import com.sl_group.jinyuntong_oem.bean.ExtractRecordBean;
import com.sl_group.jinyuntong_oem.bean.UpVipBrokerageBean;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金UI接口
 */
public interface BrokerageView {
    //提现记录
    void extractRecord(ExtractRecordBean.DataBean data);
    //交易记录
    void dealRecord(DealRecordBean.DataBean data);
    //vip升级奖励
    void upVipBrokerage(UpVipBrokerageBean.DataBean data);
}
