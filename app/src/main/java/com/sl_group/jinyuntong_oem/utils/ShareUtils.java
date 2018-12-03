package com.sl_group.jinyuntong_oem.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * Created by MT on 2016-12-19.
 * 分享工具类
 */

public class ShareUtils {
    public static UMShareListener getUMShareListener(final Context context) {
        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Toast.makeText(context, share_media + "分享开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(context, share_media + "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable t) {
                Toast.makeText(context, share_media + "分享失败", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.i("throw", "throw:" + t.getMessage());
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(context, share_media + "分享取消", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
