package com.xinyilian.text.upload_img.model;

import android.app.Activity;

import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.ProgressDialogUtils;
import com.xinyilian.text.utils.SPUtil;

import java.io.File;

/**
 * Created by 马天 on 2018/11/22.
 * description：
 */
public class UploadImgModelImpl implements UploadImgModel {
    private Activity mActivity;

    public UploadImgModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void uploadImage(String file, int index, final IUpLoadImgCallBack upLoadImgCallBack) {
        HttpUtils.getInstance().uploadMerchantImage(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.UPLOAD_IMG,
                (String) SPUtil.get(mActivity, "mid", ""),
                new File(file),
                new HttpUtils.HttpCallback() {

                    @Override
                    public void onSuccess(String data) {
                        ProgressDialogUtils.dismissProgress();
                        upLoadImgCallBack.onSuccess(data);
                    }

                });
    }
}
