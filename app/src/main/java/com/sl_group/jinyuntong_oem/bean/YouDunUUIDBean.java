package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/22.
 * description：有盾UUID对象
 */
public class YouDunUUIDBean {

    /**
     * code : 000000
     * data : {"uuid":"2DDF0646ABFF4AB28C28CE8E82535456"}
     * message : 获取有盾UUID成功
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
         * uuid : 2DDF0646ABFF4AB28C28CE8E82535456
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
