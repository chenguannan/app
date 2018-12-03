package com.sl_group.jinyuntong_oem.gather.model;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface GatherModel {
    void getPayCodeMoney(String money,GatherModel.IPayCodeMoneyCallBack payCodeMoneyCallBack);
    interface  IPayCodeMoneyCallBack{
        void onSuccess(String data);
    }
}
