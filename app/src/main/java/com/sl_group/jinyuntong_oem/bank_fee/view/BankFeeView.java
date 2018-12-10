package com.sl_group.jinyuntong_oem.bank_fee.view;

import com.sl_group.jinyuntong_oem.bean.BankFeeBean;

import java.util.List;

/**
 * Created by 马天 on 2018/12/10.
 * description：
 */
public interface BankFeeView {
    void bankFeeSuccess(List<BankFeeBean.DataBean> data);
}
