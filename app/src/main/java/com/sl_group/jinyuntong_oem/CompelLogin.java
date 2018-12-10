package com.sl_group.jinyuntong_oem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.sl_group.jinyuntong_oem.login.LoginActivity;
import com.sl_group.jinyuntong_oem.utils.SPUtil;

/**
 * Created by 马天 on 2018/11/26.
 * description：强制退出登录
 */
public class CompelLogin {
    private Activity mActivity;

    public CompelLogin(Activity activity) {
        mActivity = activity;
    }

    public void popExitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        compelExitLogin();
                    }
                })
                .setTitle("温馨提示")
                .setMessage("检测到您更换设备，请使用手机号重新登录！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                });
        builder.create().show();

    }
    private void compelExitLogin() {
        String usertel = (String) SPUtil.get(mActivity, "usertel", "");
        SPUtil.clear(mActivity);
        SPUtil.put(mActivity, "usertel", usertel);
        SPUtil.put(mActivity, "isCompelLogin", true);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCompelLogin", true);
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
