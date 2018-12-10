package com.sl_group.jinyuntong_oem.merchant_info.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.model.MerchantInfoModelImpl;
import com.sl_group.jinyuntong_oem.merchant_info.model.MerchantinfoModel;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：
 */
public class MerchantinfoPersenter {
    private Activity mActivity;
    private MerchantinfoView mMerchantinfoView;
    private MerchantInfoModelImpl mUserinfoModel;

    public MerchantinfoPersenter(Activity activity, MerchantinfoView merchantinfoView) {
        mActivity = activity;
        mMerchantinfoView = merchantinfoView;
        mUserinfoModel = new MerchantInfoModelImpl(activity);
    }

    public void merchantInfo(){
        mUserinfoModel.merchantInfo(new MerchantinfoModel.IMerchantInfoCallBack(){
            @Override
            public void onSuccess(String data) {
                LogUtils.i("商户信息："+data);
                MerchantInfoBean merchantInfoBean = new Gson().fromJson(data,MerchantInfoBean.class);
                if ("000000".equals(merchantInfoBean.getCode())){
                    MerchantInfoBean.DataBean dataBean = merchantInfoBean.getData();
                    mMerchantinfoView.merchantInfoSuccess(dataBean);
                    saveMerchantInfo(dataBean);
                    return;
                }else if ("888888".equals(merchantInfoBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(merchantInfoBean.getMessage());
            }
        });
    }
    /**
      * 保存商户信息
      * @param dataBean 商户信息对象
      */
    private void saveMerchantInfo(MerchantInfoBean.DataBean dataBean){
        SPUtil.put(mActivity,"shortName",dataBean.getShortName());
        SPUtil.put(mActivity,"shopAddress",dataBean.getShopAddress());
        SPUtil.put(mActivity,"merchantName",dataBean.getMerchantName());
        SPUtil.put(mActivity,"mid",dataBean.getMid());
        SPUtil.put(mActivity,"participantId",String.valueOf(dataBean.getParticipantId()));
        SPUtil.put(mActivity,"vipLevel",dataBean.getVipLevel());
        SPUtil.put(mActivity,"agentName",dataBean.getAgentName());
        SPUtil.put(mActivity,"cellPhone",dataBean.getCellPhone());
        SPUtil.put(mActivity,"firstName",dataBean.getFirstName());
        SPUtil.put(mActivity,"agencyId",String.valueOf(dataBean.getAgencyId()));
        SPUtil.put(mActivity,"qualifiedState",dataBean.getQualifiedState());
        SPUtil.put(mActivity,"accountNumber",dataBean.getAccountNumber());
        SPUtil.put(mActivity,"tel",dataBean.getTel());
        SPUtil.put(mActivity,"depositBank",dataBean.getDepositBank());
        SPUtil.put(mActivity,"holderName",dataBean.getHolderName());
        SPUtil.put(mActivity,"idCard",dataBean.getIdCard());
        SPUtil.put(mActivity,"canReceived",dataBean.getCanReceived());
        SPUtil.put(mActivity,"payCode",dataBean.getPayCode());
        SPUtil.put(mActivity,"gesturePassword",dataBean.getGesturePassword());
        SPUtil.put(mActivity,"tradePassword",dataBean.getTradePassword());
        SPUtil.put(mActivity,"vipTime",dataBean.getVipTime());
        SPUtil.put(mActivity,"headPortraitDirectoryName",dataBean.getHeadPortraitDirectoryName());
        SPUtil.put(mActivity,"headPortraitFilePrefix",dataBean.getHeadPortraitFilePrefix());
    }
}
