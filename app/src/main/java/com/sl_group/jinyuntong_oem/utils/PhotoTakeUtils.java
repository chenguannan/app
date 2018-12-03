package com.sl_group.jinyuntong_oem.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 拍照图片处理类
 */
public class PhotoTakeUtils {
    public PhotoTakeUtils() {}

    public static Bitmap decodeSampledBitmapFromFile(String filepath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * 计算压缩比例值
     *
     * 原版2>4>8...倍压缩
     * 当前2>3>4...倍压缩
     *
     * @param options  解析图片的配置信息
     * @param reqWidth 所需图片压缩尺寸最小宽度O
     * @param reqHeight 所需图片压缩尺寸最小高度
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int picheight = options.outHeight;
        final int picwidth = options.outWidth;
//        LogUtils.i( "原尺寸:" +  picwidth + "*" +picheight);

        int targetheight = picheight;
        int targetwidth = picwidth;
        int inSampleSize = 1;

        if (targetheight > reqHeight || targetwidth > reqWidth) {
            while (targetheight  >= reqHeight
                    && targetwidth>= reqWidth) {
//                LogUtils.LogI("压缩:" +inSampleSize + "倍");
                inSampleSize += 1;
                targetheight = picheight/inSampleSize;
                targetwidth = picwidth/inSampleSize;
            }
        }

//        LogUtils.i("最终压缩比例:" +inSampleSize + "倍");
//        LogUtils.i( "新尺寸:" +  targetwidth + "*" +targetheight);
        return inSampleSize;
    }
}
