package com.sl_group.jinyuntong_oem.vip.model;

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
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class VipCenterModelImpl implements VipCenterModel {
    private Activity mActivity;

    public VipCenterModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void queryBankFee(final IQueryBankFeeCallBack queryBankFeeCallBack) {
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
                        queryBankFeeCallBack.onSuccess(paseData);
                    }

                });
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
