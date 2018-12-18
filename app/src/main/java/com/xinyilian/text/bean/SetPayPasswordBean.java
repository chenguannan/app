package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/21.
 * description：设置支付密码
 */
public class SetPayPasswordBean {

    /**
     * code : 000000
     * data : s
     * message : 支付密码设置成功
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
