package com.xinyilian.text.brokerage_details.model;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.URLConstants;
import com.xinyilian.text.utils.CommonParamsUtils;
import com.xinyilian.text.utils.HttpUtils;
import com.xinyilian.text.utils.ParamsFormatUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

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
