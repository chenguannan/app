package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/22.
 * description：购买VIP
 */
public class BuyVipBean {

    /**
     * code : 000000
     * data : {"bizOrderNumber":"90067760181122105150","canUse":"t","isSign":"t","openHtml":null,"openUrl":"https://47.99.214.4/oss-transaction/unpay/toOpen/d7e6383c-7a7c-4f04-acf9-e2c70b9f9960","tn":"90067760181122105150","txnId":0,"txnStatus":"p"}
     * message : 快捷支付提升等级请求成功
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
         * bizOrderNumber : 90067760181122105150
         * canUse : t
         * isSign : t
         * openHtml : null
         * openUrl : https://47.99.214.4/oss-transaction/unpay/toOpen/d7e6383c-7a7c-4f04-acf9-e2c70b9f9960
         * tn : 90067760181122105150
         * txnId : 0
         * txnStatus : p
         */

        private String bizOrderNumber;
        private String canUse;
        private String isSign;
        private Object openHtml;
        private String openUrl;
        private String tn;
        private int txnId;
        private String txnStatus;

        public String getBizOrderNumber() {
            return bizOrderNumber;
        }

        public void setBizOrderNumber(String bizOrderNumber) {
            this.bizOrderNumber = bizOrderNumber;
        }

        public String getCanUse() {
            return canUse;
        }

        public void setCanUse(String canUse) {
            this.canUse = canUse;
        }

        public String getIsSign() {
            return isSign;
        }

        public void setIsSign(String isSign) {
            this.isSign = isSign;
        }

        public Object getOpenHtml() {
            return openHtml;
        }

        public void setOpenHtml(Object openHtml) {
            this.openHtml = openHtml;
        }

        public String getOpenUrl() {
            return openUrl;
        }

        public void setOpenUrl(String openUrl) {
            this.openUrl = openUrl;
        }

        public String getTn() {
            return tn;
        }

        public void setTn(String tn) {
            this.tn = tn;
        }

        public int getTxnId() {
            return txnId;
        }

        public void setTxnId(int txnId) {
            this.txnId = txnId;
        }

        public String getTxnStatus() {
            return txnStatus;
        }

        public void setTxnStatus(String txnStatus) {
            this.txnStatus = txnStatus;
        }
    }
}
