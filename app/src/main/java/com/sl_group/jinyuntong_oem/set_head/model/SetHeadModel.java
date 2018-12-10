package com.sl_group.jinyuntong_oem.set_head.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface SetHeadModel {
    /**
      *
      * @param uuid 上传照片返回UUID
      */
    void setHeadImg(String uuid,SetHeadModel.ISetHeadImgCallBack setHeadImgCallBack);

    interface ISetHeadImgCallBack{
        void onSuccess(String data);
    }
}
