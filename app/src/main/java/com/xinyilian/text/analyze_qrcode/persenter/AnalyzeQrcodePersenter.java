package com.xinyilian.text.analyze_qrcode.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.AnalyzeQrcodeBean;
import com.xinyilian.text.analyze_qrcode.model.AnalyzeQrcodeModel;
import com.xinyilian.text.analyze_qrcode.model.AnalyzeQrcodeModelImpl;
import com.xinyilian.text.analyze_qrcode.view.AnalyzeQrcodeView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class AnalyzeQrcodePersenter {
    private Activity mActivity;
    private AnalyzeQrcodeView mAnalyzeQrcodeView;
    private AnalyzeQrcodeModelImpl mFirstpageModel;

    public AnalyzeQrcodePersenter(Activity activity, AnalyzeQrcodeView analyzeQrcodeView) {
        mActivity = activity;
        mAnalyzeQrcodeView = analyzeQrcodeView;
        mFirstpageModel = new AnalyzeQrcodeModelImpl(activity);
    }

    public void analyzeQrcode(String qrcode){
        mFirstpageModel.analyzeQrcode(qrcode, new AnalyzeQrcodeModel.IAnalyzeQrcodeCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("解析二维码："+data);
                AnalyzeQrcodeBean analyzeQrcodeBean = new Gson().fromJson(data,AnalyzeQrcodeBean.class);
                if ("000000".equals(analyzeQrcodeBean.getCode())){
                    mAnalyzeQrcodeView.analyzeQrcodeSuccess(analyzeQrcodeBean.getData());
                    return;
                }else if ("888888".equals(analyzeQrcodeBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(analyzeQrcodeBean.getMessage());
            }
        });

    }
}
