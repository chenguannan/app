package com.sl_group.jinyuntong_oem.utils;

import android.widget.Toast;

import com.sl_group.jinyuntong_oem.base.BaseApplication;


/**
 * Created by MT on 2016-12-16.
 * 吐司
 */

public class ToastUtils {
    private static Toast toast;
    public static void showToast(String message){
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);//如果不为空，则直接改变当前toast的文本
        }
        toast.show();
    }

}
