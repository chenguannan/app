package com.sl_group.jinyuntong_oem.myshop.gather_bill.view;

import com.sl_group.jinyuntong_oem.bean.GatherBillBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface GatherBillView {
    void getGatherBillList(List<GatherBillBean.DataBean.ResultListBean> resultList);
}
