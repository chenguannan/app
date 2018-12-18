package com.xinyilian.text.system_prop.model;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public interface SystemPropModel {

    /**
      * 系统链接
      * @param type 链接类型
      * @param systemPropCallBack 回调
      */
    void systemProp( String type,SystemPropModel.ISystemPropCallBack systemPropCallBack);

    interface ISystemPropCallBack{
        void onSuccess(String data);
    }
}
