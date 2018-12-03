package com.sl_group.jinyuntong_oem.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by MT on 2017/5/20.
 * 进度条
 */

public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;


    public static void showProgress(Activity activity) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请稍后...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public static void dismissProgress() {
        if (null == progressDialog) {
            return;
        }

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }
}
