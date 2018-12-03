package com.sl_group.jinyuntong_oem.youdun_uuid.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface YouDunUUIDModel {

    void getYouDunUUID(YouDunUUIDModel.IYouDunUUIDCallBack youDunUUIDCallBack);

    interface IYouDunUUIDCallBack{
        void onSuccess(String data);
    }
}
