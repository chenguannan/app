package com.xinyilian.text.my_team.model;

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
 * Created by 马天 on 2018/11/25.
 * description：我的团队model实现类
 */
public class MyTeamModelImpl implements MyTeamModel {
    private Activity mActivity;

    public MyTeamModelImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void myTeam(String intoType, boolean isShowProgress, int curPage, String pageSize, final IMyTeamCallBack myTeamCallBack) {
        JSONObject obj = CommonParamsUtils.commonParamsJSONObject(mActivity);
        obj.put("method", URLConstants.MY_TEAM);
        obj.put("mid", SPUtil.get(mActivity, "mid", ""));
        obj.put("participantId", SPUtil.get(mActivity, "participantId", ""));
        //直接推荐的人 11
        obj.put("intoType", intoType);
        obj.put("name", "");
        obj.put("cellPhone", SPUtil.get(mActivity, "cellPhone", ""));
        obj.put("curPage", curPage);
        obj.put("pageSize", pageSize);

        HttpUtils.getInstance().postJson(
                mActivity,
                isShowProgress,
                CommonSet.DOMAIN_URL+URLConstants.MY_TEAM,
                com.alibaba.fastjson.JSON.toJSONString(ParamsFormatUtils.paramsMethod(obj, (String) SPUtil.get(mActivity, "key", "")))
                , new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String serviceData) {
                        String paseData = CommonParamsUtils.commonPaseParams(serviceData);
                        if (StringUtils.isEmpty(paseData)) {
                            ToastUtils.showToast("网络异常");
                            return;
                        }
                        myTeamCallBack.onSuccess(paseData);
                    }

                });
    }
}
