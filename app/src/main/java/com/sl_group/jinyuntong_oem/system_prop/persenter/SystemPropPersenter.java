package com.sl_group.jinyuntong_oem.system_prop.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.SystemPropBean;
import com.sl_group.jinyuntong_oem.system_prop.model.SystemPropModel;
import com.sl_group.jinyuntong_oem.system_prop.model.SystemPropModelImpl;
import com.sl_group.jinyuntong_oem.system_prop.view.SystemPropView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public class SystemPropPersenter {
    private Activity mActivity;
    private SystemPropView mSystemPropView;
    private SystemPropModelImpl mSystemPropModel;

    public SystemPropPersenter(Activity activity, SystemPropView systemPropView) {
        mActivity = activity;
        mSystemPropView = systemPropView;
        mSystemPropModel = new SystemPropModelImpl(activity);
    }

    public void systemProp(final String type) {
        mSystemPropModel.systemProp(type,new SystemPropModel.ISystemPropCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("系统连接：" + data);
                SystemPropBean systemPropBean = new Gson().fromJson(data, SystemPropBean.class);
                if ("000000".equals(systemPropBean.getCode())) {
                    switch (type) {
                        case "kefu":
                            mSystemPropView.getKeFuURL(systemPropBean.getData().getKefu());
                            break;
                        case "xieyi":
                            mSystemPropView.getXieYiURL(systemPropBean.getData().getXieyi());
                            break;
                        case "xinshou":
                            mSystemPropView.getXinShouURL(systemPropBean.getData().getXinshou());
                            break;
                    }
                    return;
                }
                ToastUtils.showToast(systemPropBean.getMessage());
            }
        });
    }
}
