package com.sl_group.jinyuntong_oem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.bean.AutoRealnameBean;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.realname.view.RealnameActivity;
import com.sl_group.jinyuntong_oem.utils.CommonParamsUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.HttpUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ParamsFormatUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

import java.util.Date;

/**
 * Created by 马天 on 2018/11/15.
 * description：实名认证状态
 */
public class RealnameStates {
    private Activity mActivity;
    private String qualifiedState;
    private static boolean isReal = false;
    public RealnameStates(Activity activity) {
        this.mActivity = activity;
    }

    public boolean isRealname() {
        qualifiedState = (String) SPUtil.get(mActivity, "qualifiedState", "");
        if (StringUtils.isEmpty(qualifiedState)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(mActivity.getString(R.string.warm_prompt))
                    .setCancelable(false)
                    .setMessage("网络异常，请检查网络")
                    .setPositiveButton(mActivity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mActivity.finish();
                        }
                    });
            builder.create().show();
            return isReal;
        }
        return "Y".equals(qualifiedState) || searchMerchantInfoMethod();


    }

    /**
     * 用户信息查询接口
     * method
     */
    private boolean searchMerchantInfoMethod() {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);

        obj.put("method",  URLConstants.MERCHANT_INFO);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));

        HttpUtils.getInstance().postJson(
                mActivity,
                true,
                CommonSet.DOMAIN_URL + URLConstants.MERCHANT_INFO,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", ""))),
                new HttpUtils.HttpCallback() {

            @Override
            public void onSuccess(String serviceData) {
                String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                if (StringUtils.isEmpty(paseData)) {
                    ToastUtils.showToast("网络异常");
                    return;
                }
                LogUtils.i("实名状态：" + paseData);
                MerchantInfoBean merchantInfoBean = new Gson().fromJson(paseData, MerchantInfoBean.class);
                if ("000000".equals(merchantInfoBean.getCode())) {
                    MerchantInfoBean.DataBean dataBean = merchantInfoBean.getData();
                    qualifiedState = dataBean.getQualifiedState();
                    String accountNumber = dataBean.getAccountNumber();
                    qualifiedState = dataBean.getQualifiedState();
                    SPUtil.put(mActivity, "qualifiedState", qualifiedState);
                    switch (qualifiedState) {
                        case "Y":
                            isReal = true;
                            checkResult("Y", accountNumber);
                            break;
                        case "N":
                            isReal = false;
                            checkResult("N", accountNumber);
                            break;
                        case "a":
                            isReal = false;
                            checkResult("a", accountNumber);
                            break;
                    }

                }
            }

        });
        return isReal;
    }
    private void checkResult(final String qualifiedState, final String accountNumber) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_realname, null);
        TextView tvContent =  view.findViewById(R.id.tv_pop_realname_content);
        Button btnLeft = view.findViewById(R.id.btn_pop_realname_cancel);
        Button btnRight = view.findViewById(R.id.btn_pop_realname_sure);
        switch (qualifiedState) {
            //审核通过
            case "Y":
                tvContent.setText("审核成功：恭喜您资料审核成功，欢迎使用。" );
                btnLeft.setText(mActivity.getString(R.string.cancel));
                btnRight.setText(mActivity.getString(R.string.sure));
                break;
            //审核失败
            case "N":
                tvContent.setText("审核失败：您提交的实名资料审核失败，请重新提交实名资料");
                btnLeft.setText(mActivity.getString(R.string.cancel));
                btnRight.setText("去实名");
                break;
            //审核中
            case "a":
                if (StringUtils.isEmpty(accountNumber)) {
                    tvContent.setText("请先完成实名认证");
                    btnLeft.setText(mActivity.getString(R.string.cancel));
                    btnRight.setText("去实名");
                } else {
                    tvContent.setText("资料已转人工审核，审核结果将以通知的方式推送，请注意接收！\n\n审核时间：9:00–17:00，等待10–30分钟。可点击刷新按钮，获取最新审核结果。");
                    btnLeft.setText(mActivity.getString(R.string.cancel));
                    btnRight.setText(mActivity.getString(R.string.sure));
                }
                break;


        }
        final PopupWindow popupWindow = PopupWindowUtils.getPop(mActivity, view, DisplayUtils.getScreenWidth(mActivity) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("Y".equals(qualifiedState) || "N".equals(qualifiedState)) ) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                } else if ("a".equals(qualifiedState) && StringUtils.isEmpty(accountNumber) ) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                } else if ("a".equals(qualifiedState) && !StringUtils.isEmpty(accountNumber)) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    searchMerchantInfoMethod();
                }

            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("Y".equals(qualifiedState) || ("a".equals(qualifiedState) && !StringUtils.isEmpty(accountNumber)))) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                } else if ("N".equals(qualifiedState) || ("a".equals(qualifiedState) && StringUtils.isEmpty(accountNumber))) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    needRealName();
                }

            }
        });


    }

    private void needRealName() {

        String id = "jyt_" + new Date().getTime();
        AuthBuilder mAuthBuilder = new AuthBuilder(id, CommonSet.authKey, CommonSet.urlNotify + SPUtil.get(mActivity, "mid", ""), new OnResultListener() {
            @Override
            public void onResult(String s) {
                LogUtils.i("活体认证结果：" + s);
                AutoRealnameBean autoRealnameBean = new Gson().fromJson(s, AutoRealnameBean.class);
                if (!"000000".equals(autoRealnameBean.getRet_code())) {
                    ToastUtils.showToast(autoRealnameBean.getRet_msg());
                    return;
                }
                Intent intent = new Intent(mActivity, RealnameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_name", autoRealnameBean.getId_name());
                bundle.putString("id_no", autoRealnameBean.getId_no());
                bundle.putString("start_card", autoRealnameBean.getStart_card());
                bundle.putString("addr_card", autoRealnameBean.getAddr_card());
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
        //下文调用方法做为范例，请以对接文档中的调用方法为准
        mAuthBuilder.faceAuth(mActivity);

    }
}
