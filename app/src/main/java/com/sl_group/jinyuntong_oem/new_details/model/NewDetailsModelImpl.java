package com.sl_group.jinyuntong_oem.new_details.model;

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
public class NewDetailsModelImpl implements NewDetailsModel {
    private Activity mActivity;

    public NewDetailsModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public void newDetails(String messageId, String isReady, final INewDetailsCallBack newDetailsCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.NEWS_DETAILS);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("messageId", messageId);
        obj.put("isReady", isReady);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.NEWS_DETAILS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        newDetailsCallBack.onSuccess(paseData);
                    }

                });
    }
}
