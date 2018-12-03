package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/14.
 * description：登录短信对象
 */
public class LoginSMSBean {

    /**
     * code : 000000
     * data : {"code":"193006","uuid":"90227C413BAF4B778D8D5AD18BDCDFD7"}
     * message : 发送验证码成功
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
         * code : 193006
         * uuid : 90227C413BAF4B778D8D5AD18BDCDFD7
         */

        private String code;
        private String uuid;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
