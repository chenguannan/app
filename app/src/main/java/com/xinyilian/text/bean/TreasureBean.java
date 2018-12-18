package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/3/31.
 * description：业绩，财富
 */

public class TreasureBean {


    /**
     * code : 000000
     * data : {"balance":0,"bountyAmt":0,"bountyNum":0,"creDirAmt":0,"creDirNum":0,"creFixAmt":0,"creFixNum":0,"creditAmt":0,"creditNum":0,"directAmt":0,"directNum":0,"encAmt":0,"encNum":0,"enchFee":2,"fixedNum":0,"fixedcAmt":0,"indirectAmt":0,"indirectNum":0,"loanAmt":0,"loanNum":0,"txnAmt":0,"txnNum":0}
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
         * balance : 0.0
         * bountyAmt : 0.0
         * bountyNum : 0
         * creDirAmt : 0.0
         * creDirNum : 0
         * creFixAmt : 0.0
         * creFixNum : 0
         * creditAmt : 0.0
         * creditNum : 0
         * directAmt : 0.0
         * directNum : 0
         * encAmt : 0.0
         * encNum : 0
         * enchFee : 2.0
         * fixedNum : 0
         * fixedcAmt : 0.0
         * indirectAmt : 0.0
         * indirectNum : 0
         * loanAmt : 0.0
         * loanNum : 0
         * txnAmt : 0.0
         * txnNum : 0
         */

        private double balance;
        private double bountyAmt;
        private int bountyNum;
        private double creDirAmt;
        private int creDirNum;
        private double creFixAmt;
        private int creFixNum;
        private double creditAmt;
        private int creditNum;
        private double directAmt;
        private int directNum;
        private double encAmt;
        private int encNum;
        private double enchFee;
        private int fixedNum;
        private double fixedcAmt;
        private double indirectAmt;
        private int indirectNum;
        private double loanAmt;
        private int loanNum;
        private double txnAmt;
        private int txnNum;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getBountyAmt() {
            return bountyAmt;
        }

        public void setBountyAmt(double bountyAmt) {
            this.bountyAmt = bountyAmt;
        }

        public int getBountyNum() {
            return bountyNum;
        }

        public void setBountyNum(int bountyNum) {
            this.bountyNum = bountyNum;
        }

        public double getCreDirAmt() {
            return creDirAmt;
        }

        public void setCreDirAmt(double creDirAmt) {
            this.creDirAmt = creDirAmt;
        }

        public int getCreDirNum() {
            return creDirNum;
        }

        public void setCreDirNum(int creDirNum) {
            this.creDirNum = creDirNum;
        }

        public double getCreFixAmt() {
            return creFixAmt;
        }

        public void setCreFixAmt(double creFixAmt) {
            this.creFixAmt = creFixAmt;
        }

        public int getCreFixNum() {
            return creFixNum;
        }

        public void setCreFixNum(int creFixNum) {
            this.creFixNum = creFixNum;
        }

        public double getCreditAmt() {
            return creditAmt;
        }

        public void setCreditAmt(double creditAmt) {
            this.creditAmt = creditAmt;
        }

        public int getCreditNum() {
            return creditNum;
        }

        public void setCreditNum(int creditNum) {
            this.creditNum = creditNum;
        }

        public double getDirectAmt() {
            return directAmt;
        }

        public void setDirectAmt(double directAmt) {
            this.directAmt = directAmt;
        }

        public int getDirectNum() {
            return directNum;
        }

        public void setDirectNum(int directNum) {
            this.directNum = directNum;
        }

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

        public double getEnchFee() {
            return enchFee;
        }

        public void setEnchFee(double enchFee) {
            this.enchFee = enchFee;
        }

        public int getFixedNum() {
            return fixedNum;
        }

        public void setFixedNum(int fixedNum) {
            this.fixedNum = fixedNum;
        }

        public double getFixedcAmt() {
            return fixedcAmt;
        }

        public void setFixedcAmt(double fixedcAmt) {
            this.fixedcAmt = fixedcAmt;
        }

        public double getIndirectAmt() {
            return indirectAmt;
        }

        public void setIndirectAmt(double indirectAmt) {
            this.indirectAmt = indirectAmt;
        }

        public int getIndirectNum() {
            return indirectNum;
        }

        public void setIndirectNum(int indirectNum) {
            this.indirectNum = indirectNum;
        }

        public double getLoanAmt() {
            return loanAmt;
        }

        public void setLoanAmt(double loanAmt) {
            this.loanAmt = loanAmt;
        }

        public int getLoanNum() {
            return loanNum;
        }

        public void setLoanNum(int loanNum) {
            this.loanNum = loanNum;
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
    }
}
