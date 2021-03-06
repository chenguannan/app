package com.xinyilian.text.realname_sms.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.RealnameSMSBean;
import com.xinyilian.text.realname_sms.model.RealnameSMSModel;
import com.xinyilian.text.realname_sms.model.RealnameSMSModelImpl;
import com.xinyilian.text.realname_sms.view.RealnameSMSView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class RealnameSMSPersenter {

    private Activity mActivity;
    private RealnameSMSView mRealnameSMSView;
    private RealnameSMSModelImpl mRealnameSMSModel;

    public RealnameSMSPersenter(Activity activity, RealnameSMSView RealnameSMSView) {
        mActivity = activity;
        mRealnameSMSView = RealnameSMSView;
        mRealnameSMSModel = new RealnameSMSModelImpl(activity);
    }

    public void realnameSMS(String tel) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入您的手机号");
            return;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return;
        }
        mRealnameSMSModel.realnameSMS(tel, new RealnameSMSModel.IRealnameSMSCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("实名认证验证码：" + data);
                RealnameSMSBean realnameSMSBean = new Gson().fromJson(data, RealnameSMSBean.class);
                if ("000000".equals(realnameSMSBean.getCode())) {
                    mRealnameSMSView.realnameSMSSuccess(realnameSMSBean.getData());
                    return;
                } else if ("888888".equals(realnameSMSBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(realnameSMSBean.getMessage());

            }
        });
    }

}

