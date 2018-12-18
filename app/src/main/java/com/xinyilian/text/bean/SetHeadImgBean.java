package com.xinyilian.text.bean;

/**
 * Created by 马天 on 2018/11/22.
 * description：设置头像
 */
public class SetHeadImgBean {


    /**
     * code : 000000
     * data : {"headPortraitDirectoryName":"3f0a25040d514dd0824eb89fa302e80b.jpg","headPortraitFilePrefix":"/oss/shared/anonymousImage/285"}
     * message : 头像设置成功
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
         * headPortraitDirectoryName : 3f0a25040d514dd0824eb89fa302e80b.jpg
         * headPortraitFilePrefix : /oss/shared/anonymousImage/285
         */

        private String headPortraitDirectoryName;
        private String headPortraitFilePrefix;

        public String getHeadPortraitDirectoryName() {
            return headPortraitDirectoryName;
        }

        public void setHeadPortraitDirectoryName(String headPortraitDirectoryName) {
            this.headPortraitDirectoryName = headPortraitDirectoryName;
        }

        public String getHeadPortraitFilePrefix() {
            return headPortraitFilePrefix;
        }

        public void setHeadPortraitFilePrefix(String headPortraitFilePrefix) {
            this.headPortraitFilePrefix = headPortraitFilePrefix;
        }
    }
}
