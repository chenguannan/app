package com.xinyilian.text.gather_bill.model;

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
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class GatherBillModelImpl implements GatherBillModel {
    private Activity mActivity;

    public GatherBillModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void gatherBill(boolean isShowPeogress, int curPage, String pageSize, String beginDate, String endDate, final IGatherBillCallBack gatherBillCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.GATHER_BILL);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("agencyId", SPUtil.get(mActivity, "agencyId", CommonSet.AGENCY_ID));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        obj.put("curPage", curPage);
        obj.put("pageSize", pageSize);
        //开始时间和结束时间默认是空，查询全部
        if (beginDate.contains("年")) {
            obj.put("beginDate", "");
        } else {
            obj.put("beginDate", beginDate);
        }
        if (endDate.contains("年")) {
            obj.put("endDate", "");
        } else {
            obj.put("endDate", endDate);
        }

        HttpUtils.getInstance().postJson(
                mActivity,
                isShowPeogress,
                CommonSet.DOMAIN_URL + URLConstants.GATHER_BILL,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        gatherBillCallBack.onSuccess(paseData);
                    }

                });
    }
}
