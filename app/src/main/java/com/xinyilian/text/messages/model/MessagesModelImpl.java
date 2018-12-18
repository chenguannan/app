package com.xinyilian.text.messages.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.CommonParamsUtils;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.ParamsFormatUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

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
