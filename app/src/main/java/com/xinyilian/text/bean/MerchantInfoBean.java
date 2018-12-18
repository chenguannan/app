package com.xinyilian.text.bean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/15.
 * description：商户信息
 */
public class MerchantInfoBean {


    /**
     * code : 000000
     * data : {"shortName":"测试店铺","shopAddress":"山东省-青岛市-李沧区","bizLicenseFilePrefix":"/oss/shared/anonymousImage/285","bizLicenseDirectoryName":"17cde3b78065439a8472179a2a479cd5.jpg","bizPlaceSnapshotFilePrefix":"/oss/shared/anonymousImage/285","bizPlaceSnapshotDirectoryName":"c25df3147fd84a568712eb889c6f510a.jpg","bizPlaceSnapshot1FilePrefix":"/oss/shared/anonymousImage/285","bizPlaceSnapshot1DirectoryName":"f7554a4a496743e4931a7ebc0a8e13db.jpg","headPortraitDirectoryName":"73ae8597cbbe489fab698f500cccbc8f.jpg","headPortraitFilePrefix":"/oss/shared/anonymousImage/285","merchantName":"18561619712","mid":"00007gge1000003","participantId":288,"vipLevel":0,"agentName":"测试","agentId":"00007gge1","type":104,"refId":285,"refName":"测试","refCellPhone":"13812341234","cellPhone":"18561619712","firstName":"18561619712","agencyCode":"00007","YDRemNum":3,"agencyId":281,"qualifiedState":"Y","accountNumber":"6227002397540682094","tel":"18561619712","depositBank":"中国建设银行","enchFee":2,"holderName":"马天","idCard":"341202199212212712","canReceived":"t","payCode":"1099af08-e64c-42ce-b903-c2079bfe73dd","gesturePassword":"01478","tradePassword":"CpievEp3tWpuK7exnZldGFzkQJDBPimEt+zG1EbUth6pmRt2pMLwSxtNJEhBRJRU","vipTime":null,"copyWriting":[],"stageExtraFee":"2","title":"金运通","content":"欢迎使用金运通","url":"http://www.juqick.cn/jinyuntong/index.html","ruleUrl":"http://www.juqick.cn/jinyuntong/index.html"}
     * message : 个人商户信息查询
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
         * shortName : 测试店铺
         * shopAddress : 山东省-青岛市-李沧区
         * bizLicenseFilePrefix : /oss/shared/anonymousImage/285
         * bizLicenseDirectoryName : 17cde3b78065439a8472179a2a479cd5.jpg
         * bizPlaceSnapshotFilePrefix : /oss/shared/anonymousImage/285
         * bizPlaceSnapshotDirectoryName : c25df3147fd84a568712eb889c6f510a.jpg
         * bizPlaceSnapshot1FilePrefix : /oss/shared/anonymousImage/285
         * bizPlaceSnapshot1DirectoryName : f7554a4a496743e4931a7ebc0a8e13db.jpg
         * headPortraitDirectoryName : 73ae8597cbbe489fab698f500cccbc8f.jpg
         * headPortraitFilePrefix : /oss/shared/anonymousImage/285
         * merchantName : 18561619712
         * mid : 00007gge1000003
         * participantId : 288
         * vipLevel : 0
         * agentName : 测试
         * agentId : 00007gge1
         * type : 104
         * refId : 285
         * refName : 测试
         * refCellPhone : 13812341234
         * cellPhone : 18561619712
         * firstName : 18561619712
         * agencyCode : 00007
         * YDRemNum : 3
         * agencyId : 281
         * qualifiedState : Y
         * accountNumber : 6227002397540682094
         * tel : 18561619712
         * depositBank : 中国建设银行
         * enchFee : 2.0
         * holderName : 马天
         * idCard : 341202199212212712
         * canReceived : t
         * payCode : 1099af08-e64c-42ce-b903-c2079bfe73dd
         * gesturePassword : 01478
         * tradePassword : CpievEp3tWpuK7exnZldGFzkQJDBPimEt+zG1EbUth6pmRt2pMLwSxtNJEhBRJRU
         * vipTime : null
         * copyWriting : []
         * stageExtraFee : 2
         * title : 金运通
         * content : 欢迎使用金运通
         * url : http://www.juqick.cn/jinyuntong/index.html
         * ruleUrl : http://www.juqick.cn/jinyuntong/index.html
         */

