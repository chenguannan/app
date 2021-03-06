package com.xinyilian.text.bank_fee.model;

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
 * Created by 马天 on 2018/12/10.
 * description：
 */
public class BankFeeModelImpl implements BankFeeModel {
    private Activity mActivity;

    public BankFeeModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void bankFee(final IBankFeeCallBack iBankFeeCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.BANK_FEE);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId", SPUtil.get(mActivity, "agencyId",CommonSet.AGENCY_ID));

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.BANK_FEE,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        iBankFeeCallBack.onSuccess(paseData);
                    }

                });
    }
}
