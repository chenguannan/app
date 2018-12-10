package com.sl_group.jinyuntong_oem.message_details.view;

import com.sl_group.jinyuntong_oem.bean.MessageDetailsBean;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface MessageDetailsView {
    /**
      *
      * @param data 信息详情对象
      */
    void messageDetailsSuccess(MessageDetailsBean.DataBean data);
}
