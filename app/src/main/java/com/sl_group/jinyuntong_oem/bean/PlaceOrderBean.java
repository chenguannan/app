package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/21.
 * description：预下单对象
 */
public class PlaceOrderBean {

    /**
     * code : 000000
     * data : {"bizOrderNumber":"37137855181121174624","canUse":"t","isSign":"t","openHtml":null,"openUrl":"https://47.99.214.4/oss-transaction/unpay/toOpen/106a3221-2ae4-4d4a-9c74-6b3cdae92f52","tn":"37137855181121174624","txnId":0,"txnStatus":"p"}
     * message : 预下单成功
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
         * bizOrderNumber : 37137855181121174624
         * canUse : t
         * isSign : t
         * openHtml : null
         * openUrl : https://47.99.214.4/oss-transaction/unpay/toOpen/106a3221-2ae4-4d4a-9c74-6b3cdae92f52
         * tn : 37137855181121174624
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
