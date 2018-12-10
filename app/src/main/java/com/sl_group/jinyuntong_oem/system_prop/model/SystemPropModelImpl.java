package com.sl_group.jinyuntong_oem.system_prop.model;

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
 * Created by 马天 on 2018/11/26.
 * description：
 */
public class SystemPropModelImpl implements SystemPropModel {
    private Activity mActivity;

    public SystemPropModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void systemProp( String type,final ISystemPropCallBack systemPropCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.SYSTEMPROP);
        obj.put("agencyId", CommonSet.AGENCY_ID);
        obj.put("encryptId",CommonSet.ENCRYPT_ID);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.SYSTEMPROP,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, CommonSet.KEY))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        systemPropCallBack.onSuccess(paseData);
                    }

                });
    }
}
