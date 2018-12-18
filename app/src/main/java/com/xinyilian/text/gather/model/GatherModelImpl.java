package com.xinyilian.text.gather.model;

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
