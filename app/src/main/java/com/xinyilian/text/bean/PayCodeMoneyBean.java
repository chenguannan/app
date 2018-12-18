package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/21.
 * description：带金额收款码对象
 */
public class PayCodeMoneyBean {

    /**
     * code : 000000
     * data : {"uuid":"596279FBA5AE4EE0BC26B826BEA2E3DE"}
     * message : 获取收款二维码成功
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
         * uuid : 596279FBA5AE4EE0BC26B826BEA2E3DE
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
