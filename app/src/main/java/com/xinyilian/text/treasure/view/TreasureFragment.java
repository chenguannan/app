package com.xinyilian.text.treasure.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyilian.text.CommonSet;
import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseFragment;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.bean.TreasureBean;
import com.xinyilian.text.deal_record.DealRecordActivity;
import com.xinyilian.text.extract.view.ExtractActivity;
import com.xinyilian.text.extract_record.ExtractRecordActivity;
import com.xinyilian.text.gather_rate.GatherRateActivity;
import com.xinyilian.text.merchant_info.persenter.MerchantinfoPersenter;
import com.xinyilian.text.merchant_info.view.MerchantinfoView;
import com.xinyilian.text.treasure.persenter.TrerasurePersenter;
import com.xinyilian.text.upvip_borkerage.UpVipBrokerageActivity;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.ToastUtils;
import com.xinyilian.text.vip_center.VipCenterActivity;

import java.util.Locale;

/**
 * Created by 马天 on 2018/11/13.
 * description：财富
 */
public class TreasureFragment extends BaseFragment implements TreasureView, MerchantinfoView {
    private TextView mTvTreasureCanExtractMoney;
    private TextView mTvTreasureApplyExtract;
    private LinearLayout mLlTreasureAllEarning;
    private LinearLayout mLlTreasureAllExtract;
    private LinearLayout mLlTreasureDealBrokerage;
    private LinearLayout mLlTreasureUpvipBrokerage;
    private TextView mTvTreasureAllEraning;
    private TextView mTvTreasureAllExtract;
    private TextView mTvTreasureDealBrokerage;
    private TextView mTvTreasureUpvipBrokerage;
    private ImageView mImgTreasurePolicyleft;

    private int vipLevel = -1;
    private TrerasurePersenter mTrerasurePersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    //可提金额
    private double canExtractMoney = 0.00d;
    //政策链接
    private String ruleUrl;


    @Override
    public int bindLayout() {
        return R.layout.fragment_treasure;
    }

    @Override
    public void initView(View view) {
        mTvTreasureCanExtractMoney = view.findViewById(R.id.tv_treasure_can_extract_money);
        mTvTreasureApplyExtract = view.findViewById(R.id.tv_treasure_apply_extract);
        mLlTreasureAllEarning = view.findViewById(R.id.ll_treasure_all_earning);
        mLlTreasureAllExtract = view.findViewById(R.id.ll_treasure_all_extract);
        mLlTreasureDealBrokerage = view.findViewById(R.id.ll_treasure_deal_brokerage);
        mLlTreasureUpvipBrokerage = view.findViewById(R.id.ll_treasure_upvip_brokerage);
        mTvTreasureAllEraning = view.findViewById(R.id.tv_treasure_all_eraning);
        mTvTreasureAllExtract = view.findViewById(R.id.tv_treasure_all_extract);
        mTvTreasureDealBrokerage = view.findViewById(R.id.tv_treasure_deal_brokerage);
        mTvTreasureUpvipBrokerage = view.findViewById(R.id.tv_treasure_upvip_brokerage);
        //mImgTreasurePolicyLook = view.findViewById(R.id.img_treasure_policy_look);
        mImgTreasurePolicyleft = view.findViewById(R.id.img_treasure_policy_left);
    }

    @Override
    public void initData() {
        mTrerasurePersenter = new TrerasurePersenter(getActivity(), this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(getActivity(), this);

        ruleUrl = (String) SPUtil.get(getActivity(), "ruleUrl", "");
    }

    @Override
    public void setListener() {
        mTvTreasureApplyExtract.setOnClickListener(this);
        mLlTreasureAllEarning.setOnClickListener(this);
        mLlTreasureAllExtract.setOnClickListener(this);
        mLlTreasureDealBrokerage.setOnClickListener(this);
        mLlTreasureUpvipBrokerage.setOnClickListener(this);
        mImgTreasurePolicyleft.setOnClickListener(this);
    }




    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_treasure_apply_extract:
                //申请提现
                if (canExtractMoney < CommonSet.EXTRACT_LIMIT) {
                    ToastUtils.showToast("满" + CommonSet.EXTRACT_LIMIT + "元可提");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putDouble("canExtractMoney", Double.parseDouble(mTvTreasureCanExtractMoney.getText().toString().trim()));
                startActivity(ExtractActivity.class, bundle);
                break;
            case R.id.ll_treasure_all_earning:

                break;
            case R.id.ll_treasure_all_extract:
                startActivity(ExtractRecordActivity.class);
                break;
            case R.id.ll_treasure_deal_brokerage:
                startActivity(DealRecordActivity.class);
                break;
            case R.id.ll_treasure_upvip_brokerage:
                startActivity(UpVipBrokerageActivity.class);
                break;
            case R.id.img_treasure_policy_left:

                switch (vipLevel) {
                    case -1:
                      //  ToastUtils.showToast("asdasdasdasdasdasdasd");
                        mMerchantinfoPersenter.merchantInfo();
                        break;
                    case 0:
                        //普通商户  跳转到VIP中心
                        startActivity(VipCenterActivity.class);
                        break;
                    case 1:
                        //VIP商户，跳转到收款费率
                        startActivity(GatherRateActivity.class);
                }
//                if (StringUtils.isEmpty(ruleUrl)) {
//                    mMerchantinfoPersenter.merchantInfo();
//                } else {
//                    Bundle ruleUrlBundle = new Bundle();
//                    ruleUrlBundle.putString("url", ruleUrl);
//                    startActivity(LoadWebActivity.class, ruleUrlBundle);
//                }
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mTrerasurePersenter.treasure();

        //注册广播，提现后刷新数据
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("data");
                if ("refresh".equals(msg)) {
                    mTrerasurePersenter.treasure();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    @Override
    public void treasureSuccess(TreasureBean.DataBean data) {
        canExtractMoney = data.getBalance();
        //可提佣金
        mTvTreasureCanExtractMoney.setText(String.format(Locale.CHINA,"%.2f", data.getBalance()));
        //累计收入，可提加已提
        mTvTreasureAllEraning.setText(String.format(Locale.CHINA,"%.2f", data.getEncAmt() + data.getBalance()));
        //累计提现
        mTvTreasureAllExtract.setText(String.format(Locale.CHINA,"%.2f", data.getEncAmt()));
        //交易佣金
        mTvTreasureDealBrokerage.setText(String.format(Locale.CHINA,"%.2f", data.getTxnAmt()));
        //升级VIP奖励
        mTvTreasureUpvipBrokerage.setText(String.format(Locale.CHINA,"%.2f", data.getDirectAmt() + data.getIndirectAmt()));

    }

    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        vipLevel = dataBean.getVipLevel();
//        Bundle bundle = new Bundle();
//        bundle.putString("url", dataBean.getRuleUrl());
//        startActivity(LoadWebActivity.class, bundle);
        switch (vipLevel) {
            case -1:
                //ToastUtils.showToast("asdasdasdasdasdasdasd");
                //mMerchantinfoPersenter.merchantInfo();
                break;
            case 0:
                //普通商户  跳转到VIP中心
                startActivity(VipCenterActivity.class);
                break;
            case 1:
                //VIP商户，跳转到收款费率
                startActivity(GatherRateActivity.class);
        }
    }
}
