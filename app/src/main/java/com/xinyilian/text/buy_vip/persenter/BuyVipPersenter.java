package com.xinyilian.text.buy_vip.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.BuyVipBean;
import com.xinyilian.text.buy_vip.model.BuyVipModel;
import com.xinyilian.text.buy_vip.model.BuyVipModelImpl;
import com.xinyilian.text.buy_vip.view.BuyVipView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

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
