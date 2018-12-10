package com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface ChangeGesturePasswordModel {

    void changeGesturePassword(String cellPhone,String checkCode,String uuid,String gesturePassword, ChangeGesturePasswordModel.changeGesturePasswordCallBack changeGesturePasswordCallBack);

    interface changeGesturePasswordCallBack{
        void onSuccess(String data);
    }
}
