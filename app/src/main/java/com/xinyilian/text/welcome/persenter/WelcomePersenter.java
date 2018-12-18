package com.xinyilian.text.welcome.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.welcome.model.WelcomeModel;
import com.xinyilian.text.welcome.model.WelcomeModelImpl;
import com.xinyilian.text.welcome.view.WelcomeView;

/**
 * Created by 马天 on 2018/11/19.
 * description：
 */
public class WelcomePersenter {
    private Activity mActivity;
    private WelcomeView mWelcomeView;
    private WelcomeModelImpl mWelcomeModel;

    public WelcomePersenter(Activity activity, WelcomeView welcomeView) {
        mActivity = activity;
        mWelcomeView = welcomeView;
        mWelcomeModel = new WelcomeModelImpl(activity);
    }

    public void merchantInfo() {
        mWelcomeModel.merchantInfo(new WelcomeModel.IMerchantInfoCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("信息查询：" + data);
                MerchantInfoBean merchantInfoBean = new Gson().fromJson(data, MerchantInfoBean.class);
                if ("000000".equals(merchantInfoBean.getCode())) {
                    MerchantInfoBean.DataBean dataBean = merchantInfoBean.getData();
                    mWelcomeView.getGesturePassword(dataBean.getGesturePassword());
                    saveMerchantInfo(dataBean);
                } else {
                    mWelcomeView.getGesturePasswordFail();
                }
            }
        });
    }

    private void saveMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        SPUtil.put(mActivity, "shortName", dataBean.getShortName());
        SPUtil.put(mActivity, "shopAddress", dataBean.getShopAddress());
        SPUtil.put(mActivity, "merchantName", dataBean.getMerchantName());
        SPUtil.put(mActivity, "mid", dataBean.getMid());
        SPUtil.put(mActivity, "participantId", String.valueOf(dataBean.getParticipantId()));
        SPUtil.put(mActivity, "vipLevel", dataBean.getVipLevel());
        SPUtil.put(mActivity, "agentName", dataBean.getAgentName());

        SPUtil.put(mActivity, "cellPhone", dataBean.getCellPhone());
        SPUtil.put(mActivity, "firstName", dataBean.getFirstName());
        SPUtil.put(mActivity, "agencyId", String.valueOf(dataBean.getAgencyId()));
        SPUtil.put(mActivity, "qualifiedState", dataBean.getQualifiedState());
        SPUtil.put(mActivity, "accountNumber", dataBean.getAccountNumber());
        SPUtil.put(mActivity, "tel", dataBean.getTel());
        SPUtil.put(mActivity, "depositBank", dataBean.getDepositBank());
        SPUtil.put(mActivity, "holderName", dataBean.getHolderName());
        SPUtil.put(mActivity, "idCard", dataBean.getIdCard());
        SPUtil.put(mActivity, "canReceived", dataBean.getCanReceived());
        SPUtil.put(mActivity, "payCode", dataBean.getPayCode());
        SPUtil.put(mActivity, "gesturePassword", dataBean.getGesturePassword());
        SPUtil.put(mActivity, "tradePassword", dataBean.getTradePassword());
        SPUtil.put(mActivity, "headPortraitDirectoryName", dataBean.getHeadPortraitDirectoryName());
        SPUtil.put(mActivity, "headPortraitFilePrefix", dataBean.getHeadPortraitFilePrefix());
        SPUtil.put(mActivity, "title", dataBean.getTitle());
        SPUtil.put(mActivity, "content", dataBean.getContent());
        SPUtil.put(mActivity, "url", dataBean.getUrl());
        SPUtil.put(mActivity, "ruleUrl", dataBean.getRuleUrl());
    }
}
