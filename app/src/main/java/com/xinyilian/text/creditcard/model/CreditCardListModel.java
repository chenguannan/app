package com.xinyilian.text.creditcard.model;

/**
 * Created by 马天 on 2018/11/19.
 * description：信用卡列表接口
 */
public interface CreditCardListModel {

    /**
      *
      * @param isShowProgress 是否允许弹窗进度条
      * @param creditCardListCallBack 回调
      * 查询信用卡列表
      */
    void CreditCardList(boolean isShowProgress, ICreditCardListCallBack creditCardListCallBack);

    /**
     * 银行卡列表回调接口及回调方法
     */
    interface ICreditCardListCallBack {
        void onSuccess(String data);
    }
}
