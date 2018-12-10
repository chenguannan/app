package com.sl_group.jinyuntong_oem.place_order.model;

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
