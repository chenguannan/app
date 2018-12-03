package com.sl_group.jinyuntong_oem.vip.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.BankFeeBean;
import com.sl_group.jinyuntong_oem.bean.BuyVipBean;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.vip.model.VipCenterModel;
import com.sl_group.jinyuntong_oem.vip.model.VipCenterModelImpl;
import com.sl_group.jinyuntong_oem.vip.view.VipCenterView;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class VipCenterPersenter {
    private Activity mActivity;
    private VipCenterView mVipCenterView;
    private VipCenterModelImpl mVipCenterModel;

    public VipCenterPersenter(Activity activity, VipCenterView vipCenterView) {
        mActivity = activity;
        mVipCenterView = vipCenterView;
        mVipCenterModel = new VipCenterModelImpl(activity);
    }

    public void queryBankFee(){
        mVipCenterModel.queryBankFee(new VipCenterModel.IQueryBankFeeCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("查询费率："+data);
                BankFeeBean bankFeeBean = new Gson().fromJson(data,BankFeeBean.class);
                if ("000000".equals(bankFeeBean.getCode())){
                    mVipCenterView.getBankFeeList(bankFeeBean.getData());
                    return;
                }else if ("888888".equals(bankFeeBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(bankFeeBean.getMessage());

            }
        });
    }
    public void buyVip(String accountNumber){
        mVipCenterModel.buyVip(accountNumber,new VipCenterModel.IBuyVipCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("购买VIP："+data);
                BuyVipBean buyVipBean = new Gson().fromJson(data,BuyVipBean.class);
                if ("000000".equals(buyVipBean.getCode())){
                    mVipCenterView.buyVipSuccess(buyVipBean.getData().getOpenUrl());
                    return;
                }else if ("888888".equals(buyVipBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(buyVipBean.getMessage());
            }
        });
    }
}
