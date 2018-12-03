package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/4/1.
 * description：提现佣金
 */

public class ExtractRecordBean {

    /**
     * code : 000000
     * data : {"encAmt":21,"encNum":1,"resultList":[{"logId":3288,"bizOrderNumber":"77382795180317153659","createdDate":1521272219000,"srcAmt":21,"discountFeeAmt":3,"accountNumber":"6222023803007139607","status":"s"}],"totalNum":1,"totalPage":1}
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
         * encAmt : 21.0
         * encNum : 1
         * resultList : [{"logId":3288,"bizOrderNumber":"77382795180317153659","createdDate":1521272219000,"srcAmt":21,"discountFeeAmt":3,"accountNumber":"6222023803007139607","status":"s"}]
         * totalNum : 1
         * totalPage : 1
         */

        private double encAmt;
        private int encNum;
        private int totalNum;
        private int totalPage;
        private List<ResultListBean> resultList;

        public double getEncAmt() {
            return encAmt;
        }

        public void setEncAmt(double encAmt) {
            this.encAmt = encAmt;
        }

        public int getEncNum() {
            return encNum;
        }

        public void setEncNum(int encNum) {
            this.encNum = encNum;
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
             * logId : 3288
             * bizOrderNumber : 77382795180317153659
             * createdDate : 1521272219000
             * srcAmt : 21.0
             * discountFeeAmt : 3.0
             * accountNumber : 6222023803007139607
             * status : s
             */

            private int logId;
            private String bizOrderNumber;
            private long createdDate;
            private double srcAmt;
            private double discountFeeAmt;
            private String accountNumber;
            private String status;

            public int getLogId() {
                return logId;
            }

            public void setLogId(int logId) {
                this.logId = logId;
            }

            public String getBizOrderNumber() {
                return bizOrderNumber;
            }

            public void setBizOrderNumber(String bizOrderNumber) {
                this.bizOrderNumber = bizOrderNumber;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public double getSrcAmt() {
                return srcAmt;
            }

            public void setSrcAmt(double srcAmt) {
                this.srcAmt = srcAmt;
            }

            public double getDiscountFeeAmt() {
                return discountFeeAmt;
            }

            public void setDiscountFeeAmt(double discountFeeAmt) {
                this.discountFeeAmt = discountFeeAmt;
            }

            public String getAccountNumber() {
                return accountNumber;
            }

            public void setAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
