package com.sl_group.jinyuntong_oem.extract.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.ExtractBean;
import com.sl_group.jinyuntong_oem.extract.model.ExtractModel;
import com.sl_group.jinyuntong_oem.extract.model.ExtractModelImpl;
import com.sl_group.jinyuntong_oem.extract.view.ExtractView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/24.
 * description：
 */
public class ExtractPersenter {
    private Activity mActivity;
    private ExtractView mExtractView;
    private ExtractModelImpl mApplyExtractModel;

    public ExtractPersenter(Activity activity, ExtractView extractView) {
        mActivity = activity;
        mExtractView = extractView;
        mApplyExtractModel = new ExtractModelImpl(activity);
    }

    public void applyExtact(String money,String holdName,String accountNumber){
        if (!mApplyExtractModel.checkParams(money,holdName,accountNumber)){
            return;
        }
        mApplyExtractModel.extract(money, holdName, accountNumber, new ExtractModel.IApplyExtractCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("申请提现："+data);
                ExtractBean extractBean = new Gson().fromJson(data,ExtractBean.class);
                if ("000000".equals(extractBean.getCode())){
                    mExtractView.extractSuccess(extractBean.getData());
                    return;
                }else if ("888888".equals(extractBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(extractBean.getMessage());
            }
        });
    }
}
