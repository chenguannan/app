package com.xinyilian.text.brokerage_details.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.DealBrokerageDetailsBean;
import com.xinyilian.text.bean.ExtractBrokerageDetailsBean;
import com.xinyilian.text.brokerage_details.model.BrokerageDetailsModel;
import com.xinyilian.text.brokerage_details.model.BrokerageDetailsModelImpl;
import com.xinyilian.text.brokerage_details.view.BrokerageDetailsView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金详情逻辑
 */
public class BrokerageDetailsPersenter {

    private Activity mActivity;
    private BrokerageDetailsView mBrokerageDetailsView;
    private BrokerageDetailsModelImpl mBrokerageDetailsModel;

    public BrokerageDetailsPersenter(Activity activity, BrokerageDetailsView brokerageDetailsView) {
        mActivity = activity;
        mBrokerageDetailsView = brokerageDetailsView;
        mBrokerageDetailsModel = new BrokerageDetailsModelImpl(activity);
    }

    /**
     * 佣金详情
     *
     * @param intoType 佣金类型
     * @param logId    记录日志ID
     */
    public void brokerageDetails(final String intoType, String logId) {
        mBrokerageDetailsModel.brokerageDetails(intoType, logId, new BrokerageDetailsModel.IBrokerageDetailsCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("佣金详情：" + data);
                switch (intoType) {
                    //提现
                    case CommonSet.INTOTYPE_EXTRACT:
                        ExtractBrokerageDetailsBean extractBrokerageDetailsBean = new Gson().fromJson(data, ExtractBrokerageDetailsBean.class);
                        if ("000000".equals(extractBrokerageDetailsBean.getCode())) {
                            mBrokerageDetailsView.brokerageDetails(extractBrokerageDetailsBean.getData());
                            return;
                        } else if ("888888".equals(extractBrokerageDetailsBean.getCode())) {
                            new CompelLogin(mActivity).popExitLogin();
                            return;
                        }
                        ToastUtils.showToast(extractBrokerageDetailsBean.getMessage());
                        break;
                    //交易
                    case CommonSet.INTOTYPE_DEAL:
                        DealBrokerageDetailsBean dealBrokerageDetailsBean = new Gson().fromJson(data, DealBrokerageDetailsBean.class);
                        if ("000000".equals(dealBrokerageDetailsBean.getCode())) {
                            mBrokerageDetailsView.dealDetails(dealBrokerageDetailsBean.getData());
                            return;
                        } else if ("888888".equals(dealBrokerageDetailsBean.getCode())) {
                            new CompelLogin(mActivity).popExitLogin();
                            return;
                        }
                        ToastUtils.showToast(dealBrokerageDetailsBean.getMessage());
                        break;
                }

            }
        });
    }
}
