package com.sl_group.jinyuntong_oem.upload_img.model;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public interface UploadImgModel {
    /**
     * @param file 文件名
     *             上传图片
     */
    void uploadImage(String file, int index, UploadImgModel.IUpLoadImgCallBack upLoadImgCallBack);

    /**
     * 实名上传照片回调接口及回调方法
     */
    interface IUpLoadImgCallBack {
        void onSuccess(String data);
    }
}
