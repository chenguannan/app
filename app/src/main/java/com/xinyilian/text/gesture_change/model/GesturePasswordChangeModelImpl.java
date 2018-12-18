package com.xinyilian.text.gesture_change.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.CommonParamsUtils;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ParamsFormatUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：修改手势密码
 */
public class GesturePasswordChangeModelImpl implements GesturePasswordChangeModel {

    private Activity mActivity;

    public GesturePasswordChangeModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void changeGesturePassword(String cellPhone, String checkCode, String uuid, String gesturePassword, final changeGesturePasswordCallBack changeGesturePasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.CHANGE_GESTURE_PASSWORD);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("cellPhone", cellPhone);
        obj.put("checkCode", checkCode);
        obj.put("uuid", uuid);
        obj.put("gesturePassword", gesturePassword);
        LogUtils.i("cellPhone："+cellPhone);
        LogUtils.i("checkCode："+checkCode);
        LogUtils.i("uuid："+uuid);
        LogUtils.i("gesturePassword："+gesturePassword);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.CHANGE_GESTURE_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
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
