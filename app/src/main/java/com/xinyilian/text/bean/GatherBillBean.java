package com.xinyilian.text.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：收款账单
 */
public class GatherBillBean {


    /**
     * code : 000000
     * data : {"resultList":[{"srcAmt":8,"state":"9","createdDate":1542793101000,"discountFeeAmt":0,"payAccount":"6228369100014101","payAccountName":"马天","bizOrderNumber":"98216163181121173740","extraFee":2,"settlementStatus":"a","accountNumber":"6227002397540682094"},{"srcAmt":20,"state":"9","createdDate":1542793587000,"discountFeeAmt":0,"payAccount":"6228369100014101","payAccountName":"马天","bizOrderNumber":"37137855181121174624","extraFee":2,"settlementStatus":"a","accountNumber":"6227002397540682094"}],"totalNum":2,"totalPage":1}
     * message : 查询收款账单列表成功
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
         * resultList : [{"srcAmt":8,"state":"9","createdDate":1542793101000,"discountFeeAmt":0,"payAccount":"6228369100014101","payAccountName":"马天","bizOrderNumber":"98216163181121173740","extraFee":2,"settlementStatus":"a","accountNumber":"6227002397540682094"},{"srcAmt":20,"state":"9","createdDate":1542793587000,"discountFeeAmt":0,"payAccount":"6228369100014101","payAccountName":"马天","bizOrderNumber":"37137855181121174624","extraFee":2,"settlementStatus":"a","accountNumber":"6227002397540682094"}]
         * totalNum : 2
         * totalPage : 1
         */

        private int totalNum;
        private int totalPage;
        private List<ResultListBean> resultList;

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
             * srcAmt : 8.0
             * state : 9
             * createdDate : 1542793101000
             * discountFeeAmt : 0.0
             * payAccount : 6228369100014101
             * payAccountName : 马天
             * bizOrderNumber : 98216163181121173740
             * extraFee : 2.0
             * settlementStatus : a
             * accountNumber : 6227002397540682094
             */

            private double srcAmt;
            private String state;
            private long createdDate;
            private double discountFeeAmt;
            private String payAccount;
            private String payAccountName;
            private String bizOrderNumber;
            private double extraFee;
            private String settlementStatus;
            private String accountNumber;

            public double getSrcAmt() {
                return srcAmt;
            }

            public void setSrcAmt(double srcAmt) {
                this.srcAmt = srcAmt;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public double getDiscountFeeAmt() {
                return discountFeeAmt;
            }

            public void setDiscountFeeAmt(double discountFeeAmt) {
                this.discountFeeAmt = discountFeeAmt;
            }

            public String getPayAccount() {
                return payAccount;
            }

            public void setPayAccount(String payAccount) {
                this.payAccount = payAccount;
            }

            public String getPayAccountName() {
                return payAccountName;
            }

            public void setPayAccountName(String payAccountName) {
                this.payAccountName = payAccountName;
            }

            public String getBizOrderNumber() {
                return bizOrderNumber;
            }

            public void setBizOrderNumber(String bizOrderNumber) {
                this.bizOrderNumber = bizOrderNumber;
            }

            public double getExtraFee() {
                return extraFee;
            }

            public void setExtraFee(double extraFee) {
                this.extraFee = extraFee;
            }

            public String getSettlementStatus() {
                return settlementStatus;
            }

            public void setSettlementStatus(String settlementStatus) {
                this.settlementStatus = settlementStatus;
            }

            public String getAccountNumber() {
                return accountNumber;
            }

            public void setAccountNumber(String accountNumber) {
                this.accountNumber = accountNumber;
            }
        }
    }
}
