package com.sl_group.jinyuntong_oem.extract.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.ExtractBean;
import com.sl_group.jinyuntong_oem.extract.model.ExtractModel;
import com.sl_group.jinyuntong_oem.extract.model.ExtractModelImpl;
import com.sl_group.jinyuntong_oem.extract.view.ExtractView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
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

    public void applyExtact(String extractMoney, String holderName, String accountNumber) {
        if (StringUtils.isEmpty(extractMoney)) {
            ToastUtils.showToast("请输入提现金额");
            return;
        }
        if (Double.parseDouble(extractMoney) < CommonSet.EXTRACT_LIMIT) {
            ToastUtils.showToast("满10元可提");
            return;
        }
        if (StringUtils.isEmpty(holderName) || StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("数据加载失败");
            return;
        }
        mApplyExtractModel.extract(extractMoney, holderName, accountNumber, new ExtractModel.IExtractCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("提现：" + data);
                ExtractBean extractBean = new Gson().fromJson(data, ExtractBean.class);
                if ("000000".equals(extractBean.getCode())) {
                    mExtractView.extractSuccess(extractBean.getData());
                    return;
                } else if ("888888".equals(extractBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(extractBean.getMessage());
            }
        });
    }
}
