package com.xinyilian.text.gesture_change.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：修改手势密码
 */
public interface GesturePasswordChangeModel {
    /**
     * @param cellPhone                     手机号
     * @param checkCode                     验证码
     * @param uuid                          验证码UUID
     * @param gesturePassword               手势面膜
     * @param changeGesturePasswordCallBack 修改手势密码回调
     */
    void changeGesturePassword(String cellPhone, String checkCode, String uuid, String gesturePassword, GesturePasswordChangeModel.changeGesturePasswordCallBack changeGesturePasswordCallBack);

    interface changeGesturePasswordCallBack {
        void onSuccess(String data);
    }
}
