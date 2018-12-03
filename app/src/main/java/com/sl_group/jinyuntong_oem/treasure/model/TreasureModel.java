package com.sl_group.jinyuntong_oem.treasure.model;

/**
 * Created by 马天 on 2018/11/23.
 * description：
 */
public interface TreasureModel {

    void queryTreasure(TreasureModel.IQueryTreasureCallBack iQueryTreasureCallBack);

    interface IQueryTreasureCallBack{
        void onSuccess(String data);
    }
}
