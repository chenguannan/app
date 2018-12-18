package com.xinyilian.text.welcome.model;

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
 * Created by 马天 on 2018/11/19.
 * description：
 */
public class WelcomeModelImpl implements WelcomeModel {
    private Activity mActivity;

    public WelcomeModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void merchantInfo(final WelcomeModel.IMerchantInfoCallBack merchantInfoCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.MERCHANT_INFO);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));

        HttpUtils.getInstance().postJson(
                mActivity,
                false,
                CommonSet.DOMAIN_URL + URLConstants.MERCHANT_INFO,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        merchantInfoCallBack.onSuccess(paseData);
                    }

                });
    }
}
