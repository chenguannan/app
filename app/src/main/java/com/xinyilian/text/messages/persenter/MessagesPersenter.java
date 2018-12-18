package com.xinyilian.text.messages.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.MessagesBean;
import com.xinyilian.text.messages.model.MessagesModel;
import com.xinyilian.text.messages.model.MessagesModelImpl;
import com.xinyilian.text.messages.view.MessagesView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class MessagesPersenter {
    private Activity mActivity;
    private MessagesView mMessagesView;
    private MessagesModelImpl mNewsModel;

    public MessagesPersenter(Activity activity, MessagesView messagesView) {
        mActivity = activity;
        mMessagesView = messagesView;
        mNewsModel = new MessagesModelImpl(activity);
    }

    public void messages(final String type, boolean isShowProgress, int curPage, String pageSize, String isReady){
        mNewsModel.messages(type,isShowProgress, curPage, pageSize, isReady, new MessagesModel.IMessagesCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("信息列表："+data);
                MessagesBean messagesBean = new Gson().fromJson(data,MessagesBean.class);
                if ("000000".equals(messagesBean.getCode())){
                    List<MessagesBean.DataBean.ResultListBean> newsListBeans = new ArrayList<>();
                    List<MessagesBean.DataBean.ResultListBean> noticeListBeans = new ArrayList<>();
                    for (int i = 0; i < messagesBean.getData().getResultList().size() ; i++) {
                        MessagesBean.DataBean.ResultListBean resultListBean = messagesBean.getData().getResultList().get(i);
                        if (resultListBean.getMessageId()!=0){
                            newsListBeans.add(resultListBean);
                        }
                        if (resultListBean.getNoticeId()!=0){
                            noticeListBeans.add(resultListBean);
                        }
                    }
                    switch (type){
                        case "news":
                            mMessagesView.newsSuccess(newsListBeans);
                            break;
                        case "notices":
                            mMessagesView.noticesSuccess(noticeListBeans);
                            break;
                    }

                    return;
                }else if ("888888".equals(messagesBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(messagesBean.getMessage());
            }
        });
    }

}
