package com.xinyilian.text.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by MT on 2017/3/3.
 * 参数格式转换
 */

public class ParamsFormatUtils {

    public static JSONObject paramsMethod(JSONObject obj, String key) {
        JSONObject content = new JSONObject();
        content.put("content", JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue));
        content.put("key", key);
        String contentStr = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);

        contentStr = contentStr.replaceAll("\\\"", "\\\\\\\"");
        String signStr = "{\"content\":\"" + contentStr + "\",\"key\":\"" + key + "\"}";
        String sign = MD5Utils.shaEncrypt(signStr);
        content.remove("key");
        content.put("sign", sign);
        return content;

    }
}
