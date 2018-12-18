package com.xinyilian.text.upload_img.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.UploadImgBean;
import com.xinyilian.text.upload_img.model.UploadImgModel;
import com.xinyilian.text.upload_img.model.UploadImgModelImpl;
import com.xinyilian.text.upload_img.view.UpLoadImgView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

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
//                 Intent intent = new Intent();
//                 intent.setAction("panhouye");
//                 intent.putExtra("sele","潘侯爷");
//                 mActivity.sendBroadcast(intent);
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
