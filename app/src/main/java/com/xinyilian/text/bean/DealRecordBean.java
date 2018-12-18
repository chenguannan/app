package com.xinyilian.text.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/4/1.
 * description：交易佣金
 */

public class DealRecordBean {

    /**
     * code : 000000
     * data : {"resultList":[{"createdDate":1517804856000,"logId":1229,"amt":1.72},{"createdDate":1517710537000,"logId":1174,"amt":0.27},{"createdDate":1517709346000,"logId":1169,"amt":0.25},{"createdDate":1517469086000,"logId":1012,"amt":4},{"createdDate":1517466966000,"logId":1006,"amt":4},{"createdDate":1517466552000,"logId":1002,"amt":3.98},{"createdDate":1517101941000,"logId":815,"amt":3.64},{"createdDate":1517101617000,"logId":811,"amt":3.97}],"totalNum":8,"totalPage":1,"txnAmt":21.83,"txnNum":8}
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
         * resultList : [{"createdDate":1517804856000,"logId":1229,"amt":1.72},{"createdDate":1517710537000,"logId":1174,"amt":0.27},{"createdDate":1517709346000,"logId":1169,"amt":0.25},{"createdDate":1517469086000,"logId":1012,"amt":4},{"createdDate":1517466966000,"logId":1006,"amt":4},{"createdDate":1517466552000,"logId":1002,"amt":3.98},{"createdDate":1517101941000,"logId":815,"amt":3.64},{"createdDate":1517101617000,"logId":811,"amt":3.97}]
         * totalNum : 8
         * totalPage : 1
         * txnAmt : 21.83
         * txnNum : 8
         */

        private int totalNum;
        private int totalPage;
        private double txnAmt;
        private int txnNum;
        private double fixedcAmt;
        private int fixedNum;
        private double directAmt;
        private int directNum;
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

        public double getTxnAmt() {
            return txnAmt;
        }

        public void setTxnAmt(double txnAmt) {
            this.txnAmt = txnAmt;
        }

        public int getTxnNum() {
            return txnNum;
        }

        public void setTxnNum(int txnNum) {
            this.txnNum = txnNum;
        }

        public double getFixedcAmt() {
            return fixedcAmt;
        }

        public void setFixedcAmt(double fixedcAmt) {
            this.fixedcAmt = fixedcAmt;
        }

        public int getFixedNum() {
            return fixedNum;
        }

        public void setFixedNum(int fixedNum) {
            this.fixedNum = fixedNum;
        }

        public double getDirectAmt() {
            return fixedcAmt;
        }

        public void setDirectAmt(double fixedcAmt) {
            this.fixedcAmt = fixedcAmt;
        }

        public int getDirectNum() {
            return fixedNum;
        }

        public void setDirectNum(int fixedNum) {
            this.fixedNum = fixedNum;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * createdDate : 1517804856000
             * logId : 1229
             * amt : 1.72
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
