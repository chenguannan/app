package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/3/2.
 * description：消息
 */

public class NewsBean {


    /**
     * code : 000000
     * data : {"resultList":[{"messageId":17,"participantId":504,"account":"stage00005kjd7000003","createDate":1523512006000,"title":"消息2","heading":"消息2","content":"消息2","date":1523509200000,"isReady":"f","system":"Android"}],"totalNum":1}
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
        /**
         * resultList : [{"messageId":17,"participantId":504,"account":"stage00005kjd7000003","createDate":1523512006000,"title":"消息2","heading":"消息2","content":"消息2","date":1523509200000,"isReady":"f","system":"Android"}]
         * totalNum : 1
         */

        private int totalNum;
        private List<ResultListBean> resultList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * messageId : 17
             * participantId : 504
             * account : stage00005kjd7000003
             * createDate : 1523512006000
             * title : 消息2
             * heading : 消息2
             * content : 消息2
             * date : 1523509200000
             * isReady : f
             * system : Android
             */

            private int messageId;
            private int participantId;
            private String account;
            private long createDate;
            private String title;
            private String heading;
            private String content;
            private long date;
            private String isReady;
            private String system;

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public int getParticipantId() {
                return participantId;
            }

            public void setParticipantId(int participantId) {
                this.participantId = participantId;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
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

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public String getIsReady() {
                return isReady;
            }

            public void setIsReady(String isReady) {
                this.isReady = isReady;
            }

            public String getSystem() {
                return system;
            }

            public void setSystem(String system) {
                this.system = system;
            }
        }
    }
}
