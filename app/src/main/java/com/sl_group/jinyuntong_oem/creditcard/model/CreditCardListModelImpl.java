package com.sl_group.jinyuntong_oem.creditcard.model;

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
