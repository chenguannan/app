package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/4/1.
 * description：交易佣金详情
 */

public class DealBrokerageDetailsBean {

    /**
     * code : 000000
     * data : {"createdDate":1517804856000,"amt":1.72,"merchantName":"张俊"}
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
         * createdDate : 1517804856000
         * amt : 1.72
         * merchantName : 张俊
         */

        private long createdDate;
        private double amt;
        private String merchantName;

        public long getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(long createdDate) {
            this.createdDate = createdDate;
        }

        public double getAmt() {
            return amt;
        }

        public void setAmt(double amt) {
            this.amt = amt;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }
    }
}
