package com.sl_group.jinyuntong_oem.youdun_uuid.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.YouDunUUIDBean;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.youdun_uuid.model.YouDunUUIDModel;
import com.sl_group.jinyuntong_oem.youdun_uuid.model.YouDunUUIDModelImpl;
import com.sl_group.jinyuntong_oem.youdun_uuid.view.YouDunUUIDView;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class YouDunUUIDPersenter {
    private Activity mActivity;
    private YouDunUUIDView mYouDunUUIDView;
    private YouDunUUIDModelImpl mYouDunModel;

    public YouDunUUIDPersenter(Activity activity, YouDunUUIDView youDunUUIDView) {
        mActivity = activity;
        mYouDunUUIDView = youDunUUIDView;
        mYouDunModel = new YouDunUUIDModelImpl(activity);
    }

    public void getYouDunUUID(){
        mYouDunModel.getYouDunUUID(new YouDunUUIDModel.IYouDunUUIDCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("有盾UUID："+data);
                YouDunUUIDBean youDunUUIDBean = new Gson().fromJson(data,YouDunUUIDBean.class);
                if ("000000".equals(youDunUUIDBean.getCode())){
                    mYouDunUUIDView.getYouDunUUIDSuccess(youDunUUIDBean.getData().getUuid());
                    return;
                }else if ("888888".equals(youDunUUIDBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(youDunUUIDBean.getMessage());
            }
        });
    }
}
