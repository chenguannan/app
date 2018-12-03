package com.sl_group.jinyuntong_oem.realname.model;

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
 * Created by 马天 on 2018/11/16.
 * description：
 */
public class RealnameModelImpl implements RealnameModel {
    private Activity mActivity;

    public RealnameModelImpl(Activity activity) {
        mActivity = activity;
    }


    @Override
    public boolean checkRealNameParams(String idcard, String holderName, String accountNumber, String tel, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId) {
        if (StringUtils.isEmpty(idcard)||StringUtils.isEmpty(holderName)){
            ToastUtils.showToast("人脸识别获取数据异常");
            return false;
        }
        if (StringUtils.isEmpty(accountNumber)){
            ToastUtils.showToast("请输入储蓄卡号");
            return false;
        }
        if (StringUtils.isEmpty(tel)){
            ToastUtils.showToast("请输入银行预留手机号");
            return false;
        }
        if (tel.length()!=11){
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return false;
        }
        if (StringUtils.isEmpty(bizPlaceSnapshot1ImageId)){
            ToastUtils.showToast("请上传手持身份证照片");
            return false;
        }
        if (StringUtils.isEmpty(bizPlaceSnapshot2ImageId)){
            ToastUtils.showToast("请上传银行卡正面照片");
            return false;
        }
        return true;
    }


    @Override
    public void realname(String idcard, String holderName, String accountNumber, String tel, String bizPlaceSnapshot1ImageId, String bizPlaceSnapshot2ImageId, final IRealnameCallBack iRealnameCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.REALNAME);
        obj.put("participantId", SPUtil.get(mActivity,"participantId",""));
        obj.put("mid", SPUtil.get(mActivity,"mid",""));
        obj.put("idcard", idcard);
        obj.put("holderName", holderName);
        obj.put("accountNumber", accountNumber);
        obj.put("tel", tel);
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
