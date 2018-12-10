package com.sl_group.jinyuntong_oem.messages.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.URLConstants;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class MessagesModelImpl implements MessagesModel {
    private Activity mActivity;

    public MessagesModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void messages(String type,boolean isShowProgress, int curPage, String pageSize, String isReady, final IMessagesCallBack messagesCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.MESSAGES);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId",SPUtil.get(mActivity, "agencyId", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("curPage", curPage);
        obj.put("pageSize", pageSize);
        obj.put("IsReady",isReady);
        HttpUtils.getInstance().postJson(
                mActivity,
                isShowProgress,
                CommonSet.DOMAIN_URL + URLConstants.MESSAGES,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        messagesCallBack.onSuccess(paseData);
                    }

                });
    }
}
