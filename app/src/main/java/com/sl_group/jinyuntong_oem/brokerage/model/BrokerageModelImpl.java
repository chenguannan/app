package com.sl_group.jinyuntong_oem.brokerage.model;

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
 * description：实现佣金业务接口类，数据源
 */
public class BrokerageModelImpl implements BrokerageModel {

    private Activity mActivity;

    public BrokerageModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void brokerage(String intoType, boolean isShowProgress, int curPage, String pageSize, String queryDate, final IBrokerageCallBack brokerageCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.BROKERAGE);
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("intoType", intoType);
        //默认查询全部
        if (queryDate.contains("年")) {
            obj.put("createdDate", "");
        } else {
            obj.put("createdDate", queryDate);
        }
        obj.put("curPage", curPage);
        obj.put("pageSize", pageSize);
        HttpUtils.getInstance().postJson(
                mActivity,
                isShowProgress,
                CommonSet.DOMAIN_URL + URLConstants.BROKERAGE,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        brokerageCallBack.onSuccess(paseData);
                    }

                });
    }
}
