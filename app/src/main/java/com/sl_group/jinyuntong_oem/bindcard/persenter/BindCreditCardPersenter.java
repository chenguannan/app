package com.sl_group.jinyuntong_oem.bindcard.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.BindCreditCardBean;
import com.sl_group.jinyuntong_oem.bindcard.model.BindCreditCardModel;
import com.sl_group.jinyuntong_oem.bindcard.model.BindCreditCardModelImpl;
import com.sl_group.jinyuntong_oem.bindcard.view.BindCreditCardView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/15.
 * description：绑定信用卡，业务逻辑
 */
public class BindCreditCardPersenter {
    private Activity mActivity;
    private BindCreditCardModelImpl mAddBankcardModel;
    private BindCreditCardView mBindCreditCardView;

    public BindCreditCardPersenter(Activity activity, BindCreditCardView bindCreditCardView) {
        mActivity = activity;
        mBindCreditCardView = bindCreditCardView;
        mAddBankcardModel = new BindCreditCardModelImpl(activity);
    }

    /**
      *
      * @param accountNumber 银行卡号
      * @param bankcardTel 银行预留手机号
      */
    public void bindBankcard(String accountNumber,String bankcardTel){
        //校验参数
        if (StringUtils.isEmpty(accountNumber)) {
            ToastUtils.showToast("请输入银行卡卡号或扫描银行卡");
            return ;
        }
        if (StringUtils.isEmpty(bankcardTel)) {
            ToastUtils.showToast("请输入银行预留手机号");
            return ;
        }
        if (bankcardTel.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return ;
        }
        mAddBankcardModel.bindCreditCard(accountNumber, bankcardTel, new BindCreditCardModel.IBindCreditCardCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("绑定银行卡："+data);
                BindCreditCardBean bindCreditCardBean = new Gson().fromJson(data,BindCreditCardBean.class);
                if ("000000".equals(bindCreditCardBean.getCode())){
                    mBindCreditCardView.openUnionpay(bindCreditCardBean.getData());
                    return;
                }else if ("888888".equals(bindCreditCardBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(bindCreditCardBean.getMessage());
            }
        });

    }
}
