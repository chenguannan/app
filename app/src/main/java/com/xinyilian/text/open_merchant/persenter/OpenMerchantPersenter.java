package com.xinyilian.text.open_merchant.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.bean.OpenMerchantBean;
import com.xinyilian.text.open_merchant.model.OpenMerchantMedel;
import com.xinyilian.text.open_merchant.model.OpenMerchantModelImpl;
import com.xinyilian.text.open_merchant.view.OpenMerchantView;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/17.
 * description：开通商户权限
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
        if (StringUtils.isEmpty(businessUUID)) {
            ToastUtils.showToast("请上传营业场所照");
            return ;
        }
        if (StringUtils.isEmpty(docUUID)) {
            ToastUtils.showToast("请上传门头照");
            return ;
        }
        if (StringUtils.isEmpty(businessPicUUID)) {
            ToastUtils.showToast("请上传营业执照");
            return ;
        }
        if (StringUtils.isEmpty(shopname)) {
            ToastUtils.showToast("请输入店铺名称");
            return ;
        }
        if (shopaddress.contains("选择")) {
            ToastUtils.showToast("请选择店铺地址：省/市/区");
            return ;
        }
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入储蓄卡号");
            return ;
        }
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return ;
        }
        if (tel.length() != 11) {
            ToastUtils.showToast("请输入正确的银行预留手机号");
            return ;
        }
        mOpenMerchantModel.openMerchant(businessUUID, docUUID, businessPicUUID, shopname, shopaddress, accountNumber, tel, new OpenMerchantMedel.IOpenMerchantCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("开通商户权限：" + data);
                OpenMerchantBean openMerchantBean = new Gson().fromJson(data,OpenMerchantBean.class);
                if ("000000".equals(openMerchantBean.getCode())){
                    mOpenMerchantView.openMerchantSuccess(openMerchantBean.getData());
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
