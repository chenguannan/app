package com.xinyilian.text.creditcard.model;

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
 * Created by 马天 on 2018/11/15.
 * description：信用卡列表数据源
 */
public class CreditCardListModelImpl implements CreditCardListModel {
    private Activity mActivity;
    public CreditCardListModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void CreditCardList(boolean isShowProgress, final ICreditCardListCallBack creditCardListCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.QUERY_BANKCARD);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));

        HttpUtils.getInstance().postJson(
                mActivity,
                isShowProgress,
                CommonSet.DOMAIN_URL + URLConstants.QUERY_BANKCARD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        creditCardListCallBack.onSuccess(paseData);
                    }

                });
    }
}
