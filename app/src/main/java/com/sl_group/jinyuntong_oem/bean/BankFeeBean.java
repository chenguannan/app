package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/21.
 * description：银行费率对象
 */
public class BankFeeBean {


    /**
     * code : 000000
     * data : [{"bankName":"中国工商银行","fastpayFee":0.39,"extraFee":1.1,"vipLevel":0},{"bankName":"中国工商银行","fastpayFee":0.35,"extraFee":0.8,"vipLevel":1},{"bankName":"中国农业银行股份有限公司","fastpayFee":0.36,"extraFee":2,"vipLevel":0},{"bankName":"中国农业银行股份有限公司","fastpayFee":0.8,"extraFee":0.1,"vipLevel":1}]
     * message : 查询银行费率成功
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bankName : 中国工商银行
         * fastpayFee : 0.39
         * extraFee : 1.1
         * vipLevel : 0
         */

        private String bankName;
        private double fastpayFee;
        private double extraFee;
        private int vipLevel;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public double getFastpayFee() {
            return fastpayFee;
        }

        public void setFastpayFee(double fastpayFee) {
            this.fastpayFee = fastpayFee;
        }

        public double getExtraFee() {
            return extraFee;
        }

        public void setExtraFee(double extraFee) {
            this.extraFee = extraFee;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }
    }
}
