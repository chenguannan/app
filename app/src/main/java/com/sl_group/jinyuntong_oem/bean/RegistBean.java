package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/26.
 * description：注册对象
 */
public class RegistBean {

    /**
     * code : 000000
     * data : {"encryptId":"00007gge1000005","key":"7ec82d16a5425519a21cea51ac4a7b37","mid":"00007gge1000005"}
     * message : 新增商户成功
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
         * encryptId : 00007gge1000005
         * key : 7ec82d16a5425519a21cea51ac4a7b37
         * mid : 00007gge1000005
         */

        private String encryptId;
        private String key;
        private String mid;

        public String getEncryptId() {
            return encryptId;
        }

        public void setEncryptId(String encryptId) {
            this.encryptId = encryptId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }
    }
}
