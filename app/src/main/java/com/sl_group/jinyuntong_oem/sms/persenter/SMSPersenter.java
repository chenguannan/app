package com.sl_group.jinyuntong_oem.sms.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.CommonSMSBean;
import com.sl_group.jinyuntong_oem.sms.model.SMSModel;
import com.sl_group.jinyuntong_oem.sms.model.SMSModelImpl;
import com.sl_group.jinyuntong_oem.sms.view.SMSView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

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

