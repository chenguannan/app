package com.sl_group.jinyuntong_oem.sms.model;

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
 * description：
 */
public class SMSModelImpl implements SMSModel {
    private Activity mActivity;

    public SMSModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean checkParams(String tel) {
        if (StringUtils.isEmpty(tel)){
            ToastUtils.showToast("请输入您的手机号");
            return false;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的手机号");
            return false;
        }
        return true;
    }

    @Override
    public void getSMS(String tel, final IChangeGesturePasswordSMSCallBack changeGesturePasswordSMSCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.COMMON_SMS);
        obj.put("cellPhone", tel);
        obj.put("encryptId", "merchantApp");
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.COMMON_SMS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        changeGesturePasswordSMSCallBack.onSuccess(paseData);

                    }

                });
    }
}
