package com.sl_group.jinyuntong_oem;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface URLConstants {
    //登录
    String LOGIN = "loginByPersonalMerchant";
    //注册
    String REGISTER = "personalMerchantRegister";
    //注册短信
    String REGISTER_SMS = "sendCheckCodeByRegister";
    //登录短信
    String LOGIN_SMS = "sendCheckCodeByLogin";
    //绑卡
    String BIND_BANKCARD = "setPayBankAccountInfo";
    //查询个人信息
    String MERCHANT_INFO = "searchPersonalMerchant";
    //通用发短信
    String COMMON_SMS = "personalForgetPasswordCheckcode";
    //通用发短信
    String REALNAME_SMS = "personalForgetPasswordCheckcodeNotChekPhone";
    //实名
    String REALNAME = "realNameAuthentication";
    //上传图片
    String UPLOAD_IMG = "uploadImage";
    //修改登录密码
    String CHANGE_LOGIN_PASSWORD = "modifyPassword";
    //开通商户权限
    String OPEN_MERCHANT = "openMerchantRight";
    //设置手势密码
    String SET_GESTURE_PASSWORD = "initGesturePassword";
    //修改手势密码
    String CHANGE_GESTURE_PASSWORD = "modifyGesturePassword";
    //查询信用卡列表
    String QUERY_BANKCARD = "creditCardList";
    //查询信用卡列表
    String SET_SETTLE_CARD = "setBankAccountInfo";
    //收款账单
    String GATHER_BILL = "getReceivelistRecords";
    //付款账单
    String PAY_BILL = "getPaylistRecords";
    //付款码带金额
    String PAY_QRCODE_MONEY = "getQrCodeBySrcAmt";
    //查询收款码
    String QUERY_QRCODE = "queryQrCode";
    //银行费率
    String BANK_FEE = "getVipBankFee";
    //购买VIP
    String BUY_VIP = "promoteLevelByFastPay";
    //下单
    String PLACE_ORDER = "fastpayQrcodePrecreate";
    //设置支付密码
    String SET_PAY_PASSWORD = "initTradePassword";
    //修改支付密码
    String  CHANGE_PAY_PASSWORD = "modifyTradePassword";
    //获取有盾UUID
    String  GET_YOUDUN_UUID = "youDunResultUUid";
    //设置头像
    String  SET_HEAD_PHOTO = "setHeadPhoto";
    //财富
    String TREASURE = "searchRevenue";
    //申请提现
    String APPLY_EXTRACT = "enchashment";
    //  佣金列表
    String BROKERAGE = "searchEachKindRevenue";
    //  佣金详情
    String BROKERAGE_DETSILS = "searchRevenueDetail";
    //  消息列表
    String MESSAGES = "searchMessageList";
    //  消息详情
    String MESSAGE_DETAILS = "searchMessageDetail";
    //  我推荐的人查询
    String MY_TEAM = "searchSubordinates";
    //  各种连接
    String SYSTEMPROP = "getSystemProp";
    //  解绑信用卡
    String UNBIND_CREDIT_CARD = "deleteCreditCard";
}
