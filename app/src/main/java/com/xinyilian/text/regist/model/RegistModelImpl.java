package com.xinyilian.text.regist.model;

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
public class RegistModelImpl implements RegistModel {
    private Activity mActivity;

    public RegistModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void register(String inviteCode, String tel,String checkCode ,String uuid , String password, final RegistModel.IRegisterCallBack registerCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REGISTER);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("encryptId", CommonSet.ENCRYPT_ID);
        obj.put("cellPhone", tel);
        obj.put("password", password);
        obj.put("inviteCode", inviteCode);
        obj.put("checkCode", checkCode);
        obj.put("uuid", uuid);

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
