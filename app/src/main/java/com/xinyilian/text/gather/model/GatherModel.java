package com.xinyilian.text.gather.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：二维码收款
 */
public interface GatherModel {

    /**
      *
      * @param gatherMoney 收款金额
      * @param gatherWithMoneyCallBack 设置收款金额回调
      */
    void gatherWithMoney(String gatherMoney, IGatherWithMoneyCallBack gatherWithMoneyCallBack);

    interface IGatherWithMoneyCallBack {
        void onSuccess(String data);
    }
}
