package com.sl_group.jinyuntong_oem.unbind_creditcard.percenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.UnbindCreditCardBean;
import com.sl_group.jinyuntong_oem.unbind_creditcard.model.UnBindCredirtCardModel;
import com.sl_group.jinyuntong_oem.unbind_creditcard.model.UnBindCreditCardModelImpl;
import com.sl_group.jinyuntong_oem.unbind_creditcard.view.UnBindCreditCardView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/29.
 * description：
 */
public class UnBindCreditCardPercenter {
    private Activity mActivity;
    private UnBindCreditCardView mUnBindCreditCardView;
    private UnBindCreditCardModelImpl mUnBindCreditCardModel;

    public UnBindCreditCardPercenter(Activity activity, UnBindCreditCardView unBindCreditCardView) {
        mActivity = activity;
        mUnBindCreditCardView = unBindCreditCardView;
        mUnBindCreditCardModel = new UnBindCreditCardModelImpl(activity);
    }

    public void unBindCreditCard(String fastpayBankAccountInfoId){
        mUnBindCreditCardModel.unBindCreditCard(fastpayBankAccountInfoId, new UnBindCredirtCardModel.IUnBindCreditCardCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("解绑信用卡："+data);
                UnbindCreditCardBean unbindCreditCardBean = new Gson().fromJson(data,UnbindCreditCardBean.class);
                if ("000000".equals(unbindCreditCardBean.getCode())){
                    mUnBindCreditCardView.unBindCreditCardSuccess();
                }
                ToastUtils.showToast(unbindCreditCardBean.getMessage());
            }
        });
    }
}
