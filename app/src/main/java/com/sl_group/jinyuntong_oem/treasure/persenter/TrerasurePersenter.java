package com.sl_group.jinyuntong_oem.treasure.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.TreasureBean;
import com.sl_group.jinyuntong_oem.treasure.model.TreasureModelImpl;
import com.sl_group.jinyuntong_oem.treasure.model.TreasureModel;
import com.sl_group.jinyuntong_oem.treasure.view.TreasureView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/23.
 * description：财富
 */
public class TrerasurePersenter {
    private Activity mActivity;
    private TreasureView mTreasureView;
    private TreasureModelImpl mTreasureMode;

    public TrerasurePersenter(Activity activity, TreasureView treasureView) {
        mActivity = activity;
        mTreasureView = treasureView;
        mTreasureMode = new TreasureModelImpl(activity);
    }

    public void treasure(){
        mTreasureMode.treasure(new TreasureModel.ITreasureCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("财富："+data);
                TreasureBean treasureBean = new Gson().fromJson(data,TreasureBean.class);
                if ("000000".equals(treasureBean.getCode())){
                    mTreasureView.treasureSuccess(treasureBean.getData());
                    return;
                }else if ("888888".equals(treasureBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(treasureBean.getMessage());
            }
        });
    }
}
