package com.xinyilian.text.treasure.model;

/**
 * Created by 马天 on 2018/11/23.
 * description：
 */
public interface TreasureModel {

    void treasure(ITreasureCallBack iTreasureCallBack);

    interface ITreasureCallBack {
        void onSuccess(String data);
    }
}
