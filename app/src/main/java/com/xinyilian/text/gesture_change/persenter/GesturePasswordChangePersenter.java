package com.xinyilian.text.gesture_change.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.SetGesturePasswordBean;
import com.xinyilian.text.gesture_change.model.GesturePasswordChangeModel;
import com.xinyilian.text.gesture_change.model.GesturePasswordChangeModelImpl;
import com.xinyilian.text.gesture_change.view.GesturePasswordChangeView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：修改手势密码
 */
public class GesturePasswordChangePersenter {
    private Activity mActivity;
    private GesturePasswordChangeView mGesturePasswordChangeView;
    private GesturePasswordChangeModelImpl mChangeGesturePasswordModel;

    public GesturePasswordChangePersenter(Activity activity, GesturePasswordChangeView gesturePasswordChangeView) {
        mActivity = activity;
        mGesturePasswordChangeView = gesturePasswordChangeView;
        mChangeGesturePasswordModel = new GesturePasswordChangeModelImpl(activity);
    }

    public void setGesturePassword(String cellPhone, String checkCode, String uuid, String gesturePassword) {
        mChangeGesturePasswordModel.changeGesturePassword(cellPhone, checkCode, uuid, gesturePassword, new GesturePasswordChangeModel.changeGesturePasswordCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("修改手势密码：" + data);
                SetGesturePasswordBean setGesturePasswordBean = new Gson().fromJson(data, SetGesturePasswordBean.class);
                if ("000000".equals(setGesturePasswordBean.getCode())) {
                    mGesturePasswordChangeView.gesturePasswordChangeSuccess();
                    ToastUtils.showToast(setGesturePasswordBean.getMessage());
                } else if ("888888".equals(setGesturePasswordBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();

                } else {
                    mGesturePasswordChangeView.gesturePasswordChangeFail();
                    ToastUtils.showToast(setGesturePasswordBean.getMessage());
                }

            }
        });

    }
}
