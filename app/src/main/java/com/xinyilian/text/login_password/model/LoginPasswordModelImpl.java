package com.xinyilian.text.login_password.model;

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
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class LoginPasswordModelImpl implements LoginPasswordModel {
    private Activity mActivity;

    public LoginPasswordModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public void loginPassword(String cellPhone, String checkCode, String uuid, String password, final ILoginPasswordCallBack forgetLoginPasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.CHANGE_LOGIN_PASSWORD);
        obj.put("cellPhone", cellPhone);
        obj.put("checkCode", checkCode);
        obj.put("uuid", uuid);
        obj.put("password", password);
        obj.put("encryptId", CommonSet.ENCRYPT_ID);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.CHANGE_LOGIN_PASSWORD,
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
