package com.sl_group.jinyuntong_oem.pay_password_change.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.URLConstants;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class PayPasswordChangeModelImpl implements PayPasswordChangeModel {
    private Activity mActivity;

    public PayPasswordChangeModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void changePayPassword(String cellPhone,String checkCode,String uuid,String tradePassword, final IChangePayPasswordCallBack changePayPasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.CHANGE_PAY_PASSWORD);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("cellPhone",cellPhone);
        obj.put("checkCode",checkCode);
        obj.put("uuid",uuid);
        obj.put("tradePassword",tradePassword);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.CHANGE_PAY_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        changePayPasswordCallBack.onSuccess(paseData);
                    }

                });
    }
}