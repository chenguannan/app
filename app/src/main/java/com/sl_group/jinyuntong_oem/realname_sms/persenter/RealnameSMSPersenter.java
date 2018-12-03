package com.sl_group.jinyuntong_oem.realname_sms.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.CommonSMSBean;
import com.sl_group.jinyuntong_oem.realname_sms.model.RealnameSMSModel;
import com.sl_group.jinyuntong_oem.realname_sms.model.RealnameSMSModelImpl;
import com.sl_group.jinyuntong_oem.realname_sms.view.RealnameSMSView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

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

    public void getRealnameSMS(String tel){
        if (!mRealnameSMSModel.checkParams(tel)){
            return;
        }
        mRealnameSMSModel.getRealnameSMS(tel, new RealnameSMSModel.IRealnameSMSCallBack(){
            @Override
            public void onSuccess(String data) {
                LogUtils.i("实名验证码："+data);
                CommonSMSBean commonSMSBean = new Gson().fromJson(data,CommonSMSBean.class);
                if ("000000".equals(commonSMSBean.getCode())){
                    mRealnameSMSView.getRealnameSMS(commonSMSBean.getData());
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

