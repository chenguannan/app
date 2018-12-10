package com.sl_group.jinyuntong_oem.message_details.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.MessageDetailsBean;
import com.sl_group.jinyuntong_oem.message_details.model.MessageDetailsModel;
import com.sl_group.jinyuntong_oem.message_details.model.MessageDetailsModelImpl;
import com.sl_group.jinyuntong_oem.message_details.view.MessageDetailsView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class MessageDetailsPersenter {
    private Activity mActivity;
    private MessageDetailsView mMessageDetailsView;
    private MessageDetailsModelImpl mNewDetailsModel;

    public MessageDetailsPersenter(Activity activity, MessageDetailsView messageDetailsView) {
        mActivity = activity;
        mMessageDetailsView = messageDetailsView;
        mNewDetailsModel = new MessageDetailsModelImpl(activity);
    }

    public void newDetails(String messageId, String isReady, String noticeId){
        mNewDetailsModel.messageDetails(messageId, isReady,noticeId, new MessageDetailsModel.IMessageDetailsCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("信息详情："+data);
                MessageDetailsBean messageDetailsBean = new Gson().fromJson(data,MessageDetailsBean.class);
                if ("000000".equals(messageDetailsBean.getCode())){
                    mMessageDetailsView.messageDetailsSuccess(messageDetailsBean.getData());
                    return;
                }else if ("888888".equals(messageDetailsBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(messageDetailsBean.getMessage());
            }
        });
    }
}
