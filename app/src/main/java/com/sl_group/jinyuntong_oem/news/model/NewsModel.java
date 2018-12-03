package com.sl_group.jinyuntong_oem.news.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface NewsModel {

    void news(boolean isShowProgress,int curPage,String pageSize,String isReady,NewsModel.INewsCallBack newsCallBack);

    interface INewsCallBack{
        void onSuccess(String data);
    }
}
