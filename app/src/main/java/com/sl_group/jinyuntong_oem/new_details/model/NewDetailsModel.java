package com.sl_group.jinyuntong_oem.new_details.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface NewDetailsModel {
    void newDetails(String messageId,String isReady,NewDetailsModel.INewDetailsCallBack newDetailsCallBack);
    interface INewDetailsCallBack{
        void onSuccess(String data);
    }
}
