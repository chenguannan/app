package com.sl_group.jinyuntong_oem.safe_set.login_password.model;

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
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class ChangeLoginPasswordModelImpl implements ChangeLoginPasswordModel {
    private Activity mActivity;

    public ChangeLoginPasswordModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean checkForgetLoginPasswordParams(String tel, String password, String passwordAgain) {
        if (StringUtils.isEmpty(tel)){
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的手机号码");
            return false;
        }
        if (StringUtils.isEmpty(password)){
            ToastUtils.showToast("请输入8-14位数字和字母组合的新密码");
            return false;
        }
        if (!IsNumberUtils.isLetterDigit(password)){
            ToastUtils.showToast("请输入数字和字母组合的密码");
            return false;
        }

        if (StringUtils.isEmpty(passwordAgain)){
            ToastUtils.showToast("请再次输入密码");
            return false;
        }
        if (!password.equals(passwordAgain)){
            ToastUtils.showToast("两次密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    public void forgetLoginPassword(String tel, String password, final IForgetLoginPasswordCallBack forgetLoginPasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.CHANGE_LOGIN_PASSWORD);
        obj.put("cellPhone", tel);
        obj.put("password", password);
        obj.put("encryptId", "merchantApp");
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.CHANGE_LOGIN_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        forgetLoginPasswordCallBack.onSuccess(paseData);

                    }

                });
    }
}
