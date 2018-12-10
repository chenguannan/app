package com.sl_group.jinyuntong_oem.realname_sms.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.URLConstants;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：实名认证短信
 */
public class RealnameSMSModelImpl implements RealnameSMSModel {
    private Activity mActivity;

    public RealnameSMSModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void realnameSMS(String tel, final IRealnameSMSCallBack realnameSMSCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REALNAME_SMS);
        obj.put("encryptId", CommonSet.ENCRYPT_ID);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("cellPhone", tel);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.REALNAME_SMS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        realnameSMSCallBack.onSuccess(paseData);

                    }

                });
    }


}
