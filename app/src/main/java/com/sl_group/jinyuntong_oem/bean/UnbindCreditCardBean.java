package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/5/10.
 * description：
 */

public class UnbindCreditCardBean {

    /**
     * code : 000000
     * data : 1
     * message : 信用卡删除成功
     */

    private String code;
    private int data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
