package com.xinyilian.text.system_prop.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.bean.SystemPropBean;
import com.xinyilian.text.system_prop.model.SystemPropModel;
import com.xinyilian.text.system_prop.model.SystemPropModelImpl;
import com.xinyilian.text.system_prop.view.SystemPropView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

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
        mSystemPropModel.systemProp(type, new SystemPropModel.ISystemPropCallBack() {
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
                        case "yaoqingma":
                            mSystemPropView.getYaoQingMaURL(systemPropBean.getData().getYaoqing());
                            break;
                    }
                    return;
                }
                ToastUtils.showToast(systemPropBean.getMessage());
            }
        });
    }
}
