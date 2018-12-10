package com.sl_group.jinyuntong_oem.messages.view;

import com.sl_group.jinyuntong_oem.bean.MessagesBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface MessagesView {

    /**
     * 消息列表查询成功
     *
     * @param newsListBeans 消息列表
     */
    void newsSuccess(List<MessagesBean.DataBean.ResultListBean> newsListBeans);

    /**
     * 系统公告查询成功
     *
     * @param noticeListBeans 公告列表
     */
    void noticesSuccess(List<MessagesBean.DataBean.ResultListBean> noticeListBeans);
}
