package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/4/1.
 * description：提现
 */

public class ExtractBean {

    /**
     * code : 000000
     * data : {"bizOrderNumber":"","srcAmt":0,"discountFeeAmt":0,"accountNumber":"","status":""}
     * message :
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
         * bizOrderNumber :
         * srcAmt : 0.0
         * discountFeeAmt : 0.0
         * accountNumber :
         * status :
         */

        private String bizOrderNumber;
        private double srcAmt;
        private double discountFeeAmt;
        private String accountNumber;
        private String status;

        public String getBizOrderNumber() {
            return bizOrderNumber;
        }

        public void setBizOrderNumber(String bizOrderNumber) {
            this.bizOrderNumber = bizOrderNumber;
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
