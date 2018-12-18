package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/20.
 * description：开通商户权限
 */
public class OpenMerchantBean {

    /**
     * code : 000000
     * data : https://47.99.214.4/oss-transaction/unpay/toOpen/1dd63a63-b5d5-4de8-9450-004d438ba37a
     * message : 请求开通商户成功
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
