package com.sl_group.jinyuntong_oem.scan_input_money.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.PlaceOrderBean;
import com.sl_group.jinyuntong_oem.bean.SetPayPasswordBean;
import com.sl_group.jinyuntong_oem.scan_input_money.model.ScanQrcodeInputMoneyModel;
import com.sl_group.jinyuntong_oem.scan_input_money.model.ScanQrcodeInputMoneyModelImpl;
import com.sl_group.jinyuntong_oem.scan_input_money.view.ScanQrcodeInputMoneyView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class ScanQrcodeInputMoneyPersenter {
    private Activity mActivity;
    private ScanQrcodeInputMoneyView mScanQrcodeInputMoneyView;
    private ScanQrcodeInputMoneyModelImpl mScanQrcodeInputMoneyModel;

    public ScanQrcodeInputMoneyPersenter(Activity activity, ScanQrcodeInputMoneyView scanQrcodeInputMoneyView) {
        mActivity = activity;
        mScanQrcodeInputMoneyView = scanQrcodeInputMoneyView;
        mScanQrcodeInputMoneyModel = new ScanQrcodeInputMoneyModelImpl(activity);
    }

    public void setPayPassword(final String payPassword) {
        mScanQrcodeInputMoneyModel.setPayPassword(payPassword, new ScanQrcodeInputMoneyModel.ISetPayPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置支付密码："+data);
                SetPayPasswordBean setPayPasswordBean = new Gson().fromJson(data,SetPayPasswordBean.class);
                if ("000000".equals(setPayPasswordBean.getCode())){
                    SPUtil.put(mActivity,"tradePassword", payPassword);
                    mScanQrcodeInputMoneyView.setPasswordSuccess(payPassword);
                    return;
                }else if ("888888".equals(setPayPasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setPayPasswordBean.getMessage());
            }
        });
    }

    public void planceOrder(String faceUuid,String uuid,String accountNumber, String tradePassword,String srcAmt) {
        mScanQrcodeInputMoneyModel.placeOrder(faceUuid,uuid,accountNumber,tradePassword,srcAmt, new ScanQrcodeInputMoneyModel.IPlaceOrderCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("下单："+data);
                PlaceOrderBean placeOrderBean = new Gson().fromJson(data,PlaceOrderBean.class);
                if ("000000".equals(placeOrderBean.getCode())){
                    mScanQrcodeInputMoneyView.placeOrderSuccess(placeOrderBean.getData().getOpenUrl());
                    return;
                }else if ("888888".equals(placeOrderBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(placeOrderBean.getMessage());

            }
        });
    }
}
