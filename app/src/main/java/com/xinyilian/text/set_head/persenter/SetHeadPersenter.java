package com.xinyilian.text.set_head.persenter;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.SetHeadImgBean;
import com.xinyilian.text.set_head.model.SetHeadModel;
import com.xinyilian.text.set_head.model.SetHeadModelImpl;
import com.xinyilian.text.set_head.view.SetHeadView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class SetHeadPersenter {
    private Activity mActivity;
    private SetHeadView mSetHeadView;
    private SetHeadModelImpl mSetHeadModel;

    public SetHeadPersenter(Activity activity, SetHeadView setHeadView) {
        mActivity = activity;
        mSetHeadView = setHeadView;
        mSetHeadModel = new SetHeadModelImpl(activity);
    }

    public void setHeadImg(String uuid) {
        mSetHeadModel.setHeadImg(uuid, new SetHeadModel.ISetHeadImgCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置头像：" + data);
                SetHeadImgBean setHeadImgBean = new Gson().fromJson(data, SetHeadImgBean.class);
                if ("000000".equals(setHeadImgBean.getCode())) {
                    Intent intent = new Intent();
                    intent.setAction("panhouye");
                    intent.putExtra("sele","潘侯爷");
                    mActivity.sendBroadcast(intent);
                    mSetHeadView.setHeadImgSuccess(setHeadImgBean.getData());
                    return;
                } else if ("888888".equals(setHeadImgBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setHeadImgBean.getMessage());
            }
        });
    }
}
