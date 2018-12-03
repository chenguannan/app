package com.sl_group.jinyuntong_oem.youdun_uuid.model;

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
public class YouDunUUIDModelImpl implements YouDunUUIDModel {
    private Activity mActivity;

    public YouDunUUIDModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void getYouDunUUID(final IYouDunUUIDCallBack youDunUUIDCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.GET_YOUDUN_UUID);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId", SPUtil.get(mActivity, "agencyId", CommonSet.AGENCY_ID));

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.GET_YOUDUN_UUID,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        youDunUUIDCallBack.onSuccess(paseData);
                    }

                });
    }
}
