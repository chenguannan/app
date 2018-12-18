package com.xinyilian.text.realname.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.RealnameBean;
import com.xinyilian.text.realname.model.RealnameModel;
import com.xinyilian.text.realname.model.RealnameModelImpl;
import com.xinyilian.text.realname.view.RealnameView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/16.
 * description：实名认证
 */
public class RealnamePersenter {
    private Activity mActivity;
    private RealnameModelImpl mRealnameMode;
    private RealnameView mRealnameView;

    public RealnamePersenter(Activity activity, RealnameView realnameView) {
        mActivity = activity;
        mRealnameView = realnameView;
        mRealnameMode = new RealnameModelImpl(activity);
    }

    public void realname(String idcard, String holderName, String accountNumber, String tel, String checkCode ,String uuid, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId) {
        if (StringUtils.isEmpty(idcard)||StringUtils.isEmpty(holderName)){
            ToastUtils.showToast("人脸识别获取数据异常");
            return ;
        }
        if (StringUtils.isEmpty(accountNumber)){
            ToastUtils.showToast("请输入储蓄卡号");
            return ;
        }
        if (StringUtils.isEmpty(tel)){
            ToastUtils.showToast("请输入银行预留手机号");
            return ;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return ;
        }
        if (StringUtils.isEmpty(uuid)) {
            ToastUtils.showToast("请先获取验证码");
            return;
        }
        if (StringUtils.isEmpty(checkCode)||checkCode.length()!=6){
            ToastUtils.showToast("请输入验证码");
            return;
        }
        if (StringUtils.isEmpty(bizPlaceSnapshot1ImageId)){
            ToastUtils.showToast("请上传手持身份证照片");
            return ;
        }
        if (StringUtils.isEmpty(bizPlaceSnapshot2ImageId)){
            ToastUtils.showToast("请上传银行卡正面照片");
            return ;
        }
        mRealnameMode.realname(idcard, holderName, accountNumber, tel,checkCode,uuid, bizPlaceSnapshot1ImageId, bizPlaceSnapshot2ImageId, new RealnameModel.IRealnameCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("实名认证：" + data);
                RealnameBean realnameBean = new Gson().fromJson(data, RealnameBean.class);
                if ("000000".equals(realnameBean.getCode())) {
                    mRealnameView.realnameSuccess();
                    return;
                }else if ("888888".equals(realnameBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(realnameBean.getMessage());
            }
        });
    }
}
