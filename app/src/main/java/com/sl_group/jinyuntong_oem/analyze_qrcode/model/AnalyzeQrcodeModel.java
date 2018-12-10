package com.sl_group.jinyuntong_oem.analyze_qrcode.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface AnalyzeQrcodeModel {
    void analyzeQrcode(String qrcode,AnalyzeQrcodeModel.IAnalyzeQrcodeCallBack analyzeQrcodeCallBack);

    interface IAnalyzeQrcodeCallBack {
        void onSuccess(String data);
    }
}
