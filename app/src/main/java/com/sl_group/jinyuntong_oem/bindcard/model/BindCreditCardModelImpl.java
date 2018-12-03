package com.sl_group.jinyuntong_oem.bindcard.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.URLConstants;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/15.
 * description：绑定信用卡实现类，数据源
 */
public class BindCreditCardModelImpl implements BindCreditCardModel {
    private Activity mActivity;

    public BindCreditCardModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean verficBindCreditCardData(String accountNumber, String bankcardTel) {
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入银行卡卡号或扫描银行卡");
            return false;
        }
        if (StringUtils.isEmpty(bankcardTel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return false;
        }
        if (bankcardTel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return false;
        }

        return true;
    }


    @Override
    public void bindCreditCard(String accountNumber, String bankcardTel, final IBindCreditCardCallBack bindCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("accountNumber", accountNumber);
        obj.put("method", URLConstants.BIND_BANKCARD);
        obj.put("tel", bankcardTel);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.BIND_BANKCARD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        bindCallBack.onSuccess(paseData);
                    }

                });
    }
}
