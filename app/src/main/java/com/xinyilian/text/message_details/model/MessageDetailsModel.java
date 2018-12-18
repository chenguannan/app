package com.xinyilian.text.message_details.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：消息详情
 */
public interface MessageDetailsModel {
    /**
      *
      * @param messageId 消息id
      * @param isReady 是否阅读
      * @param noticeId 公告id
      * @param messageDetailsCallBack 信息查询成功回调
      */
    void messageDetails(String messageId,String isReady, String noticeId, IMessageDetailsCallBack messageDetailsCallBack);
    interface IMessageDetailsCallBack {
        void onSuccess(String data);
    }
}
