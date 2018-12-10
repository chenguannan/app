package com.sl_group.jinyuntong_oem.settle_info.model;

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
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class SettleInfoModelImpl implements SettleInfoModel {
    private Activity mActivity;

    public SettleInfoModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public boolean checkParams(String accountNumber, String tel) {
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入储蓄卡卡号");
            return false;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return false;
        }
        if (tel.length()!=11) {
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return false;
        }
        return true;
    }

    @Override
    public void setSettleCard(String accountNumber, String tel, final ISetSettleCardCallBack setSettleCardCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.SET_SETTLE_CARD);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("accountNumber", accountNumber);
        obj.put("tel", tel);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.SET_SETTLE_CARD,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        setSettleCardCallBack.onSuccess(paseData);
                    }

                });
    }
}
