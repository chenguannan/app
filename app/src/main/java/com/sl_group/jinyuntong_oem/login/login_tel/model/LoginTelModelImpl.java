package com.sl_group.jinyuntong_oem.login.login_tel.model;

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
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginTelModelImpl implements LoginTelModel {
    private Activity mActivity;

    public LoginTelModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public boolean checkGetSMSParams(String tel) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (tel.length()!=11) {
            ToastUtils.showToast("请输入正确手机号码");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkLoginParams(String tel, String smsCode) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (tel.length()!=11) {
            ToastUtils.showToast("请输入正确手机号码");
            return false;
        }
        if (StringUtils.isEmpty(smsCode)) {
            ToastUtils.showToast("请输入验证码");
            return false;
        }
        if (smsCode.length()!=6) {
            ToastUtils.showToast("请输入验证码");
            return false;
        }
        return true;
    }

    @Override
    public void getLoginSMS(String tel, final ILoginSMSCallBack loginSMSCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.LOGIN_SMS);
        obj.put("cellPhone", tel);
        obj.put("agencyId", CommonSet.AGENCY_ID);

        obj.put("encryptId", "merchantApp");
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.LOGIN_SMS,
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
    public void login(String tel, String smsCode,String uuid, final ILoginCallBack loginCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.LOGIN);
        obj.put("loginName", tel);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("checkCode", smsCode);
        obj.put("uuid",uuid);
        obj.put("loginType","");

        obj.put("encryptId", "merchantApp");

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.LOGIN,
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
