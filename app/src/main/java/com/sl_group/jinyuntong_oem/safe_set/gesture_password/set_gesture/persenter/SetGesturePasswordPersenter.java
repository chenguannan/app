package com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetGesturePasswordBean;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.model.SetGesturePasswordModel;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.model.SetGesturePasswordModelImpl;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.view.SetGesturePasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class SetGesturePasswordPersenter {
    private Activity mActivity;
    private SetGesturePasswordView mSetGesturePasswordView;
    private SetGesturePasswordModelImpl mSetGesturePasswordModel;

    public SetGesturePasswordPersenter(Activity activity, SetGesturePasswordView setGesturePasswordView) {
        mActivity = activity;
        mSetGesturePasswordView = setGesturePasswordView;
        mSetGesturePasswordModel = new SetGesturePasswordModelImpl(activity);
    }

    public void setGesturePassword(String gesturePassword) {
        mSetGesturePasswordModel.setGesturePassword(gesturePassword, new SetGesturePasswordModel.setGesturePasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置手势密码：" + data);
                SetGesturePasswordBean setGesturePasswordBean = new Gson().fromJson(data, SetGesturePasswordBean.class);
                if ("000000".equals(setGesturePasswordBean.getCode())) {
                    mSetGesturePasswordView.finshActivity();
                    return;
                } else if ("888888".equals(setGesturePasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                mSetGesturePasswordView.resetGesturePassword();
                ToastUtils.showToast(setGesturePasswordBean.getMessage());


            }
        });

    }
}
