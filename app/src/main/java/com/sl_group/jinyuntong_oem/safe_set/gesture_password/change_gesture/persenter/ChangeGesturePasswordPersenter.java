package com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetGesturePasswordBean;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.model.ChangeGesturePasswordModel;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.model.ChangeGesturePasswordModelImpl;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.view.ChangeGesturePasswordView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class ChangeGesturePasswordPersenter {
    private Activity mActivity;
    private ChangeGesturePasswordView mChangeGesturePasswordView;
    private ChangeGesturePasswordModelImpl mChangeGesturePasswordModel;

    public ChangeGesturePasswordPersenter(Activity activity, ChangeGesturePasswordView changeGesturePasswordView) {
        mActivity = activity;
        mChangeGesturePasswordView = changeGesturePasswordView;
        mChangeGesturePasswordModel = new ChangeGesturePasswordModelImpl(activity);
    }

    public void setGesturePassword(String cellPhone,String checkCode,String uuid,String gesturePassword) {
        mChangeGesturePasswordModel.changeGesturePassword(cellPhone,checkCode,uuid,gesturePassword, new ChangeGesturePasswordModel.changeGesturePasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("修改手势密码：" + data);
                SetGesturePasswordBean setGesturePasswordBean = new Gson().fromJson(data, SetGesturePasswordBean.class);
                if ("000000".equals(setGesturePasswordBean.getCode())) {
                    mChangeGesturePasswordView.finshActivity();
                } else if ("888888".equals(setGesturePasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                mChangeGesturePasswordView.resetGesturePassword();
                ToastUtils.showToast(setGesturePasswordBean.getMessage());
            }
        });

    }
}
