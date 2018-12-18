package com.xinyilian.text.creditcard.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.xinyilian.text.CompelLogin;
import com.xinyilian.text.creditcard.model.CreditCardListModel;
import com.xinyilian.text.creditcard.model.CreditCardListModelImpl;
import com.xinyilian.text.creditcard.view.CreditCardListView;
import com.xinyilian.text.bean.CreditCardBean;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/19.
 * description：信用卡列表业务
 */
public class CreditCardListPersenter {
    private Activity mActivity;
    private CreditCardListView mCreditCardListView;
    private CreditCardListModelImpl mBankcardModel;

    public CreditCardListPersenter(Activity activity, CreditCardListView creditCardListView) {
        mActivity = activity;
        mCreditCardListView = creditCardListView;
        mBankcardModel = new CreditCardListModelImpl(activity);
    }

    /**
      * 信用卡列表
      */
    public void creditCardList(boolean isShowProgress){
        mBankcardModel.CreditCardList(isShowProgress,new CreditCardListModel.ICreditCardListCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("信用卡列表："+data);
                CreditCardBean bankCardBean = new Gson().fromJson(data, CreditCardBean.class);
                if ("000000".equals(bankCardBean.getCode())){
                    mCreditCardListView.getCreditCardList(bankCardBean.getData());
                    return;
                }else if ("888888".equals(bankCardBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(bankCardBean.getMessage());
            }
        });
    }
}
