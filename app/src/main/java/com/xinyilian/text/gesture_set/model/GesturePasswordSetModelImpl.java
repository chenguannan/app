package com.xinyilian.text.gesture_set.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.CommonParamsUtils;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.ParamsFormatUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：设置手势密码
 */
public class GesturePasswordSetModelImpl implements GesturePasswordSetModel {

    private Activity mActivity;

    public GesturePasswordSetModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void gesturePasswordSet(String cellPhone, String checkCode, String uuid, String gesturePassword, final IGesturePasswordSetCallBack iGesturePasswordSetCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.SET_GESTURE_PASSWORD);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("cellPhone",cellPhone);
        obj.put("checkCode",checkCode);
        obj.put("uuid",uuid);
        obj.put("gesturePassword",gesturePassword);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.SET_GESTURE_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        iGesturePasswordSetCallBack.onSuccess(paseData);
                    }

                });
    }
}
