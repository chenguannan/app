package com.sl_group.jinyuntong_oem.login.login_password.model;

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
public class LoginPasswordModelImpl implements LoginPasswordModel {
    private Activity mActivity;

    public LoginPasswordModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public boolean checkParams(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showToast("请输入密码");
            return false;
        }
        if (password.length()<8) {
            ToastUtils.showToast("请输入8-14位密码");
            return false;
        }
        return true;
    }

    @Override
    public void login(String username, String password, final ILoginCallBack loginCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.LOGIN);
        obj.put("loginName", username);
        obj.put("agencyId", CommonSet.AGENCY_ID);

        obj.put("password", password);
        obj.put("loginType", "UsePwd");

        obj.put("encryptId", CommonSet.ENCRYPT_ID);

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
