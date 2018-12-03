package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/26.
 * description：连接对象
 */
public class SystemPropBean {

    /**
     * code : 000000
     * data : {"kefu":"https://www.znyoo.com","xieyi":"https://www.znyoo.com","xinshou":"https://www.znyoo.com"}
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
         * kefu : https://www.znyoo.com
         * xieyi : https://www.znyoo.com
         * xinshou : https://www.znyoo.com
         */

        private String kefu;
        private String xieyi;
        private String xinshou;

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
    }
}
