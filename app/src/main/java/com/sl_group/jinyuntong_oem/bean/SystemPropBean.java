package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/26.
 * description：各种连接，协议，客服，新手指引
 */
public class SystemPropBean {

    /**
     * code : 000000
     * data : {"kefu":"https://yapp.jytpay.com/oss-app/contract2/contract.html","xieyi":"https://yapp.jytpay.com/oss-app/contract2/protocol.html","xinshou":"https://yapp.jytpay.com/oss-app/newGuide/newGuide.html","yaoqing":"https://yapp.jytpay.com/oss-app/contract/inviteId.html"}
     * message : 手势密码设置成功
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
         * kefu : https://yapp.jytpay.com/oss-app/contract2/contract.html
         * xieyi : https://yapp.jytpay.com/oss-app/contract2/protocol.html
         * xinshou : https://yapp.jytpay.com/oss-app/newGuide/newGuide.html
         * yaoqing : https://yapp.jytpay.com/oss-app/contract/inviteId.html
         */

        private String kefu;
        private String xieyi;
        private String xinshou;
        private String yaoqing;

        public String getKefu() {
            return kefu;
        }

        public void setKefu(String kefu) {
            this.kefu = kefu;
        }

        public String getXieyi() {
            return xieyi;
        }

        public void setXieyi(String xieyi) {
            this.xieyi = xieyi;
        }

        public String getXinshou() {
            return xinshou;
        }

        public void setXinshou(String xinshou) {
            this.xinshou = xinshou;
        }

        public String getYaoqing() {
            return yaoqing;
        }

        public void setYaoqing(String yaoqing) {
            this.yaoqing = yaoqing;
        }
    }
}
