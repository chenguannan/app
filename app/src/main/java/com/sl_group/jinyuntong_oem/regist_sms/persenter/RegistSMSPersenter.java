package com.sl_group.jinyuntong_oem.regist_sms.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.RegisterSMSBean;
import com.sl_group.jinyuntong_oem.regist_sms.model.RegistSMSModel;
import com.sl_group.jinyuntong_oem.regist_sms.model.RegistSMSModelImpl;
import com.sl_group.jinyuntong_oem.regist_sms.view.RegistSMSView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public class RegistSMSPersenter {

    private Activity mActivity;
    private RegistSMSModelImpl mRegistSMSModel;
    private RegistSMSView mRegistSMSView;

    public RegistSMSPersenter(Activity activity, RegistSMSView registSMSView) {
        this.mActivity = activity;
        mRegistSMSView = registSMSView;
        mRegistSMSModel = new RegistSMSModelImpl(activity);
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param inviteCode 邀请码
     * @param tel        手机号
     */
    public void registSMS(String inviteCode, String tel) {
        if (StringUtils.isEmpty(inviteCode)) {
            ToastUtils.showToast("请输入邀请码");
            return ;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return ;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的手机号码");
            return ;
        }
        mRegistSMSModel.registSMS(inviteCode, tel, new RegistSMSModel.IRegistSMSCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("注册获取验证码：" + data);
                RegisterSMSBean registerSmsBean = new Gson().fromJson(data, RegisterSMSBean.class);
                if ("000000".equals(registerSmsBean.getCode())) {
                    mRegistSMSView.registSMSSuccess(registerSmsBean.getData());
                } else if ("888888".equals(registerSmsBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                }
                ToastUtils.showToast(registerSmsBean.getMessage());

            }
        });
    }

}
