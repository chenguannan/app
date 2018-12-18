package com.xinyilian.text.login.model;

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
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginModelImpl implements LoginModel {
    private Activity mActivity;

    public LoginModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void getLoginSMS(String tel, final ILoginSMSCallBack loginSMSCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.LOGIN_SMS);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("encryptId", CommonSet.ENCRYPT_ID);
        obj.put("cellPhone", tel);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.LOGIN_SMS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        loginSMSCallBack.onSuccess(paseData);

                    }

                });

    }

    @Override
    public void login(String loginType, String tel, String password, String smsCode, String uuid, final ILoginCallBack loginCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.LOGIN);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("loginName", tel);
        obj.put("encryptId", CommonSet.ENCRYPT_ID);
        obj.put("loginType", loginType);
        if (StringUtils.isEmpty(loginType)) {
            obj.put("checkCode", smsCode);
            obj.put("uuid", uuid);
        } else {
            obj.put("password", password);
        }
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.LOGIN,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        loginCallBack.onSuccess(paseData);
                    }

                });
    }
}
