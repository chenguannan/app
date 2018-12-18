package com.xinyilian.text.message_details.model;

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
public class MessageDetailsModelImpl implements MessageDetailsModel {
    private Activity mActivity;

    public MessageDetailsModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public void messageDetails(String messageId,String isReady, String noticeId, final IMessageDetailsCallBack messageDetailsCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.MESSAGE_DETAILS);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId", SPUtil.get(mActivity, "agencyId", ""));
        obj.put("noticeId", noticeId);
        obj.put("messageId", messageId);
        obj.put("isReady", isReady);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.MESSAGE_DETAILS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        messageDetailsCallBack.onSuccess(paseData);
                    }

                });
    }
}
