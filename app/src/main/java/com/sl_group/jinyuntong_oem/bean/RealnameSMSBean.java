package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/16.
 * description：通用短信
 */
public class RealnameSMSBean {


    /**
     * code : 000000
     * data : {"uuid":"D9AE389B814747AFB0561A14EB1A7807"}
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
         * uuid : D9AE389B814747AFB0561A14EB1A7807
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
