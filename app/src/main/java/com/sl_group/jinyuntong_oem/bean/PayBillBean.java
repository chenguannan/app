package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：付款账单对象
 */
public class PayBillBean {


    /**
     * code : 000000
     * data : {"resultList":[{"srcAmt":5,"createdDate":1541476531000,"bizOrderNumber":"04299125181106115525","payedMid":"00007gge1000003","payAccount":"6225757537946692","payAccountName":"韩龙","state":"9","shortName":"测试店铺"},{"srcAmt":5,"createdDate":1541476685000,"bizOrderNumber":"01664038181106115802","payedMid":"00007gge1000003","payAccount":"6225757537946692","payAccountName":"韩龙","state":"9","shortName":"测试店铺"},{"srcAmt":8,"createdDate":1542793101000,"bizOrderNumber":"98216163181121173740","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":20,"createdDate":1542793587000,"bizOrderNumber":"37137855181121174624","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542855112000,"bizOrderNumber":"90067760181122105150","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542856485000,"bizOrderNumber":"30707189181122111443","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542856965000,"bizOrderNumber":"86595697181122112243","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542878796000,"bizOrderNumber":"60395891181122172634","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542878930000,"bizOrderNumber":"61618811181122172848","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1543038953000,"bizOrderNumber":"54482207181124135551","payedMid":"00007gge1000003","payAccount":"6226890122393019","payAccountName":"马天","state":"9","shortName":"测试店铺"}],"totalNum":12,"totalPage":2}
     * message : 查询付款账单列表成功
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
         * resultList : [{"srcAmt":5,"createdDate":1541476531000,"bizOrderNumber":"04299125181106115525","payedMid":"00007gge1000003","payAccount":"6225757537946692","payAccountName":"韩龙","state":"9","shortName":"测试店铺"},{"srcAmt":5,"createdDate":1541476685000,"bizOrderNumber":"01664038181106115802","payedMid":"00007gge1000003","payAccount":"6225757537946692","payAccountName":"韩龙","state":"9","shortName":"测试店铺"},{"srcAmt":8,"createdDate":1542793101000,"bizOrderNumber":"98216163181121173740","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":20,"createdDate":1542793587000,"bizOrderNumber":"37137855181121174624","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542855112000,"bizOrderNumber":"90067760181122105150","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542856485000,"bizOrderNumber":"30707189181122111443","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542856965000,"bizOrderNumber":"86595697181122112243","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542878796000,"bizOrderNumber":"60395891181122172634","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1542878930000,"bizOrderNumber":"61618811181122172848","payedMid":"00007gge1000003","payAccount":"6228369100014101","payAccountName":"马天","state":"9","shortName":"测试店铺"},{"srcAmt":100,"createdDate":1543038953000,"bizOrderNumber":"54482207181124135551","payedMid":"00007gge1000003","payAccount":"6226890122393019","payAccountName":"马天","state":"9","shortName":"测试店铺"}]
         * totalNum : 12
         * totalPage : 2
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
             * srcAmt : 5.0
             * createdDate : 1541476531000
             * bizOrderNumber : 04299125181106115525
             * payedMid : 00007gge1000003
             * payAccount : 6225757537946692
             * payAccountName : 韩龙
             * state : 9
             * shortName : 测试店铺
             */

            private double srcAmt;
            private long createdDate;
            private String bizOrderNumber;
            private String payedMid;
            private String payAccount;
            private String payAccountName;
            private String state;
            private String shortName;

            public double getSrcAmt() {
                return srcAmt;
            }

            public void setSrcAmt(double srcAmt) {
                this.srcAmt = srcAmt;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public String getBizOrderNumber() {
                return bizOrderNumber;
            }

            public void setBizOrderNumber(String bizOrderNumber) {
                this.bizOrderNumber = bizOrderNumber;
            }

            public String getPayedMid() {
                return payedMid;
            }

            public void setPayedMid(String payedMid) {
                this.payedMid = payedMid;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }
        }
    }
}
