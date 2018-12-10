package com.sl_group.jinyuntong_oem.set_pay_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetPayPasswordBean;
import com.sl_group.jinyuntong_oem.set_pay_password.model.SetPayPasswordModel;
import com.sl_group.jinyuntong_oem.set_pay_password.model.SetPayPasswordModelImpl;
import com.sl_group.jinyuntong_oem.set_pay_password.view.SetPayPasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class SetPayPasswordPersenter {
    private Activity mActivity;
    private SetPayPasswordView mSetPayPasswordView;
    private SetPayPasswordModelImpl mSetPayPasswordModel;

    public SetPayPasswordPersenter(Activity activity, SetPayPasswordView setPayPasswordView) {
        mActivity = activity;
        mSetPayPasswordView = setPayPasswordView;
        mSetPayPasswordModel = new SetPayPasswordModelImpl(activity);
    }

    public void setPayPassword(String cellPhone,String checkCode,String uuid,final String payPassword) {
        mSetPayPasswordModel.setPayPassword( cellPhone, checkCode, uuid,payPassword, new SetPayPasswordModel.ISetPayPasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置支付密码："+data);
                SetPayPasswordBean setPayPasswordBean = new Gson().fromJson(data,SetPayPasswordBean.class);
                if ("000000".equals(setPayPasswordBean.getCode())){
                    SPUtil.put(mActivity,"tradePassword", payPassword);
                    mSetPayPasswordView.setPasswordSuccess(payPassword);
                    return;
                }else if ("888888".equals(setPayPasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setPayPasswordBean.getMessage());
            }
        });
    }
}
