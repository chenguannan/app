package com.sl_group.jinyuntong_oem.gather.model;

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
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class GatherModelImpl implements GatherModel {
    private Activity mActivity;

    public GatherModelImpl(Activity activity) {
        mActivity = activity;
    }

    /**
      *
      * @param gatherMoney 收款金额
      */
    @Override
    public void gatherWithMoney(String gatherMoney, final IGatherWithMoneyCallBack gatherWithMoneyCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.PAY_QRCODE_MONEY);
        obj.put("agencyId",SPUtil.get(mActivity, "agencyId",CommonSet.AGENCY_ID));
        obj.put("mid",SPUtil.get(mActivity, "mid", ""));
        obj.put("srcAmt", gatherMoney);

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.PAY_QRCODE_MONEY,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        gatherWithMoneyCallBack.onSuccess(paseData);
                    }

                });
    }

}
