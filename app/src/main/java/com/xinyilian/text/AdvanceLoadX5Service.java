package com.xinyilian.text;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.xinyilian.text.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by 马天 on 2018/12/4.
 * description：
 */
public class AdvanceLoadX5Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initX5();
    }

    private void initX5() {
        //  预加载X5内核
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            LogUtils.i( "onViewInitFinished is " + arg0);
            //初始化完成回调
        }

        @Override
        public void onCoreInitFinished() {
            LogUtils.i( "onCoreInitFinished");
        }
    };

}