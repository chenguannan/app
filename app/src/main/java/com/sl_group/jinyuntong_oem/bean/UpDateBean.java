package com.sl_group.jinyuntong_oem.bean;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public class UpDateBean {

    /**
     * code : 000000
     * data : {"version":"13","content":"界面优化！","url":"http://jyfapp.huilianwifi.cn/jiyifu_13_2.2.apk","verName":"2.2"}
     * message : 查询成功
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
         * version : 13
         * content : 界面优化！
         * url : http://jyfapp.huilianwifi.cn/jiyifu_13_2.2.apk
         * verName : 2.2
         */

        private String version;
        private String content;
        private String url;
        private String verName;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }
    }
}
