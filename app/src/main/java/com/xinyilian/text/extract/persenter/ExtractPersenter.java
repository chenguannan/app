package com.xinyilian.text.extract.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.ExtractBean;
import com.xinyilian.text.extract.model.ExtractModel;
import com.xinyilian.text.extract.model.ExtractModelImpl;
import com.xinyilian.text.extract.view.ExtractView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

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
