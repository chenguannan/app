package com.xinyilian.text.pay_password_set.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.SetPayPasswordBean;
import com.xinyilian.text.pay_password_set.model.PayPasswordSetModel;
import com.xinyilian.text.pay_password_set.model.PayPasswordSetModelImpl;
import com.xinyilian.text.pay_password_set.view.PayPasswordSetView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class PayPasswordSetPersenter {
    private Activity mActivity;
    private PayPasswordSetView mPayPasswordSetView;
    private PayPasswordSetModelImpl mSetPayPasswordModel;

    public PayPasswordSetPersenter(Activity activity, PayPasswordSetView payPasswordSetView) {
        mActivity = activity;
        mPayPasswordSetView = payPasswordSetView;
        mSetPayPasswordModel = new PayPasswordSetModelImpl(activity);
    }

    public void payPasswordSet(String cellPhone, String checkCode, String uuid, final String payPassword) {
        mSetPayPasswordModel.payPasswordSet( cellPhone, checkCode, uuid,payPassword, new PayPasswordSetModel.IPayPasswordSetCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置支付密码："+data);
                SetPayPasswordBean setPayPasswordBean = new Gson().fromJson(data,SetPayPasswordBean.class);
                if ("000000".equals(setPayPasswordBean.getCode())){
                    SPUtil.put(mActivity,"tradePassword", payPassword);
                    mPayPasswordSetView.payPasswordSetSuccess(payPassword);
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
