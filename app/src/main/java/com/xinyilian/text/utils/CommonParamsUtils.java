package com.xinyilian.text.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.xinyilian.text.CommonSet;

import org.json.JSONException;

/**
 * Created by 马天 on 2017/11/10.
 * 功能：获取请求参数obj
 */

public class CommonParamsUtils {

    public static JSONObject commonParamsJSONObject(Activity activity) {
        JSONObject obj = new JSONObject();
        obj.put("apiVersion", CommonSet.API_VERSION);
        obj.put("txnDate", String.valueOf(System.currentTimeMillis()));
        obj.put("encryptId", SPUtil.get(activity, "encryptId", "merchantApp"));
        obj.put("mobileMac", MacUtils.getMac(activity));
        return obj;
    }

    public static String commonPaseParams(String paseData) {
        ProgressDialogUtils.dismissProgress();
        if (TextUtils.isEmpty(paseData)) {
            return null;
        }
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(paseData);
            return jsonObject.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }

}
