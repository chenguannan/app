package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/4/1.
 * description：提现佣金详情
 */

public class ExtractBrokerageDetailsBean {

    /**
     * code : 000000
     * data : {"bizOrderNumber":"77382795180317153659","createdDate":1521272219000,"srcAmt":21,"discountFeeAmt":3,"accountNumber":"6222023803007139607","status":"s","remark":""}
     * message : 佣金详情查询成功
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
         * bizOrderNumber : 77382795180317153659
         * createdDate : 1521272219000
         * srcAmt : 21.0
         * discountFeeAmt : 3.0
         * accountNumber : 6222023803007139607
         * status : s
         * remark :
         */

        private String bizOrderNumber;
        private long createdDate;
        private double srcAmt;
        private double discountFeeAmt;
        private String accountNumber;
        private String status;
        private String remark;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
