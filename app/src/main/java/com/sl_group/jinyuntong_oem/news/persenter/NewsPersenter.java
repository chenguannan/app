package com.sl_group.jinyuntong_oem.news.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.NewsBean;
import com.sl_group.jinyuntong_oem.news.model.NewsModel;
import com.sl_group.jinyuntong_oem.news.model.NewsModelImpl;
import com.sl_group.jinyuntong_oem.news.view.NewsView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class NewsPersenter {
    private Activity mActivity;
    private NewsView mNewsView;
    private NewsModelImpl mNewsModel;

    public NewsPersenter(Activity activity, NewsView newsView) {
        mActivity = activity;
        mNewsView = newsView;
        mNewsModel = new NewsModelImpl(activity);
    }

    public void news(boolean isShowProgress, int curPage, String pageSize, String isReady){
        mNewsModel.news(isShowProgress, curPage, pageSize, isReady, new NewsModel.INewsCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("消息："+data);
                NewsBean newsBean = new Gson().fromJson(data,NewsBean.class);
                if ("000000".equals(newsBean.getCode())){
                    mNewsView.getNews(newsBean.getData().getResultList());
                    return;
                }else if ("888888".equals(newsBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(newsBean.getMessage());
            }
        });
    }

}
