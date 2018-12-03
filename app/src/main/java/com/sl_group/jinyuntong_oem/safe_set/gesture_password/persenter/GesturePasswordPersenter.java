package com.sl_group.jinyuntong_oem.safe_set.gesture_password.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetGesturePasswordBean;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.model.GesturePasswordModel;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.model.GesturePasswordModelImpl;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.view.GesturePasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class GesturePasswordPersenter {
    private Activity mActivity;
    private GesturePasswordView mGesturePasswordView;
    private GesturePasswordModelImpl mGesturePasswordModel;

    public GesturePasswordPersenter(Activity activity, GesturePasswordView gesturePasswordView) {
        mActivity = activity;
        mGesturePasswordView = gesturePasswordView;
        mGesturePasswordModel = new GesturePasswordModelImpl(activity);
    }



    public void clearGesturePassword(){
        mGesturePasswordModel.clearGesturePassword( new GesturePasswordModel.clearGesturePasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置手势密码："+data);
                SetGesturePasswordBean setGesturePasswordBean = new Gson().fromJson(data,SetGesturePasswordBean.class);
                if ("000000".equals(setGesturePasswordBean.getCode())){
                    mGesturePasswordView.resetGesturePassword();
                    return;
                }else if ("888888".equals(setGesturePasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setGesturePasswordBean.getMessage());
            }
        });

    }
}
