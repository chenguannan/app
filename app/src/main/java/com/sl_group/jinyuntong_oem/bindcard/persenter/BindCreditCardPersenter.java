package com.sl_group.jinyuntong_oem.bindcard.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bindcard.model.BindCreditCardModel;
import com.sl_group.jinyuntong_oem.bindcard.model.BindCreditCardModelImpl;
import com.sl_group.jinyuntong_oem.bindcard.view.BindCreditCardView;
import com.sl_group.jinyuntong_oem.bean.AddBankcardBean;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
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
      * 绑定信用卡
      */
    public void bindBankcard(String accountNumber,String bankcardTel){
        //校验参数
        if (!mAddBankcardModel.verficBindCreditCardData(accountNumber,bankcardTel)){
            return;
        }
        mAddBankcardModel.bindCreditCard(accountNumber, bankcardTel, new BindCreditCardModel.IBindCreditCardCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("绑定银行卡："+data);
                AddBankcardBean addBankcardBean = new Gson().fromJson(data,AddBankcardBean.class);
                if ("000000".equals(addBankcardBean.getCode())){
                    mBindCreditCardView.openUnionpay(addBankcardBean.getData());
                    return;
                }else if ("888888".equals(addBankcardBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(addBankcardBean.getMessage());
            }
        });

    }
}
