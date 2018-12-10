package com.sl_group.jinyuntong_oem.pay_password_change.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.ChangePayPasswordBean;
import com.sl_group.jinyuntong_oem.pay_password_change.model.PayPasswordChangeModel;
import com.sl_group.jinyuntong_oem.pay_password_change.model.PayPasswordChangeModelImpl;
import com.sl_group.jinyuntong_oem.pay_password_change.view.PayPasswordChangeView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class PayPasswordChangePersenter {
    private Activity mActivity;
    private PayPasswordChangeView mPayPasswordChangeView;
    private PayPasswordChangeModelImpl mChangePayPasswordModel;

    public PayPasswordChangePersenter(Activity activity, PayPasswordChangeView payPasswordChangeView) {
        mActivity = activity;
        mPayPasswordChangeView = payPasswordChangeView;
        mChangePayPasswordModel = new PayPasswordChangeModelImpl(activity);
    }

    public void payPasswordSet(String cellPhone, String checkCode, String uuid, String tradePassword) {
        if (StringUtils.isEmpty(tradePassword)) {
            ToastUtils.showToast("请输入支付密码");
            return;
        }
        mChangePayPasswordModel.changePayPassword(cellPhone, checkCode, uuid, tradePassword, new PayPasswordChangeModel.IChangePayPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("修改支付密码：" + data);
                ChangePayPasswordBean changePayPasswordBean = new Gson().fromJson(data, ChangePayPasswordBean.class);
                if ("000000".equals(changePayPasswordBean.getCode())) {
                    mPayPasswordChangeView.payPasswordChangeSuccess();
                }
                ToastUtils.showToast(changePayPasswordBean.getMessage());
            }
        });
    }
}
