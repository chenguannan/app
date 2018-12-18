package com.xinyilian.text.message_details.view;

import com.xinyilian.text.bean.MessageDetailsBean;

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
