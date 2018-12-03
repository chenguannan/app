package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/16.
 * description：实名认证短信
 */
public class CommonSMSBean {

    /**
     * code : 000000
     * data : 748776
     * message : 发送验证码成功
     */

    private String code;
    private String data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
