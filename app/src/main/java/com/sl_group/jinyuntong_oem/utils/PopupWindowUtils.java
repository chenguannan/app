package com.sl_group.jinyuntong_oem.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by 马天 on 2018/1/26.
 * description：
 */

public class PopupWindowUtils {

    public static PopupWindow getPop(final Activity activity, View v, int width, int height){
      PopupWindow popupWindow = new PopupWindow(activity);
        popupWindow.setContentView(v);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(activity,1.0f);
            }
        });
        setBackgroundAlpha(activity,0.3f);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        return popupWindow;
    }

    //弹窗背景
    private static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
