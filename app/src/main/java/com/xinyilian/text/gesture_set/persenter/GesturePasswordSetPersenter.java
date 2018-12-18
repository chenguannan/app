package com.xinyilian.text.gesture_set.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.SetGesturePasswordBean;
import com.xinyilian.text.gesture_set.model.GesturePasswordSetModel;
import com.xinyilian.text.gesture_set.model.GesturePasswordSetModelImpl;
import com.xinyilian.text.gesture_set.view.GesturePasswordSetView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：手势密码
 */
public class GesturePasswordSetPersenter {
    private Activity mActivity;
    private GesturePasswordSetView mGesturePasswordSetView;
    private GesturePasswordSetModelImpl mSetGesturePasswordModel;

    public GesturePasswordSetPersenter(Activity activity, GesturePasswordSetView gesturePasswordSetView) {
        mActivity = activity;
        mGesturePasswordSetView = gesturePasswordSetView;
        mSetGesturePasswordModel = new GesturePasswordSetModelImpl(activity);
    }

    public void gesturePasswordSet(String cellPhone, String checkCode, String uuid, String gesturePassword) {
        mSetGesturePasswordModel.gesturePasswordSet(cellPhone, checkCode, uuid, gesturePassword, new GesturePasswordSetModel.IGesturePasswordSetCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置手势密码：" + data);
                SetGesturePasswordBean setGesturePasswordBean = new Gson().fromJson(data, SetGesturePasswordBean.class);
                if ("000000".equals(setGesturePasswordBean.getCode())) {
                    mGesturePasswordSetView.gesturePasswordSetSuccess();
                    ToastUtils.showToast(setGesturePasswordBean.getMessage());
                } else if ("888888".equals(setGesturePasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                } else {
                    mGesturePasswordSetView.gesturePasswordSetFail();
                    ToastUtils.showToast(setGesturePasswordBean.getMessage());
                }

            }
        });

    }
}
