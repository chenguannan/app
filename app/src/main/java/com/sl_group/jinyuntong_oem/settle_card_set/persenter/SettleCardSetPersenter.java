package com.sl_group.jinyuntong_oem.settle_card_set.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetSettleCardBean;
import com.sl_group.jinyuntong_oem.settle_card_set.model.SettleCardSetModel;
import com.sl_group.jinyuntong_oem.settle_card_set.model.SettleCardSetModelImpl;
import com.sl_group.jinyuntong_oem.settle_card_set.view.SettleCardSetView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class SettleCardSetPersenter {
    private Activity mActivity;
    private SettleCardSetView mSettleCardSetView;
    private SettleCardSetModelImpl mSettleInfoModel;

    public SettleCardSetPersenter(Activity activity, SettleCardSetView settleCardSetView) {
        mActivity = activity;
        mSettleCardSetView = settleCardSetView;
        mSettleInfoModel = new SettleCardSetModelImpl(activity);
    }


    public void settleCardSet(String accountNumber, String tel) {
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入储蓄卡卡号");
            return;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return;
        }
        mSettleInfoModel.settleCardSet(accountNumber, tel, new SettleCardSetModel.ISettleCardSetCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置结算卡：" + data);
                SetSettleCardBean setSettleCardBean = new Gson().fromJson(data, SetSettleCardBean.class);
                if ("000000".equals(setSettleCardBean.getCode())) {
                    mSettleCardSetView.settleCardSetSuccess(setSettleCardBean.getData());
                    return;
                } else if ("888888".equals(setSettleCardBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setSettleCardBean.getMessage());
            }
        });
    }
}