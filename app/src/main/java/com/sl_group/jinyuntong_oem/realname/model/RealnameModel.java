package com.sl_group.jinyuntong_oem.realname.model;

/**
 * Created by 马天 on 2018/11/16.
 * description：
 */
public interface RealnameModel {


    /**
     * 检查参数
     *
     * @param idcard                   身份证号码
     * @param holderName               持卡人姓名
     * @param accountNumber            银行卡号
     * @param tel                      银行预留手机号
     * @param bizPlaceSnapshot1ImageId 手持身份证正面
     * @param bizPlaceSnapshot2ImageId 手持银行卡正面
     * @return boolean
     */
    boolean checkRealNameParams(String idcard, String holderName, String accountNumber, String tel, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId);



    /**
     * 实名认证
     * @param iRealnameCallBack 实名回调接口
     */
    void realname(String idcard, String holderName, String accountNumber, String tel, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId, RealnameModel.IRealnameCallBack iRealnameCallBack);

    /**
     * 实名获取验证码回调接口及回调方法
     */
    interface IRealnameCallBack {
        void onSuccess(String data);
    }


    /**
     * 实名回调接口及回调方法
     */
    interface IRealnameGetSMSCallBack {
        void onSuccess(String data);
    }
}
