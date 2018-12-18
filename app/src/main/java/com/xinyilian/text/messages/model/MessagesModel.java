package com.xinyilian.text.messages.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface MessagesModel {

    /**
     * @param type             信息类型，消息或系统公告
     * @param isShowProgress   是否显示加载进度条
     * @param curPage          当前页码
     * @param pageSize         每页条数
     * @param isReady          是否阅读
     * @param messagesCallBack 查询成功回调
     */
    void messages(String type, boolean isShowProgress, int curPage, String pageSize, String isReady, IMessagesCallBack messagesCallBack);

    interface IMessagesCallBack {
        void onSuccess(String data);
    }
}
