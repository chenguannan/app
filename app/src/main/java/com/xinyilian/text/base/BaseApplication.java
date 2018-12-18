package com.xinyilian.text.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xinyilian.text.AdvanceLoadX5Service;
import com.xinyilian.text.CommonSet;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.LinkedList;
import java.util.List;

/**
 * 基础应用
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    private List<Activity> activitys;

    //static 代码段可以防止内存泄露
    static {
        //微信分享
        PlatformConfig.setWeixin(CommonSet.WECHAT_SHARE_KEY, CommonSet.WECHAT_SHARE_VALUE);
        //QQ分享
        PlatformConfig.setQQZone(CommonSet.QQ_SHARE_KEY, CommonSet.QQ_SHARE_VALUE);

        //设置全局的Header构建器  recycleview下拉上拉样式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //                layout.setPrimaryColorsId(R.color.blackColor, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    public BaseApplication() {
        activitys = new LinkedList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UMShareAPI.get(this);

        UMConfigure.init(this,"5bfa300ff1f556c66e000a22","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        preinitX5WebCore();
        //预加载x5内核
        Intent intent = new Intent(this, AdvanceLoadX5Service.class);
        startService(intent);
    }
    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
    }
    /**
     * 单例，返回一个实例
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
