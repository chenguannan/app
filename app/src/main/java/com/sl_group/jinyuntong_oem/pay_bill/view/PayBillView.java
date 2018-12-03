package com.sl_group.jinyuntong_oem.pay_bill.view;

import com.sl_group.jinyuntong_oem.bean.PayBillBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface PayBillView {
    void getPayBillList(List<PayBillBean.DataBean.ResultListBean> resultList);
}
