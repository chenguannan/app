package com.sl_group.jinyuntong_oem.pay_bill.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.PayBillBean;
import com.sl_group.jinyuntong_oem.pay_bill.model.PayBillModel;
import com.sl_group.jinyuntong_oem.pay_bill.model.PayBillModelImpl;
import com.sl_group.jinyuntong_oem.pay_bill.view.PayBillView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：付款账单
 */
public class PayBillPersenter {
    private Activity mActivity;
    private PayBillView mPayBillView;
    private PayBillModelImpl mPayBillModel;

    public PayBillPersenter(Activity activity, PayBillView PayBillView) {
        mActivity = activity;
        mPayBillView = PayBillView;
        mPayBillModel = new PayBillModelImpl(activity);
    }

    public void getPayBill(boolean isShowPeogress,int curPage, String pageSize, String beginDate, String endDate){
        mPayBillModel.payBill(isShowPeogress,curPage, pageSize, beginDate, endDate, new PayBillModel.IPayBillCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("付款账单："+data);
                PayBillBean PayBillBean = new Gson().fromJson(data,PayBillBean.class);
                if ("000000".equals(PayBillBean.getCode())){
                    mPayBillView.payBillSuccess(PayBillBean.getData());
                    return;
                }else if ("888888".equals(PayBillBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(PayBillBean.getMessage());
            }
        });
    }
}
