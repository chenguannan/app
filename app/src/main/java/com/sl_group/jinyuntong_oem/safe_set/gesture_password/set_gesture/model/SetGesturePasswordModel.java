package com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface SetGesturePasswordModel {

    void setGesturePassword(String gesturePassword,SetGesturePasswordModel.setGesturePasswordCallBack setGesturePasswordCallBack);

    interface setGesturePasswordCallBack{
        void onSuccess(String data);
    }
}
