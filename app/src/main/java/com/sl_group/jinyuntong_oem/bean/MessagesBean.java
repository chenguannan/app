package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/3/2.
 * description：消息列表
 */

public class MessagesBean {


    /**
     * code : 000000
     * data : {"resultList":[{"noticeId":3,"createDate":1544007199000,"title":"依然是测试","heading":"依然是测试","content":"依然是测试依然是测试依然是测试依然是测试依然是测试依然是测试依然是测试","isReady":"t","messageId":0},{"noticeId":2,"createDate":1543904664000,"title":"测试","heading":"这是测试","content":"这是测试这是测试这是测试这是测试这是测试这是测试","isReady":"t","messageId":0},{"noticeId":1,"createDate":1543802594000,"title":"1","heading":"1","content":"1","isReady":"t","messageId":0}]}
     * message : 消息列表查询成功
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<ResultListBean> resultList;

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * noticeId : 3
             * createDate : 1544007199000
             * title : 依然是测试
             * heading : 依然是测试
             * content : 依然是测试依然是测试依然是测试依然是测试依然是测试依然是测试依然是测试
             * isReady : t
             * messageId : 0
             */

            private int noticeId;
            private long createDate;
            private String title;
            private String heading;
            private String content;
            private String isReady;
            private int messageId;

            public int getNoticeId() {
                return noticeId;
            }

            public void setNoticeId(int noticeId) {
                this.noticeId = noticeId;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHeading() {
                return heading;
            }

            public void setHeading(String heading) {
                this.heading = heading;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIsReady() {
                return isReady;
            }

            public void setIsReady(String isReady) {
                this.isReady = isReady;
            }

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }
        }
    }
}
