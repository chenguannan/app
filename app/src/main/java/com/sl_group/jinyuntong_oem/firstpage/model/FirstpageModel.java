package com.sl_group.jinyuntong_oem.firstpage.model;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public interface FirstpageModel {
    void analyzeQrcode(String qrcode,FirstpageModel.IAnalyzeQrcodeCallBack analyzeQrcodeCallBack);

    interface IAnalyzeQrcodeCallBack {
        void onSuccess(String data);
    }
}
