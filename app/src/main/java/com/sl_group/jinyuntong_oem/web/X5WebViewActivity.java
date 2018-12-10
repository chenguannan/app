package com.sl_group.jinyuntong_oem.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.X5WebView;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by 马天 on 2018/12/4.
 * description：
 */
public class X5WebViewActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvActionbarClose;

    private X5WebView mX5WebView;
    private ProgressBar mPb;
    private String title;

    @Override
    public int bindLayout() {
        return R.layout.activity_x5_webview;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvActionbarClose = findViewById(R.id.tv_actionbar_close);
        mPb =  findViewById(R.id.pb);
        mX5WebView = findViewById(R.id.x5_webview);

    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("支付");
        initHardwareAccelerate();
        mX5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                mPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mPb.setVisibility(View.GONE);
            }

            /**
             * 防止加载网页时调起系统浏览器
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString("url", "");
            title = bundle.getString("title", "");
            mX5WebView.loadUrl(url);
        }

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvActionbarClose.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_actionbar_close:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (mX5WebView != null)
            mX5WebView.destroy();
        if (!StringUtils.isEmpty(title)&&title.contains("升级")){
            //提现成功后，发送广播，刷新财富页面数据
            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("data","VIPrefresh");
            LocalBroadcastManager.getInstance(X5WebViewActivity.this).sendBroadcast(intent);
            sendBroadcast(intent);
        }

        super.onDestroy();
    }
}
