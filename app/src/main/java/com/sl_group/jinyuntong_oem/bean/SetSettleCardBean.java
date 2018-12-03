package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public class SetSettleCardBean {

    /**
     * code : 000000
     * data : https://47.99.214.4/oss-transaction/unpay/toOpen/cccb43a3-b4fa-4cc8-bb16-3b68b658d10c
     * message : 提交结算卡成功
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
