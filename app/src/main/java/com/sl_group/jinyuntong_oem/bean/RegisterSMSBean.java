package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/12.
 * description：短信验证码对象
 */
public class RegisterSMSBean {


    /**
     * code : 000000
     * data : {"code":"964213"}
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
         * code : 964213
         */

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
