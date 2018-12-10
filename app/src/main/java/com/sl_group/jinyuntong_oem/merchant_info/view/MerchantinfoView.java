package com.sl_group.jinyuntong_oem.merchant_info.view;

import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface MerchantinfoView {
    /**
      * 商户信息查询成功
      * @param dataBean 商户信息对象
      */
    void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean);
}
