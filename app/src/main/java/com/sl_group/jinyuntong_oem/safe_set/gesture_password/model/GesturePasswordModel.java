package com.sl_group.jinyuntong_oem.safe_set.gesture_password.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public interface GesturePasswordModel {
    void clearGesturePassword(GesturePasswordModel.clearGesturePasswordCallBack clearGesturePasswordCallBack);

    interface clearGesturePasswordCallBack{
        void onSuccess(String data);
    }
}
