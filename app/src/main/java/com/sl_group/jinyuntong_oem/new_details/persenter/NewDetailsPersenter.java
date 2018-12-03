package com.sl_group.jinyuntong_oem.new_details.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.NewDetailsBean;
import com.sl_group.jinyuntong_oem.new_details.model.NewDetailsModel;
import com.sl_group.jinyuntong_oem.new_details.model.NewDetailsModelImpl;
import com.sl_group.jinyuntong_oem.new_details.view.NewDetailsView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class NewDetailsPersenter {
    private Activity mActivity;
    private NewDetailsView mNewDetailsView;
    private NewDetailsModelImpl mNewDetailsModel;

    public NewDetailsPersenter(Activity activity, NewDetailsView newDetailsView) {
        mActivity = activity;
        mNewDetailsView = newDetailsView;
        mNewDetailsModel = new NewDetailsModelImpl(activity);
    }

    public void newDetails(String messageId, String isReady){
        mNewDetailsModel.newDetails(messageId, isReady, new NewDetailsModel.INewDetailsCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("消息详情："+data);
                NewDetailsBean newDetailsBean = new Gson().fromJson(data,NewDetailsBean.class);
                if ("000000".equals(newDetailsBean.getCode())){
                    mNewDetailsView.getNewDetails(newDetailsBean.getData());
                    return;
                }else if ("888888".equals(newDetailsBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(newDetailsBean.getMessage());
            }
        });
    }
}
