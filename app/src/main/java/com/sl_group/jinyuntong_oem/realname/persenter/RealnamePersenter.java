package com.sl_group.jinyuntong_oem.realname.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.RealnameBean;
import com.sl_group.jinyuntong_oem.realname.model.RealnameModel;
import com.sl_group.jinyuntong_oem.realname.model.RealnameModelImpl;
import com.sl_group.jinyuntong_oem.realname.view.RealnameView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/16.
 * description：
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
        if (!mRealnameMode.checkRealNameParams(idcard, holderName, accountNumber, tel, bizPlaceSnapshot1ImageId, bizPlaceSnapshot2ImageId)) {
            return;
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
