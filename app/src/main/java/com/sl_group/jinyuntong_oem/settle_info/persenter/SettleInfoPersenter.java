package com.sl_group.jinyuntong_oem.settle_info.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.SetSettleCardBean;
import com.sl_group.jinyuntong_oem.settle_info.model.SettleInfoModel;
import com.sl_group.jinyuntong_oem.settle_info.model.SettleInfoModelImpl;
import com.sl_group.jinyuntong_oem.settle_info.view.SettleInfoView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public class SettleInfoPersenter {
    private Activity mActivity;
    private SettleInfoView mSettleInfoView;
    private SettleInfoModelImpl mSettleInfoModel;

    public SettleInfoPersenter(Activity activity, SettleInfoView settleInfoView) {
        mActivity = activity;
        mSettleInfoView = settleInfoView;
        mSettleInfoModel = new SettleInfoModelImpl(activity);
    }


    public void  setSettleCard(String accountNumber,String tel){
        if (!mSettleInfoModel.checkParams(accountNumber,tel)){
            return;
        }
        mSettleInfoModel.setSettleCard(accountNumber,tel, new SettleInfoModel.ISetSettleCardCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("设置结算卡："+data);
                SetSettleCardBean setSettleCardBean = new Gson().fromJson(data,SetSettleCardBean.class);
                if ("000000".equals(setSettleCardBean.getCode())){
                    mSettleInfoView.openURL(setSettleCardBean.getData());
                    return;
                }else if ("888888".equals(setSettleCardBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(setSettleCardBean.getMessage());
            }
        });
    }
}
