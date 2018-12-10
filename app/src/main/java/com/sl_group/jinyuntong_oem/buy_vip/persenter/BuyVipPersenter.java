package com.sl_group.jinyuntong_oem.buy_vip.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.BuyVipBean;
import com.sl_group.jinyuntong_oem.buy_vip.model.BuyVipModel;
import com.sl_group.jinyuntong_oem.buy_vip.model.BuyVipModelImpl;
import com.sl_group.jinyuntong_oem.buy_vip.view.BuyVipView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class BuyVipPersenter {
    private Activity mActivity;
    private BuyVipView mBuyVipView;
    private BuyVipModelImpl mVipCenterModel;

    public BuyVipPersenter(Activity activity, BuyVipView buyVipView) {
        mActivity = activity;
        mBuyVipView = buyVipView;
        mVipCenterModel = new BuyVipModelImpl(activity);
    }


    public void buyVip(String accountNumber) {
        mVipCenterModel.buyVip(accountNumber, new BuyVipModel.IBuyVipCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("购买VIP：" + data);
                BuyVipBean buyVipBean = new Gson().fromJson(data, BuyVipBean.class);
                if ("000000".equals(buyVipBean.getCode())) {
                    mBuyVipView.buyVipSuccess(buyVipBean.getData().getOpenUrl());
                    return;
                } else if ("888888".equals(buyVipBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(buyVipBean.getMessage());
            }
        });
    }
}
