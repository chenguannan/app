package com.sl_group.jinyuntong_oem.pay_bill.model;

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
public class PayBillModelImpl implements PayBillModel {
    private Activity mActivity;

    public PayBillModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void getPayBill(boolean isShowPeogress, int curPage, String pageSize, String beginDate, String endDate, final IPayBillCallBack payBillCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.PAY_BILL);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId", SPUtil.get(mActivity, "agencyId",CommonSet.AGENCY_ID));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("curPage", curPage);
        obj.put("pageSize", pageSize);
        if (beginDate.contains("年")){
            obj.put("beginDate", "");
        }else {
            obj.put("beginDate", beginDate);
        }
        if (endDate.contains("年")){
            obj.put("endDate", "");
        }else {
            obj.put("endDate", endDate);
        }

        HttpUtils.getInstance().postJson(
                mActivity,
                isShowPeogress,
                CommonSet.DOMAIN_URL + URLConstants.PAY_BILL,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        payBillCallBack.onSuccess(paseData);
                    }

                });
    }
}
