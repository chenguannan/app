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
 * description：
 */
public class OpenMerchantModelImpl implements OpenMerchantMedel {
    private Activity mActivity;

    public OpenMerchantModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean checkOpenMerchantParams(String businessUUID, String docUUID, String businessPicUUID, String shopname, String shopaddress, String accountNumber, String tel) {
        if (StringUtils.isEmpty(businessUUID)) {
            ToastUtils.showToast("请上传营业场所照");
            return false;
        }
        if (StringUtils.isEmpty(docUUID)) {
            ToastUtils.showToast("请上传门头照");
            return false;
        }
        if (StringUtils.isEmpty(businessPicUUID)) {
            ToastUtils.showToast("请上传营业执照");
            return false;
        }
        if (StringUtils.isEmpty(shopname)) {
            ToastUtils.showToast("请输入店铺名称");
            return false;
        }
        if (shopaddress.contains("选择")) {
            ToastUtils.showToast("请选择店铺地址：省/市/区");
            return false;
        }
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入储蓄卡号");
            return false;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return false;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return false;
        }

        return true;
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
