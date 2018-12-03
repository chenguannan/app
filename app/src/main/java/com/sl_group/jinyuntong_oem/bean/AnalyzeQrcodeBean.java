package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/21.
 * description：解析二维码对象
 */
public class AnalyzeQrcodeBean {

    /**
     * code : 000000
     * data : {"srcAmt":20,"shortName":"测试店铺","receivedMid":"00007gge1000003"}
     * message : 获取码信息成功
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
         * srcAmt : 20.0
         * shortName : 测试店铺
         * receivedMid : 00007gge1000003
         */

        private double srcAmt;
        private String shortName;
        private String receivedMid;

        public double getSrcAmt() {
            return srcAmt;
        }

        public void setSrcAmt(double srcAmt) {
            this.srcAmt = srcAmt;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getReceivedMid() {
            return receivedMid;
        }

        public void setReceivedMid(String receivedMid) {
            this.receivedMid = receivedMid;
        }
    }
}
