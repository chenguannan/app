package com.sl_group.jinyuntong_oem.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by MT on 2017/3/4.
 * 打开相机
 */

public class CameraUtils {

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.sl_group.jinyuntong_oem.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
