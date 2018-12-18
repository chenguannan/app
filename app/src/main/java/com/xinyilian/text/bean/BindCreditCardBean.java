package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/19.
 * description：添加信用卡对象
 */
public class BindCreditCardBean {

    /**
     * code : 000000
     * data : https://47.99.214.4/oss-transaction/unpay/toOpen/ac049773-a031-4daf-941a-0add2e5e27e3
     * message : 提交交易卡成功
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
