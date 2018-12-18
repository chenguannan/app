package com.xinyilian.text.sms.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.CommonSMSBean;
import com.xinyilian.text.sms.model.SMSModel;
import com.xinyilian.text.sms.model.SMSModelImpl;
import com.xinyilian.text.sms.view.SMSView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class SMSPersenter {

    private Activity mActivity;
    private SMSView mSMSView;
    private SMSModelImpl mChangeGesturePasswordSMSVerficModel;

    public SMSPersenter(Activity activity, SMSView SMSView) {
        mActivity = activity;
        mSMSView = SMSView;
        mChangeGesturePasswordSMSVerficModel = new SMSModelImpl(activity);
    }

    public void getChangeGesturePsswordSMS(String tel){
        if (!mChangeGesturePasswordSMSVerficModel.checkParams(tel)){
            return;
        }
        mChangeGesturePasswordSMSVerficModel.getSMS(tel, new SMSModel.IChangeGesturePasswordSMSCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("验证码："+data);
                CommonSMSBean commonSMSBean = new Gson().fromJson(data,CommonSMSBean.class);
                if ("000000".equals(commonSMSBean.getCode())){
                    mSMSView.getSMS(commonSMSBean.getData());
                    return;
                }else if ("888888".equals(commonSMSBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                    ToastUtils.showToast(commonSMSBean.getMessage());

            }
        });
    }

}

