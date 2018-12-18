package com.xinyilian.text.realname.model;

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
 * Created by 马天 on 2018/11/16.
 * description：
 */
public class RealnameModelImpl implements RealnameModel {
    private Activity mActivity;

    public RealnameModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void realname(String idcard, String holderName, String accountNumber, String tel,String checkCode ,String uuid, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId, final IRealnameCallBack iRealnameCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REALNAME);
        obj.put("participantId", SPUtil.get(mActivity,"participantId",""));
        obj.put("mid", SPUtil.get(mActivity,"mid",""));
        obj.put("idcard", idcard);
        obj.put("holderName", holderName);
        obj.put("accountNumber", accountNumber);
        obj.put("tel", tel);
        obj.put("cellPhone", tel);
        obj.put("checkCode", checkCode);
        obj.put("uuid", uuid);
        obj.put("legalPersonID1ImageId", "");
        obj.put("legalPersonID2ImageId", "");
        obj.put("bizPlaceSnapshot1ImageId", bizPlaceSnapshot1ImageId);
        obj.put("bizPlaceSnapshot2ImageId", bizPlaceSnapshot2ImageId);
        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL +  URLConstants.REALNAME,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity,"key","")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        iRealnameCallBack.onSuccess(paseData);

                    }

                });
    }
}
