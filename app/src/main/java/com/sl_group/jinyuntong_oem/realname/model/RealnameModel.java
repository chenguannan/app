package com.sl_group.jinyuntong_oem.realname.model;

/**
 * Created by 马天 on 2018/11/16.
 * description：
 */
public interface RealnameModel {

    /**
     * 实名认证
     *
     * @param idcard                   身份证号
     * @param holderName               真实姓名
     * @param accountNumber            结算卡号
     * @param tel                      预留手机号
     * @param checkCode                验证码
     * @param uuid                     验证码UUID
     * @param bizPlaceSnapshot1ImageId 手持身份证照片UUID
     * @param bizPlaceSnapshot2ImageId 银行卡正面照UUID
     * @param iRealnameCallBack        实名回调接口
     */
    void realname(String idcard, String holderName, String accountNumber, String tel, String checkCode, String uuid,
                  String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId, RealnameModel.IRealnameCallBack iRealnameCallBack);

    /**
     * 实名获取验证码回调接口及回调方法
     */
    interface IRealnameCallBack {
        void onSuccess(String data);
    }

}
