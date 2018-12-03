package com.sl_group.jinyuntong_oem.brokerage.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.DealRecordBean;
import com.sl_group.jinyuntong_oem.bean.ExtractRecordBean;
import com.sl_group.jinyuntong_oem.bean.UpVipBrokerageBean;
import com.sl_group.jinyuntong_oem.brokerage.model.BrokerageModel;
import com.sl_group.jinyuntong_oem.brokerage.model.BrokerageModelImpl;
import com.sl_group.jinyuntong_oem.brokerage.view.BrokerageView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/24.
 * description：佣金逻辑层
 */
public class BrokeragePersenter {
    private Activity mActivity;
    private BrokerageView mBrokerageView;
    private BrokerageModelImpl mExtractRecordModel;

    public BrokeragePersenter(Activity activity, BrokerageView brokerageView) {
        mActivity = activity;
        mBrokerageView = brokerageView;
        mExtractRecordModel = new BrokerageModelImpl(activity);
    }

    /**
     * 查询佣金
     *
     * @param intoType       佣金类型
     * @param isShowProgress 是否显示加载进度条
     * @param curPage        当前页码
     * @param pageSize       每页条数
     * @param queryDate      查询日期
     */
    public void brokerage(final String intoType, boolean isShowProgress, int curPage, String pageSize, String queryDate) {
        mExtractRecordModel.brokerage(intoType, isShowProgress, curPage, pageSize, queryDate, new BrokerageModel.IBrokerageCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("佣金：：" + data);
                switch (intoType) {
                    //提现
                    case CommonSet.INTOTYPE_EXTRACT:
                        ExtractRecordBean extractRecordBean = new Gson().fromJson(data, ExtractRecordBean.class);
                        if ("000000".equals(extractRecordBean.getCode())) {
                            mBrokerageView.extractRecord(extractRecordBean.getData());
                            return;
                        } else if ("888888".equals(extractRecordBean.getCode())) {
                            new CompelLogin(mActivity).popExitLogin();
                            return;
                        }
                        ToastUtils.showToast(extractRecordBean.getMessage());
                        break;
                    //交易
                    case CommonSet.INTOTYPE_DEAL:
                        DealRecordBean dealRecordBean = new Gson().fromJson(data, DealRecordBean.class);
                        if ("000000".equals(dealRecordBean.getCode())) {
                            mBrokerageView.dealRecord(dealRecordBean.getData());
                            return;
                        } else if ("888888".equals(dealRecordBean.getCode())) {
                            new CompelLogin(mActivity).popExitLogin();
                            return;
                        }
                        ToastUtils.showToast(dealRecordBean.getMessage());
                        break;
                    //升级VIP
                    case CommonSet.INTOTYPE_UP_VIP:
                        UpVipBrokerageBean upVipBrokerageBean = new Gson().fromJson(data, UpVipBrokerageBean.class);
                        if ("000000".equals(upVipBrokerageBean.getCode())) {
                            mBrokerageView.upVipBrokerage(upVipBrokerageBean.getData());
                            return;
                        } else if ("888888".equals(upVipBrokerageBean.getCode())) {
                            new CompelLogin(mActivity).popExitLogin();
                            return;
                        }
                        ToastUtils.showToast(upVipBrokerageBean.getMessage());
                        break;
                }

            }
        });
    }
}
