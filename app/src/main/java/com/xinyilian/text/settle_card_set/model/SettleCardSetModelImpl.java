package com.xinyilian.text.settle_card_set.model;

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
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class SettleCardSetModelImpl implements SettleCardSetModel {
    private Activity mActivity;

    public SettleCardSetModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void settleCardSet(String accountNumber, String tel, final ISettleCardSetCallBack iSettleCardSetCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.SET_SETTLE_CARD);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("accountNumber", accountNumber);
        obj.put("tel", tel);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.SET_SETTLE_CARD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        iSettleCardSetCallBack.onSuccess(paseData);
                    }

                });
    }
}
