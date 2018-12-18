package com.xinyilian.text.buy_vip.model;

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
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class BuyVipModelImpl implements BuyVipModel {
    private Activity mActivity;

    public BuyVipModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void buyVip(String accountNumber, final IBuyVipCallBack buyVipCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.BUY_VIP);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("payedMid", SPUtil.get(mActivity, "mid", ""));
        obj.put("accountNumber", accountNumber);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.BUY_VIP,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        buyVipCallBack.onSuccess(paseData);
                    }

                });
    }
}
