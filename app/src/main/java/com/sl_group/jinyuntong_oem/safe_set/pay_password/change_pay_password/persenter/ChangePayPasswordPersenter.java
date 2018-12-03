package com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.ChangePayPasswordBean;
import com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.model.ChangePayPasswordModel;
import com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.model.ChangePayPasswordModelImpl;
import com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.view.ChangePayPasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class ChangePayPasswordPersenter {
    private Activity mActivity;
    private ChangePayPasswordView mChangePayPasswordView;
    private ChangePayPasswordModelImpl mChangePayPasswordModel;

    public ChangePayPasswordPersenter(Activity activity, ChangePayPasswordView changePayPasswordView) {
        mActivity = activity;
        mChangePayPasswordView = changePayPasswordView;
        mChangePayPasswordModel = new ChangePayPasswordModelImpl(activity);
    }

    public void changePayPassword(String tradePassword) {
        if (!mChangePayPasswordModel.checkParams(tradePassword)) {
            return;
        }
        mChangePayPasswordModel.changePayPassword(tradePassword, new ChangePayPasswordModel.IChangePayPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("修改支付密码：" + data);
                ChangePayPasswordBean changePayPasswordBean = new Gson().fromJson(data, ChangePayPasswordBean.class);
                if ("000000".equals(changePayPasswordBean.getCode())) {
                    mChangePayPasswordView.changePayPasswordSuccess();
                }
                ToastUtils.showToast(changePayPasswordBean.getMessage());
            }
        });
    }
}
