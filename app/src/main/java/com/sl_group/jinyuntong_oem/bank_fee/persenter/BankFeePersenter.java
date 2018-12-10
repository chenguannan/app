package com.sl_group.jinyuntong_oem.bank_fee.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bank_fee.model.BankFeeModel;
import com.sl_group.jinyuntong_oem.bank_fee.model.BankFeeModelImpl;
import com.sl_group.jinyuntong_oem.bank_fee.view.BankFeeView;
import com.sl_group.jinyuntong_oem.bean.BankFeeBean;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/12/10.
 * description：
 */
public class BankFeePersenter {
    private Activity mActivity;
    private BankFeeView mBankFeeView;
    private BankFeeModelImpl mBankFeeModel;

    public BankFeePersenter(Activity activity, BankFeeView bankFeeView) {
        mActivity = activity;
        mBankFeeView = bankFeeView;
        mBankFeeModel = new BankFeeModelImpl(activity);
    }

    public void bankFee(){
        mBankFeeModel.bankFee(new BankFeeModel.IBankFeeCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("查询费率："+data);
                BankFeeBean bankFeeBean = new Gson().fromJson(data,BankFeeBean.class);
                if ("000000".equals(bankFeeBean.getCode())){
                    mBankFeeView.bankFeeSuccess(bankFeeBean.getData());
                    return;
                }else if ("888888".equals(bankFeeBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(bankFeeBean.getMessage());
            }
        });
    }
}
