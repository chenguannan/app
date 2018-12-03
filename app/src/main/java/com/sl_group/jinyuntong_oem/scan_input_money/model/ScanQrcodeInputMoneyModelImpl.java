package com.sl_group.jinyuntong_oem.scan_input_money.model;

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
 * description：
 */
public class ScanQrcodeInputMoneyModelImpl implements ScanQrcodeInputMoneyModel {
    private Activity mActivity;

    public ScanQrcodeInputMoneyModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public void placeOrder(String faceUuid,String uuid,String accountNumber, String tradePassword,String srcAmt, final IPlaceOrderCallBack placeOrderCallBack) {
        //下单
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.PLACE_ORDER);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("uuid", uuid);
        obj.put("payedMid", SPUtil.get(mActivity, "mid", ""));
        obj.put("accountNumber",accountNumber);
        obj.put("tradePassword",tradePassword);
        obj.put("srcAmt", srcAmt);

        obj.put("faceUuid", faceUuid);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                "https://name.znyoo.cn/oss-transaction/gateway/fastpayQrcodePrecreate",
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

    @Override
    public void setPayPassword(String tradePassword, final ISetPayPasswordCallBack setPayPasswordCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.SET_PAY_PASSWORD);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("tradePassword",tradePassword);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.SET_PAY_PASSWORD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        setPayPasswordCallBack.onSuccess(paseData);
                    }

                });
    }


}
