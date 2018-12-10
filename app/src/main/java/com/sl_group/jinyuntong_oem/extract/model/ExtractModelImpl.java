package com.sl_group.jinyuntong_oem.extract.model;

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
 * Created by 马天 on 2018/11/24.
 * description：
 */
public class ExtractModelImpl implements ExtractModel {
    private Activity mActivity;

    public ExtractModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void extract(String extractMoney, String holderName, String accountNumber, final IExtractCallBack extractCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.APPLY_EXTRACT);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("accountNumber", accountNumber);
        obj.put("holderName", holderName);
        obj.put("srcAmt", extractMoney);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.APPLY_EXTRACT,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        extractCallBack.onSuccess(paseData);
                    }

                });
    }
}
