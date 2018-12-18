package com.xinyilian.text.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/4/1.
 * description：直接推荐佣金，升级VIP奖励
 */

public class UpVipBrokerageBean {


    /**
     * code : 000000
     * data : {"directAmt":20,"directNum":1,"resultList":[{"createdDate":1543202570000,"logId":9,"amt":20}],"totalNum":1,"totalPage":1}
     * message : 佣金查询成功
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
         * directAmt : 20.0
         * directNum : 1
         * resultList : [{"createdDate":1543202570000,"logId":9,"amt":20}]
         * totalNum : 1
         * totalPage : 1
         */

        private double directAmt;
        private int directNum;
        private int totalNum;
        private int totalPage;
        private List<ResultListBean> resultList;

        public double getDirectAmt() {
            return directAmt;
        }

        public void setDirectAmt(double directAmt) {
            this.directAmt = directAmt;
        }

        public int getDirectNum() {
            return directNum;
        }

        public void setDirectNum(int directNum) {
            this.directNum = directNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * createdDate : 1543202570000
             * logId : 9
             * amt : 20.0
             */

            private long createdDate;
            private int logId;
            private double amt;

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public int getLogId() {
                return logId;
            }

            public void setLogId(int logId) {
                this.logId = logId;
            }

            public double getAmt() {
                return amt;
            }

            public void setAmt(double amt) {
                this.amt = amt;
            }
        }
    }
}
