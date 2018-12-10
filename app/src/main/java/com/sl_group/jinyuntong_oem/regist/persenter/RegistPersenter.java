package com.sl_group.jinyuntong_oem.regist.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.RegistBean;
import com.sl_group.jinyuntong_oem.regist.model.RegistModel;
import com.sl_group.jinyuntong_oem.regist.model.RegistModelImpl;
import com.sl_group.jinyuntong_oem.regist.view.RegistView;
import com.sl_group.jinyuntong_oem.utils.IsNumberUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public class RegistPersenter {

    private Activity mActivity;
    private RegistModelImpl mRegisterModel;
    private RegistView mRegistView;

    public RegistPersenter(Activity activity, RegistView registView) {
        this.mActivity = activity;
        mRegistView = registView;
        mRegisterModel = new RegistModelImpl(activity);
    }


    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     *
     * @param inviteCode     邀请码
     * @param tel            手机号
     * @param vificationCode 验证码
     * @param password       密码
     * @param passwordAgain  再次输入密码
     * @param isSelected     勾选用户协议
     */
    public void register(String inviteCode, String tel, String vificationCode, String uuid, String password, String passwordAgain, boolean isSelected) {
        if (StringUtils.isEmpty(inviteCode)) {
            ToastUtils.showToast("请输入邀请码");
            return;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入手机号码");
            return;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号码");
            return;
        }
        if (StringUtils.isEmpty(uuid)) {
            ToastUtils.showToast("请获取短信验证码");
            return;
        }
        if (StringUtils.isEmpty(vificationCode)) {
            ToastUtils.showToast("请输入短信验证码");
            return;
        }
        if (StringUtils.isEmpty(password) || password.length() < 8) {
            ToastUtils.showToast("请输入8-14位数字和字母组合的密码");
            return;
        }
        if (!IsNumberUtils.isLetterDigit(password)) {
            ToastUtils.showToast("请输入数字和字母组合的密码");
            return;
        }

        if (StringUtils.isEmpty(passwordAgain) || passwordAgain.length() < 8) {
            ToastUtils.showToast("请再次输入登录密码");
            return;
        }

        if (!password.equals(passwordAgain)) {
            ToastUtils.showToast("两次密码输入不一致");
            return;
        }
        if (!isSelected) {
            ToastUtils.showToast("请阅读并勾选用户使用协议");
            return;
        }
        mRegisterModel.register(inviteCode, tel, vificationCode, uuid, password, new RegistModel.IRegisterCallBack() {
            @Override
            public void onSuccess(String data) {

                LogUtils.i("注册：" + data);
                RegistBean registBean = new Gson().fromJson(data, RegistBean.class);
                if ("000000".equals(registBean.getCode())) {
                    RegistBean.DataBean dataBean = registBean.getData();
                    SPUtil.put(mActivity, "mid", dataBean.getMid());
                    SPUtil.put(mActivity, "encryptId", dataBean.getEncryptId());
                    SPUtil.put(mActivity, "key", dataBean.getKey());
                    mRegistView.registSuccess();
                    return;
                }
                ToastUtils.showToast(registBean.getMessage());
            }
        });
    }
}
