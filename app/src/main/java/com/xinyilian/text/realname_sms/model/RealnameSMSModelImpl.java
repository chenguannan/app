package com.xinyilian.text.realname_sms.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.CommonParamsUtils;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.ParamsFormatUtils;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

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
