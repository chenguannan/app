package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/3/30.
 * description：信用卡列表
 */

public class CreditCardBean {


    /**
     * code : 000000
     * data : [{"fastpayBankAccountInfoId":3,"createdDate":null,"createdBy":null,"lastModifiedTime":1542771195000,"accountNumber":"FAC5E32C2B2B3F92EA3E527AD09091B0","holderName":"马天","idcard":"ABCBDF31D230BB6BADBB8B7567F7B8089411D14C08AB5823","tel":"164618465168","expiredDate":null,"cvv2":null,"mid":"00007gge1000003","remark":null,"cardFilePrefix":null,"cardDirectoryName":null,"qualifiedState":null,"extraParams":null,"bankName":"中国农业银行","bankCode":"103100000026","cardType":"借记卡","showAccountNumber":"622836******4101"}]
     * message : 个人卡包查询成功
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
         * fastpayBankAccountInfoId : 3
         * createdDate : null
         * createdBy : null
         * lastModifiedTime : 1542771195000
         * accountNumber : FAC5E32C2B2B3F92EA3E527AD09091B0
         * holderName : 马天
         * idcard : ABCBDF31D230BB6BADBB8B7567F7B8089411D14C08AB5823
         * tel : 164618465168
         * expiredDate : null
         * cvv2 : null
         * mid : 00007gge1000003
         * remark : null
         * cardFilePrefix : null
         * cardDirectoryName : null
         * qualifiedState : null
         * extraParams : null
         * bankName : 中国农业银行
         * bankCode : 103100000026
         * cardType : 借记卡
         * showAccountNumber : 622836******4101
         */

        private int fastpayBankAccountInfoId;
        private Object createdDate;
        private Object createdBy;
        private long lastModifiedTime;
        private String accountNumber;
        private String holderName;
        private String idcard;
        private String tel;
        private Object expiredDate;
        private Object cvv2;
        private String mid;
        private Object remark;
        private Object cardFilePrefix;
        private Object cardDirectoryName;
        private Object qualifiedState;
        private Object extraParams;
        private String bankName;
        private String bankCode;
        private String cardType;
        private String showAccountNumber;

        public int getFastpayBankAccountInfoId() {
            return fastpayBankAccountInfoId;
        }

        public void setFastpayBankAccountInfoId(int fastpayBankAccountInfoId) {
            this.fastpayBankAccountInfoId = fastpayBankAccountInfoId;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public long getLastModifiedTime() {
            return lastModifiedTime;
        }

        public void setLastModifiedTime(long lastModifiedTime) {
            this.lastModifiedTime = lastModifiedTime;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getHolderName() {
            return holderName;
        }

        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Object getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(Object expiredDate) {
            this.expiredDate = expiredDate;
        }

        public Object getCvv2() {
            return cvv2;
        }

        public void setCvv2(Object cvv2) {
            this.cvv2 = cvv2;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getCardFilePrefix() {
            return cardFilePrefix;
        }

        public void setCardFilePrefix(Object cardFilePrefix) {
            this.cardFilePrefix = cardFilePrefix;
        }

        public Object getCardDirectoryName() {
            return cardDirectoryName;
        }

        public void setCardDirectoryName(Object cardDirectoryName) {
            this.cardDirectoryName = cardDirectoryName;
        }

        public Object getQualifiedState() {
            return qualifiedState;
        }

        public void setQualifiedState(Object qualifiedState) {
            this.qualifiedState = qualifiedState;
        }

        public Object getExtraParams() {
            return extraParams;
        }

        public void setExtraParams(Object extraParams) {
            this.extraParams = extraParams;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getShowAccountNumber() {
            return showAccountNumber;
        }

        public void setShowAccountNumber(String showAccountNumber) {
            this.showAccountNumber = showAccountNumber;
        }
    }
}
