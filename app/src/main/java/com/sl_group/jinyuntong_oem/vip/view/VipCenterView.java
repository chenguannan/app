package com.sl_group.jinyuntong_oem.vip.view;

import com.sl_group.jinyuntong_oem.bean.BankFeeBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface VipCenterView {
    void getBankFeeList(List<BankFeeBean.DataBean> data);

    void buyVipSuccess(String openUrl);
}
