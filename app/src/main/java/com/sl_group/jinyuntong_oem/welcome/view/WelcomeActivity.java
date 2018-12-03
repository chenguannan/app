package com.sl_group.jinyuntong_oem.welcome.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.MainActivity;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.UpDateBean;
import com.sl_group.jinyuntong_oem.login.LoginActivity;
import com.sl_group.jinyuntong_oem.login.gesture.GestureLoginActivity;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.welcome.persenter.WelcomePersenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 马天 on 2018/11/14.
 * description：启动页
 */
public class WelcomeActivity extends BaseActivity implements WelcomeView {
    private long startTime;
    private long endTime;

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData() {
        getUpdateInfo();

        startTime = System.currentTimeMillis();

        String mid = (String) SPUtil.get(this,"mid","");
        if (StringUtils.isEmpty(mid)){
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    startActivity(LoginActivity.class);
                    finish();
                }
            };
            timer.schedule(task, 3000);
            return;
        }
        WelcomePersenter welcomePersenter = new WelcomePersenter(this, this);
        welcomePersenter.merchantInfo();
    }


    @Override
    public void setListener() {
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void getGesturePassword(final String gesturePassword) {
        endTime = System.currentTimeMillis();
        if (endTime-startTime>3000){
            skipGestureLoginActivity(gesturePassword);
        }else {

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    skipGestureLoginActivity(gesturePassword);
                }
            };
            timer.schedule(task, 3000-(endTime-startTime));
        }

    }

    @Override
    public void getGesturePasswordFail() {
        startActivity(LoginActivity.class);
        finish();
    }

    private void skipGestureLoginActivity(String gesturePassword) {
       boolean loginSuccess= (boolean) SPUtil.get(this,"loginSuccess",false);
        if (StringUtils.isEmpty(gesturePassword)){
            if (loginSuccess){
                startActivity(MainActivity.class);
            }else {
                startActivity(LoginActivity.class);
            }
            finish();
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("gesturePassword", gesturePassword);
            startActivity(GestureLoginActivity.class, bundle);
            finish();
        }

    }

    private void getUpdateInfo() {
        //在子线程中获取服务器的数据
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(CommonSet.SERVICE_UPDATE);
                    //建立连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    conn.setRequestMethod("GET");
                    //设置请求超时时间
                    conn.setConnectTimeout(5000);
                    //设置读取超时时间
                    conn.setReadTimeout(5000);
                    //判断是否获取成功
                    if (conn.getResponseCode() == 200) {
                        //获得输入流
                        InputStream is = conn.getInputStream();
                        //解析输入流中的数据
                        // 1.建立通道对象
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        // 2.定义存储空间
                        final byte[] buffer = new byte[1024];
                        // 3.开始读文件
                        int len;
                        try {
                            if (is != null) {
                                while ((len = is.read(buffer)) != -1) {
                                    // 将Buffer中的数据写到outputStream对象中
                                    outputStream.write(buffer, 0, len);
                                }
                            }
                            // 4.关闭流
                            outputStream.close();
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LogUtils.i("更新txt读取结果：" + outputStream.toString());
                        if (StringUtils.isEmpty(outputStream.toString())){
                            return;
                        }
                        UpDateBean upDateBean = new Gson().fromJson(outputStream.toString(), UpDateBean.class);
                        if ("000000".equals(upDateBean.getCode())) {
                            UpDateBean.DataBean dataBean = upDateBean.getData();
                            SPUtil.put(WelcomeActivity.this, "version", dataBean.getVersion());
                            SPUtil.put(WelcomeActivity.this, "changelog", dataBean.getContent());
                            SPUtil.put(WelcomeActivity.this, "versionShort", dataBean.getVerName());
                            SPUtil.put(WelcomeActivity.this, "install_url", dataBean.getUrl());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //启动线程
        thread.start();
    }
}
