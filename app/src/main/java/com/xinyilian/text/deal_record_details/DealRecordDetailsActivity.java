package com.xinyilian.text.deal_record_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.DealBrokerageDetailsBean;
import com.xinyilian.text.bean.ExtractBrokerageDetailsBean;
import com.xinyilian.text.brokerage_details.persenter.BrokerageDetailsPersenter;
import com.xinyilian.text.brokerage_details.view.BrokerageDetailsView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/24.
 * description：交易记录详情
 */
public class DealRecordDetailsActivity extends BaseActivity implements BrokerageDetailsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvDealRecordDetailsResult;
    private TextView mTvDealRecordDetailsType;
    private TextView mTvDealRecordDetailsDate;
    private TextView mTvDealRecordDetailsMoney;
    private TextView mTvDealDetailsPeople;

    //佣金详情persenter
    private BrokerageDetailsPersenter mBrokerageDetailsPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_deal_record_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvDealRecordDetailsResult = findViewById(R.id.tv_deal_record_details_result);
        mTvDealRecordDetailsType = findViewById(R.id.tv_deal_record_details_type);
        mTvDealRecordDetailsDate = findViewById(R.id.tv_deal_record_details_date);
        mTvDealRecordDetailsMoney = findViewById(R.id.tv_deal_record_details_money);
        mTvDealDetailsPeople = findViewById(R.id.tv_deal_details_people);

    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("交易详情");
        //初始化佣金详情persenter
        mBrokerageDetailsPersenter = new BrokerageDetailsPersenter(this, this);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mBrokerageDetailsPersenter.brokerageDetails(bundle.getString("intoType", "10"), bundle.getString("logId", ""));
        }

    }

    @Override
    public void brokerageDetails(ExtractBrokerageDetailsBean.DataBean data) {

    }

    /**
      * 获取交易佣金详情
      * @param data 交易佣金详情对象
      */
    @SuppressLint("SetTextI18n")
    @Override
    public void dealDetails(DealBrokerageDetailsBean.DataBean data) {
        mTvDealRecordDetailsResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.tx), null, null);
        mTvDealRecordDetailsType.setText("交易佣金");
        mTvDealRecordDetailsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(data.getCreatedDate()));
        mTvDealRecordDetailsMoney.setText(String.format(Locale.CHINA,"%.2f", data.getAmt()) + "元");
        mTvDealDetailsPeople.setText(data.getMerchantName());
    }
}
