package com.sl_group.jinyuntong_oem.system_prop.model;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public interface SystemPropModel {


    void systemProp( String type,SystemPropModel.ISystemPropCallBack systemPropCallBack);

    interface ISystemPropCallBack{
        void onSuccess(String data);
    }
}
