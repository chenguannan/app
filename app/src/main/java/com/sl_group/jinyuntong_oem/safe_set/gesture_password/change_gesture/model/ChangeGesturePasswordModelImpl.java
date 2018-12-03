package com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.model;

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
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class ChangeGesturePasswordModelImpl implements ChangeGesturePasswordModel {

    private Activity mActivity;

    public ChangeGesturePasswordModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void changeGesturePassword(String gesturePassword, final changeGesturePasswordCallBack changeGesturePasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.CHANGE_GESTURE_PASSWORD);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("gesturePassword",gesturePassword);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.CHANGE_GESTURE_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        changeGesturePasswordCallBack.onSuccess(paseData);
                    }

                });
    }
}
