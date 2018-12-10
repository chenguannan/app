package com.sl_group.jinyuntong_oem.settle_info.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface SettleInfoModel {
    boolean checkParams(String accountNumber,String tel);

    void setSettleCard(String accountNumber, String tel,SettleInfoModel.ISetSettleCardCallBack setSettleCardCallBack);

    interface ISetSettleCardCallBack {
        void onSuccess(String data);
    }
}
