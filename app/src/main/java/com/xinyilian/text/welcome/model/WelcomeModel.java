package com.xinyilian.text.welcome.model;

/**
 * Created by 马天 on 2018/11/19.
 * description：
 */
public interface WelcomeModel {

    void merchantInfo(WelcomeModel.IMerchantInfoCallBack merchantInfoCallBack);

    interface IMerchantInfoCallBack{
        void onSuccess(String data);
    }
}
