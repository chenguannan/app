package com.sl_group.jinyuntong_oem.brokerage_details.model;

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
 * Created by 马天 on 2018/11/24.
 * description：佣金详情业务接口实现类，数据源
 */
public class BrokerageDetailsModelImpl implements BrokerageDetailsModel{
    private Activity mActivity;

    public BrokerageDetailsModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void brokerageDetails(String intoType, String logId, final IBrokerageDetailsCallBack brokerageDetailsCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.BROKERAGE_DETSILS);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("intoType", intoType);
        obj.put("logId", logId);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.BROKERAGE_DETSILS,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        brokerageDetailsCallBack.onSuccess(paseData);
                    }

                });
    }
}
