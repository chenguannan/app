package com.sl_group.jinyuntong_oem.gesture_set.model;

/**
 * Created by 马天 on 2018/11/18.
 * description：设置手势密码
 */
public interface GesturePasswordSetModel {

    /**
     * @param cellPhone                   手机号
     * @param checkCode                   短信验证码
     * @param uuid                        验证码UUID
     * @param gesturePassword             手势密码
     * @param iGesturePasswordSetCallBack 成功回调
     */
    void gesturePasswordSet(String cellPhone, String checkCode, String uuid, String gesturePassword, IGesturePasswordSetCallBack iGesturePasswordSetCallBack);

    interface IGesturePasswordSetCallBack {
        void onSuccess(String data);
    }
}
