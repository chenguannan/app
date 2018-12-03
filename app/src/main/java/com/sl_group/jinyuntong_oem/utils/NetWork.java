package com.sl_group.jinyuntong_oem.utils;


import com.sl_group.jinyuntong_oem.base.BaseApplication;

/**
 * Created by 马天 on 2017/12/2.
 * 功能：判断手机网络类型
 */

public class NetWork {
    public static void internal() {
        int networkType = NetWorkUtils.getNetworkType(BaseApplication.getInstance());
        String networkTypeName = NetWorkUtils.getNetworkTypeName(BaseApplication.getInstance());
        LogUtils.d("网络名字：", networkTypeName);
        LogUtils.d("网络类型：", networkType + "");
        switch (networkTypeName) {
            case NetWorkUtils.NETWORK_TYPE_WIFI:
                ToastUtils.showToast("你目前处于wifi网络");
                break;
            case NetWorkUtils.NETWORK_TYPE_DISCONNECT:
                ToastUtils.showToast("你目前处于断网状态");
                break;
            case NetWorkUtils.NETWORK_TYPE_3G:
                ToastUtils.showToast("你目前处于3G状态");
                break;
            case NetWorkUtils.NETWORK_TYPE_2G:
                ToastUtils.showToast("你目前处于2G网络");
                break;
            case NetWorkUtils.NETWORK_TYPE_WAP:
                ToastUtils.showToast("你目前处于企业网");
                break;
            case NetWorkUtils.NETWORK_TYPE_UNKNOWN:
                ToastUtils.showToast("你目前网络类型不知道");
                break;
        }
    }
}
