package com.sl_group.jinyuntong_oem.open_merchant.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.OpenMerchantBean;
import com.sl_group.jinyuntong_oem.open_merchant.model.OpenMerchantMedel;
import com.sl_group.jinyuntong_oem.open_merchant.model.OpenMerchantModelImpl;
import com.sl_group.jinyuntong_oem.open_merchant.view.OpenMerchantView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public class OpenMerchantPersenter {
    private Activity mActivity;
    private OpenMerchantView mOpenMerchantView;
    private OpenMerchantModelImpl mOpenMerchantModel;

    public OpenMerchantPersenter(Activity activity, OpenMerchantView openMerchantView) {
        mActivity = activity;
        mOpenMerchantView = openMerchantView;
        mOpenMerchantModel = new OpenMerchantModelImpl(activity);
    }



    public void openMerchant(String businessUUID, String docUUID, String businessPicUUID, String shopname, String shopaddress, String accountNumber, String tel) {
        if (!mOpenMerchantModel.checkOpenMerchantParams(businessUUID, docUUID, businessPicUUID, shopname, shopaddress, accountNumber, tel)) {
            return;
        }
        mOpenMerchantModel.openMerchant(businessUUID, docUUID, businessPicUUID, shopname, shopaddress, accountNumber, tel, new OpenMerchantMedel.IOpenMerchantCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("开通商户权限：" + data);
                OpenMerchantBean openMerchantBean = new Gson().fromJson(data,OpenMerchantBean.class);
                if ("000000".equals(openMerchantBean.getCode())){
                    mOpenMerchantView.skipActivity(openMerchantBean.getData());
                    return;
                }else if ("888888".equals(openMerchantBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(openMerchantBean.getMessage());

            }
        });
    }

}
