package com.sl_group.jinyuntong_oem.gather.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.PayCodeMoneyBean;
import com.sl_group.jinyuntong_oem.gather.model.GatherModel;
import com.sl_group.jinyuntong_oem.gather.model.GatherModelImpl;
import com.sl_group.jinyuntong_oem.gather.view.GatherView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class GatherPersenter {
    private Activity mActivity;
    private GatherView mGatherView;
    private GatherModelImpl mGatherModel;

    public GatherPersenter(Activity activity, GatherView gatherView) {
        mActivity = activity;
        mGatherView = gatherView;
        mGatherModel = new GatherModelImpl(activity);
    }


    public void gatherWithMoney(String gatherMoney){
        mGatherModel.gatherWithMoney(gatherMoney, new GatherModel.IGatherWithMoneyCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("带金额收款二维码信息查询："+data);
                PayCodeMoneyBean payCodeMoneyBean = new Gson().fromJson(data,PayCodeMoneyBean.class);
                if ("000000".equals(payCodeMoneyBean.getCode())){
                    mGatherView.gatherWithMoneySuccess(payCodeMoneyBean.getData().getUuid());
                    return;
                }else if ("888888".equals(payCodeMoneyBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(payCodeMoneyBean.getMessage());
            }
        });
    }
}
