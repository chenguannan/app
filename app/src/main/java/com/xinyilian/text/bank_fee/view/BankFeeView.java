package com.xinyilian.text.bank_fee.view;

import com.xinyilian.text.bean.BankFeeBean;

import java.util.List;

/**
 * Created by 马天 on 2018/12/10.
 * description：
 */
public interface BankFeeView {
    void bankFeeSuccess(List<BankFeeBean.DataBean> data);
}
