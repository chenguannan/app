package com.sl_group.jinyuntong_oem.register.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.URLConstants;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.IsNumberUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public class RegisterModelImpl implements RegisterModel {
    private Activity mActivity;

    public RegisterModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean checkSMSParams(String inviteCode,String tel) {
        if (StringUtils.isEmpty(inviteCode)) {
            ToastUtils.showToast("请输入邀请码");
            return false;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkRegisterParams(String inviteCode, String tel, String vificationCode, String password, String passwordAgain, boolean isSelected) {


        if (StringUtils.isEmpty(inviteCode)) {
            ToastUtils.showToast("请输入邀请码");
            return false;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的手机号码");
            return false;
        }
        if (StringUtils.isEmpty(vificationCode)) {
            ToastUtils.showToast("请输入短信验证码");
            return false;
        }
        if (StringUtils.isEmpty(password)||password.length()<8) {
            ToastUtils.showToast("请输入8-14位数字和字母组合的密码");
            return false;
        }
        if (!IsNumberUtils.isLetterDigit(password)){
            ToastUtils.showToast("请输入数字和字母组合的密码");
            return false;
        }

        if (StringUtils.isEmpty(passwordAgain)||passwordAgain.length()<8) {
            ToastUtils.showToast("请再次输入登录密码");
            return false;
        }

        if (!password.equals(passwordAgain)) {
            ToastUtils.showToast("两次密码输入不一致");
            return false;
        }

        return true;
    }

    @Override
    public void getSMS(String inviteCode,String tel, final IRegisterGetSMSCallBack registerGetSMSCallBack) {
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
                        registerGetSMSCallBack.onSuccess(paseData);
                    }

                });
    }

    @Override
    public void register(String inviteCode, String tel, String password, final RegisterModel.IRegisterCallBack registerCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REGISTER);
        obj.put("cellPhone", tel);
        obj.put("agencyId", CommonSet.AGENCY_ID);

        obj.put("password", password);
        obj.put("inviteCode", inviteCode);
        obj.put("encryptId", "merchantApp");

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.REGISTER,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        registerCallBack.onSuccess(paseData);
                    }

                });
    }
}
