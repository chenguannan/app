package com.sl_group.jinyuntong_oem.upload_img.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.UploadImgBean;
import com.sl_group.jinyuntong_oem.upload_img.model.UploadImgModel;
import com.sl_group.jinyuntong_oem.upload_img.model.UploadImgModelImpl;
import com.sl_group.jinyuntong_oem.upload_img.view.UpLoadImgView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class UploadImgPersenter {
    private Activity mActivity;
    private UpLoadImgView mUpLoadImgView;
    private UploadImgModelImpl mUploadImgModel;

    public UploadImgPersenter(Activity activity, UpLoadImgView upLoadImgView) {
        mActivity = activity;
        mUpLoadImgView = upLoadImgView;
        mUploadImgModel = new UploadImgModelImpl(activity);
    }

    public void uploadImg(String file, final int index) {
        mUploadImgModel.uploadImage(file, index, new UploadImgModel.IUpLoadImgCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("上传照片：" + data);
                UploadImgBean uploadImgBean = new Gson().fromJson(data, UploadImgBean.class);
                if ("000000".equals(uploadImgBean.getCode())) {
                    mUpLoadImgView.uploadImgSuccess(uploadImgBean.getData(), index);
                    return;
                } else if ("888888".equals(uploadImgBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(uploadImgBean.getMessage());
            }
        });
    }
}
