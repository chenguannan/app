package com.xinyilian.text.unbind_creditcard.model;

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
 * Created by 马天 on 2018/11/29.
 * description：
 */
public class UnBindCreditCardModelImpl implements UnBindCredirtCardModel {
    private Activity mActivity;

    public UnBindCreditCardModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void unBindCreditCard(String fastpayBankAccountInfoId, final IUnBindCreditCardCallBack unBindCreditCardCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.UNBIND_CREDIT_CARD);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("fastpayBankAccountInfoId", fastpayBankAccountInfoId);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.UNBIND_CREDIT_CARD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        unBindCreditCardCallBack.onSuccess(paseData);
                    }

                });
    }
}
