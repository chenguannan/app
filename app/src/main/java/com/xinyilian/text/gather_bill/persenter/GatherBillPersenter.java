package com.xinyilian.text.gather_bill.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.GatherBillBean;
import com.xinyilian.text.gather_bill.model.GatherBillModel;
import com.xinyilian.text.gather_bill.model.GatherBillModelImpl;
import com.xinyilian.text.gather_bill.view.GatherBillView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class GatherBillPersenter {
    private Activity mActivity;
    private GatherBillView mGatherBillView;
    private GatherBillModelImpl mGatherBillModel;

    public GatherBillPersenter(Activity activity, GatherBillView gatherBillView) {
        mActivity = activity;
        mGatherBillView = gatherBillView;
        mGatherBillModel = new GatherBillModelImpl(activity);
    }

    public void getGatherBill(boolean isShowPeogress,int curPage, String pageSize, String beginDate, String endDate){
        mGatherBillModel.gatherBill(isShowPeogress,curPage, pageSize, beginDate, endDate, new GatherBillModel.IGatherBillCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("收款账单："+data);
                GatherBillBean gatherBillBean = new Gson().fromJson(data,GatherBillBean.class);
                if ("000000".equals(gatherBillBean.getCode())){
                    mGatherBillView.gatherBillSuccess(gatherBillBean.getData().getResultList());
                    return;
                }else if ("888888".equals(gatherBillBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(gatherBillBean.getMessage());
            }
        });
    }
}
