package com.sl_group.jinyuntong_oem.place_order.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.PlaceOrderBean;
import com.sl_group.jinyuntong_oem.place_order.model.PlaceOrderModel;
import com.sl_group.jinyuntong_oem.place_order.model.PlaceOrderModelImpl;
import com.sl_group.jinyuntong_oem.place_order.view.PlaceOrderView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class PlaceOrderPersenter {
    private Activity mActivity;
    private PlaceOrderView mPlaceOrderView;
    private PlaceOrderModelImpl mScanQrcodeInputMoneyModel;

    public PlaceOrderPersenter(Activity activity, PlaceOrderView placeOrderView) {
        mActivity = activity;
        mPlaceOrderView = placeOrderView;
        mScanQrcodeInputMoneyModel = new PlaceOrderModelImpl(activity);
    }


    public void planceOrder(String faceUuid, String uuid, String accountNumber, String tradePassword, String srcAmt) {
        mScanQrcodeInputMoneyModel.placeOrder(faceUuid, uuid, accountNumber, tradePassword, srcAmt, new PlaceOrderModel.IPlaceOrderCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("下单：" + data);
                PlaceOrderBean placeOrderBean = new Gson().fromJson(data, PlaceOrderBean.class);
                if ("000000".equals(placeOrderBean.getCode())) {
                    mPlaceOrderView.placeOrderSuccess(placeOrderBean.getData().getOpenUrl());
                    return;
                } else if ("888888".equals(placeOrderBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(placeOrderBean.getMessage());

            }
        });
    }
}
