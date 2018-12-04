package com.sl_group.jinyuntong_oem.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/4/1.
 * description：推荐的人，我的团队
 */

public class MyTeamBean {


    /**
     * code : 000000
     * data : {"directNum":1,"levelList":[{"num":1,"vipLevel":0,"levelName":"级别1"},{"num":0,"vipLevel":1,"levelName":"vip"}],"resultList":[{"participantId":290,"qualifiedState":"Y","merchantName":"17664015008","vipLevel":0,"levelName":"级别1","createdDate":1542771740000,"cellPhone":"17664015008","isRealAuth":"Y"}],"totalNum":1,"totalPage":1}
     * message : 推荐人查询成功
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
         * directNum : 1
         * levelList : [{"num":1,"vipLevel":0,"levelName":"级别1"},{"num":0,"vipLevel":1,"levelName":"vip"}]
         * resultList : [{"participantId":290,"qualifiedState":"Y","merchantName":"17664015008","vipLevel":0,"levelName":"级别1","createdDate":1542771740000,"cellPhone":"17664015008","isRealAuth":"Y"}]
         * totalNum : 1
         * totalPage : 1
         */

        private int directNum;
        private int totalNum;
        private int totalPage;
        private List<LevelListBean> levelList;
        private List<ResultListBean> resultList;

        public int getDirectNum() {
            return directNum;
        }

        public void setDirectNum(int directNum) {
            this.directNum = directNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<LevelListBean> getLevelList() {
            return levelList;
        }

        public void setLevelList(List<LevelListBean> levelList) {
            this.levelList = levelList;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class LevelListBean {
            /**
             * num : 1
             * vipLevel : 0
             * levelName : 级别1
             */

            private int num;
            private int vipLevel;
            private String levelName;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }
        }

        public static class ResultListBean {
            /**
             * participantId : 290
             * qualifiedState : Y
             * merchantName : 17664015008
             * vipLevel : 0
             * levelName : 级别1
             * createdDate : 1542771740000
             * cellPhone : 17664015008
             * isRealAuth : Y
             */

            private int participantId;
            private String qualifiedState;
            private String merchantName;
            private int vipLevel;
            private String levelName;
            private long createdDate;
            private String cellPhone;
            private String isRealAuth;

            public int getParticipantId() {
                return participantId;
            }

            public void setParticipantId(int participantId) {
                this.participantId = participantId;
            }

            public String getQualifiedState() {
                return qualifiedState;
            }

            public void setQualifiedState(String qualifiedState) {
                this.qualifiedState = qualifiedState;
            }

            public String getMerchantName() {
                return merchantName;
            }

            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public String getCellPhone() {
                return cellPhone;
            }

            public void setCellPhone(String cellPhone) {
                this.cellPhone = cellPhone;
            }

            public String getIsRealAuth() {
                return isRealAuth;
            }

            public void setIsRealAuth(String isRealAuth) {
                this.isRealAuth = isRealAuth;
            }
        }
    }
}