        private String shortName;
        private String shopAddress;
        private String bizLicenseFilePrefix;
        private String bizLicenseDirectoryName;
        private String bizPlaceSnapshotFilePrefix;
        private String bizPlaceSnapshotDirectoryName;
        private String bizPlaceSnapshot1FilePrefix;
        private String bizPlaceSnapshot1DirectoryName;
        private String headPortraitDirectoryName;
        private String headPortraitFilePrefix;
        private String merchantName;
        private String mid;
        private int participantId;
        private int vipLevel;
        private String agentName;
        private String agentId;
        private int type;
        private int refId;
        private String refName;
        private String refCellPhone;
        private String cellPhone;
        private String firstName;
        private String agencyCode;
        private int YDRemNum;
        private int agencyId;
        private String qualifiedState;
        private String accountNumber;
        private String tel;
        private String depositBank;
        private double enchFee;
        private String holderName;
        private String idCard;
        private String canReceived;
        private String payCode;
        private String gesturePassword;
        private String tradePassword;
        private String vipTime;
        private String stageExtraFee;
        private String title;
        private String content;
        private String url;
        private String ruleUrl;
        private List<?> copyWriting;

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getBizLicenseFilePrefix() {
            return bizLicenseFilePrefix;
        }

        public void setBizLicenseFilePrefix(String bizLicenseFilePrefix) {
            this.bizLicenseFilePrefix = bizLicenseFilePrefix;
        }

        public String getBizLicenseDirectoryName() {
            return bizLicenseDirectoryName;
        }

        public void setBizLicenseDirectoryName(String bizLicenseDirectoryName) {
            this.bizLicenseDirectoryName = bizLicenseDirectoryName;
        }

        public String getBizPlaceSnapshotFilePrefix() {
            return bizPlaceSnapshotFilePrefix;
        }

        public void setBizPlaceSnapshotFilePrefix(String bizPlaceSnapshotFilePrefix) {
            this.bizPlaceSnapshotFilePrefix = bizPlaceSnapshotFilePrefix;
        }

        public String getBizPlaceSnapshotDirectoryName() {
            return bizPlaceSnapshotDirectoryName;
        }

        public void setBizPlaceSnapshotDirectoryName(String bizPlaceSnapshotDirectoryName) {
            this.bizPlaceSnapshotDirectoryName = bizPlaceSnapshotDirectoryName;
        }

        public String getBizPlaceSnapshot1FilePrefix() {
            return bizPlaceSnapshot1FilePrefix;
        }

        public void setBizPlaceSnapshot1FilePrefix(String bizPlaceSnapshot1FilePrefix) {
            this.bizPlaceSnapshot1FilePrefix = bizPlaceSnapshot1FilePrefix;
        }

        public String getBizPlaceSnapshot1DirectoryName() {
            return bizPlaceSnapshot1DirectoryName;
        }

        public void setBizPlaceSnapshot1DirectoryName(String bizPlaceSnapshot1DirectoryName) {
            this.bizPlaceSnapshot1DirectoryName = bizPlaceSnapshot1DirectoryName;
        }

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

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public int getParticipantId() {
            return participantId;
        }

        public void setParticipantId(int participantId) {
            this.participantId = participantId;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getRefId() {
            return refId;
        }

        public void setRefId(int refId) {
            this.refId = refId;
        }

        public String getRefName() {
            return refName;
        }

        public void setRefName(String refName) {
            this.refName = refName;
        }

        public String getRefCellPhone() {
            return refCellPhone;
        }

        public void setRefCellPhone(String refCellPhone) {
            this.refCellPhone = refCellPhone;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public int getYDRemNum() {
            return YDRemNum;
        }

        public void setYDRemNum(int YDRemNum) {
            this.YDRemNum = YDRemNum;
        }

        public int getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(int agencyId) {
            this.agencyId = agencyId;
        }

        public String getQualifiedState() {
            return qualifiedState;
        }

        public void setQualifiedState(String qualifiedState) {
            this.qualifiedState = qualifiedState;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getDepositBank() {
            return depositBank;
        }

        public void setDepositBank(String depositBank) {
            this.depositBank = depositBank;
        }

        public double getEnchFee() {
            return enchFee;
        }

        public void setEnchFee(double enchFee) {
            this.enchFee = enchFee;
        }

        public String getHolderName() {
            return holderName;
        }

        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getCanReceived() {
            return canReceived;
        }

        public void setCanReceived(String canReceived) {
            this.canReceived = canReceived;
        }

        public String getPayCode() {
            return payCode;
        }

        public void setPayCode(String payCode) {
            this.payCode = payCode;
        }

        public String getGesturePassword() {
            return gesturePassword;
        }

        public void setGesturePassword(String gesturePassword) {
            this.gesturePassword = gesturePassword;
        }

        public String getTradePassword() {
            return tradePassword;
        }

        public void setTradePassword(String tradePassword) {
            this.tradePassword = tradePassword;
        }

        public String getVipTime() {
            return vipTime;
        }

        public void setVipTime(String vipTime) {
            this.vipTime = vipTime;
        }

        public String getStageExtraFee() {
            return stageExtraFee;
        }

        public void setStageExtraFee(String stageExtraFee) {
            this.stageExtraFee = stageExtraFee;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getRuleUrl() {
            return ruleUrl;
        }

        public void setRuleUrl(String ruleUrl) {
            this.ruleUrl = ruleUrl;
        }

        public List<?> getCopyWriting() {
            return copyWriting;
        }

        public void setCopyWriting(List<?> copyWriting) {
            this.copyWriting = copyWriting;
        }
    }
}
