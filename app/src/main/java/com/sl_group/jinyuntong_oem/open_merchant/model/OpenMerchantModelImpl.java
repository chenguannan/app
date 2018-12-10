package com.sl_group.jinyuntong_oem.open_merchant.model;

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
 * Created by 马天 on 2018/11/17.
 * description：开通商户权限
 */
public class OpenMerchantModelImpl implements OpenMerchantMedel {
    private Activity mActivity;

    public OpenMerchantModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void openMerchant(String businessUUID, String docUUID, String businessPicUUID, String shopname, String shopaddress, String accountNumber, String tel, final IOpenMerchantCallBack openMerchantCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.OPEN_MERCHANT);
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("shortName", shopname);
        obj.put("businessLicense", businessPicUUID);
        obj.put("doorheadPhoto", docUUID);
        obj.put("placeOfBusiness", businessUUID);
        obj.put("shopAddress", shopaddress);
        obj.put("accountNumber", accountNumber);
        obj.put("tel", tel);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.OPEN_MERCHANT,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        openMerchantCallBack.onSuccess(paseData);

                    }

                });
    }


}
