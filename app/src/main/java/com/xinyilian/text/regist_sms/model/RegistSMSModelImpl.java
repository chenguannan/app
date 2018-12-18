package com.xinyilian.text.regist_sms.model;

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
 * Created by 马天 on 2018/11/10.
 * description：
 */
public class RegistSMSModelImpl implements RegistSMSModel {
    private Activity mActivity;

    public RegistSMSModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void registSMS(String inviteCode, String tel, final IRegistSMSCallBack registSMSCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REGISTER_SMS);
        obj.put("cellPhone", tel);
        obj.put("inviteCode", inviteCode);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("encryptId","merchantApp");
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.REGISTER_SMS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        registSMSCallBack.onSuccess(paseData);
                    }

                });
    }


}
