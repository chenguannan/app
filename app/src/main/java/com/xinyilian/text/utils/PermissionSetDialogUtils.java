package com.xinyilian.text.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.xinyilian.text.R;

import static com.xinyilian.text.utils.AppUtils.getPackageName;


/**
 * Created by 马天 on 2017/12/22.
 * description：设置权限
 */

public class PermissionSetDialogUtils {
    /**
     * 检查是否拥有指定的所有权限
     */
    public  static boolean checkPermissionAllGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    public static void showSetPermission(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("温馨提示")
                .setCancelable(false)
                .setMessage("请在权限管理中打开权限，以免影响正常使用！")
                .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + getPackageName(activity)));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }
}
