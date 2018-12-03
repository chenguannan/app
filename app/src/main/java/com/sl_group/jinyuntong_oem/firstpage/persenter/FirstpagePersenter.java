package com.sl_group.jinyuntong_oem.firstpage.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.AnalyzeQrcodeBean;
import com.sl_group.jinyuntong_oem.firstpage.model.FirstpageModel;
import com.sl_group.jinyuntong_oem.firstpage.model.FirstpageModelImpl;
import com.sl_group.jinyuntong_oem.firstpage.view.FirstpageView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/21.
 * description：
 */
public class FirstpagePersenter {
    private Activity mActivity;
    private FirstpageView mFirstpageView;
    private FirstpageModelImpl mFirstpageModel;

    public FirstpagePersenter(Activity activity, FirstpageView firstpageView) {
        mActivity = activity;
        mFirstpageView = firstpageView;
        mFirstpageModel = new FirstpageModelImpl(activity);
    }

    public void analyzeQrcode(String qrcode){
        mFirstpageModel.analyzeQrcode(qrcode, new FirstpageModel.IAnalyzeQrcodeCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("解析二维码："+data);
                AnalyzeQrcodeBean analyzeQrcodeBean = new Gson().fromJson(data,AnalyzeQrcodeBean.class);
                if ("000000".equals(analyzeQrcodeBean.getCode())){
                    AnalyzeQrcodeBean.DataBean dataBean = analyzeQrcodeBean.getData();
                    mFirstpageView.getQrcodeContent(dataBean.getSrcAmt(),dataBean.getShortName(),dataBean.getReceivedMid());
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
