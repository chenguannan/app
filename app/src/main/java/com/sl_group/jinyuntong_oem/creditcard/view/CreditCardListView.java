package com.sl_group.jinyuntong_oem.creditcard.view;

import com.sl_group.jinyuntong_oem.bean.CreditCardBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/19.
 * description：UI接口
 */
public interface CreditCardListView {
    //获取信用卡列表
    void getCreditCardList(List<CreditCardBean.DataBean> list);
}
