package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/12.
 * description：短信验证码对象
 */
public class RegisterSMSBean {


    /**
     * code : 000000
     * data : {"uuid":"ED8617ACDE1942548D6D6F8360BBCB8A"}
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
         * uuid : ED8617ACDE1942548D6D6F8360BBCB8A
         */

        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
