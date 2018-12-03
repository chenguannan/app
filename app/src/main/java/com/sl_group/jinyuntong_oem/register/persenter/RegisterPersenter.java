package com.sl_group.jinyuntong_oem.register.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.RegistBean;
import com.sl_group.jinyuntong_oem.bean.RegisterSMSBean;
import com.sl_group.jinyuntong_oem.register.model.RegisterModel;
import com.sl_group.jinyuntong_oem.register.model.RegisterModelImpl;
import com.sl_group.jinyuntong_oem.register.view.RegisterView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public class RegisterPersenter {

    private Activity mActivity;
    private RegisterModelImpl mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPersenter(Activity activity, RegisterView registerView) {
        this.mActivity = activity;
        mRegisterView = registerView;
        mRegisterModel = new RegisterModelImpl(activity);
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param inviteCode 邀请码
     * @param tel        手机号
     */
    public void getSMS(String inviteCode, String tel) {
        if (!mRegisterModel.checkSMSParams(inviteCode, tel)) {
            return;
        }
        mRegisterModel.getSMS(inviteCode, tel, new RegisterModel.IRegisterGetSMSCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("注册获取验证码：" + data);
                RegisterSMSBean registerSmsBean = new Gson().fromJson(data, RegisterSMSBean.class);
                if ("000000".equals(registerSmsBean.getCode())) {
                    mRegisterView.startCountTime();
                } else if ("888888".equals(registerSmsBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                }
                ToastUtils.showToast(registerSmsBean.getMessage());

            }
        });
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param inviteCode     邀请码
     * @param tel            手机号
     * @param vificationCode 验证码
     * @param password       密码
     * @param passwordAgain  再次输入密码
     */
    public void register(String inviteCode, String tel, String vificationCode, String password, String passwordAgain, boolean isSelected) {
        if (!mRegisterModel.checkRegisterParams(inviteCode, tel, vificationCode, password, passwordAgain, isSelected)) {
            return;
        }
        mRegisterModel.register(inviteCode, tel, password, new RegisterModel.IRegisterCallBack() {
            @Override
            public void onSuccess(String data) {

                LogUtils.i("注册：" + data);
                RegistBean registBean = new Gson().fromJson(data, RegistBean.class);
                if ("000000".equals(registBean.getCode())) {
                    RegistBean.DataBean dataBean = registBean.getData();
                    SPUtil.put(mActivity, "mid", dataBean.getMid());
                    SPUtil.put(mActivity, "encryptId", dataBean.getEncryptId());
                    SPUtil.put(mActivity, "key", dataBean.getKey());
                    mRegisterView.registSuccess();
                    return;
                }
                ToastUtils.showToast(registBean.getMessage());
            }
        });
    }
}
