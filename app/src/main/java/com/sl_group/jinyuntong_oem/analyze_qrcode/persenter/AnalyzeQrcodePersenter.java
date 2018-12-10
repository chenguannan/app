package com.sl_group.jinyuntong_oem.analyze_qrcode.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.AnalyzeQrcodeBean;
import com.sl_group.jinyuntong_oem.analyze_qrcode.model.AnalyzeQrcodeModel;
import com.sl_group.jinyuntong_oem.analyze_qrcode.model.AnalyzeQrcodeModelImpl;
import com.sl_group.jinyuntong_oem.analyze_qrcode.view.AnalyzeQrcodeView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

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
