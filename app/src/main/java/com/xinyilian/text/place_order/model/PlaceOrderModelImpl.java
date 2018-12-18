package com.xinyilian.text.place_order.model;

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
 * Created by 马天 on 2018/11/21.
 * description：下单
 */
public class PlaceOrderModelImpl implements PlaceOrderModel {
    private Activity mActivity;

    public PlaceOrderModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void placeOrder(String faceUuid, String qrcodeContent, String accountNumber, String tradePassword, String srcAmt, final IPlaceOrderCallBack placeOrderCallBack) {
        //下单
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.PLACE_ORDER);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("uuid", qrcodeContent);
        obj.put("payedMid", SPUtil.get(mActivity, "mid", ""));
        obj.put("accountNumber",accountNumber);
        obj.put("tradePassword",tradePassword);
        obj.put("srcAmt", srcAmt);
        obj.put("faceUuid", faceUuid);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.PLACE_ORDER_URL+"fastpayQrcodePrecreate",
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        placeOrderCallBack.onSuccess(paseData);
                    }

                });
    }



}
