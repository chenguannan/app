package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/16.
 * description：通用短信
 */
public class CommonSMSBean {


    /**
     * code : 000000
     * data : {"code":"233288","uuid":"3A9D2305BD414B8083A87B6E25AFA862"}
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
         * code : 233288
         * uuid : 3A9D2305BD414B8083A87B6E25AFA862
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
