package com.xinyilian.text.creditcard.view;

import com.xinyilian.text.bean.CreditCardBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/19.
 * description：UI接口
 */
public interface CreditCardListView {
    //获取信用卡列表
    void getCreditCardList(List<CreditCardBean.DataBean> list);
}
