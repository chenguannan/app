package com.sl_group.jinyuntong_oem.analyze_qrcode.model;

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
public class AnalyzeQrcodeModelImpl implements AnalyzeQrcodeModel {
    private Activity mActivity;

    public AnalyzeQrcodeModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public void analyzeQrcode(String qrcode, final IAnalyzeQrcodeCallBack analyzeQrcodeCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.QUERY_QRCODE);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId",SPUtil.get(mActivity, "agencyId",CommonSet.AGENCY_ID));
        obj.put("uuid",qrcode);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.QUERY_QRCODE,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        analyzeQrcodeCallBack.onSuccess(paseData);
                    }

                });
    }
}