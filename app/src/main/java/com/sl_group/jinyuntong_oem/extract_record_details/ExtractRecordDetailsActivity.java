package com.sl_group.jinyuntong_oem.extract_record_details;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.DealBrokerageDetailsBean;
import com.sl_group.jinyuntong_oem.bean.ExtractBrokerageDetailsBean;
import com.sl_group.jinyuntong_oem.brokerage_details.persenter.BrokerageDetailsPersenter;
import com.sl_group.jinyuntong_oem.brokerage_details.view.BrokerageDetailsView;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by 马天 on 2018/11/24.
 * description：提现记录详情
 */
public class ExtractRecordDetailsActivity extends BaseActivity implements BrokerageDetailsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvExtractRecordDetailsResult;
    private TextView mTvExtractRecordDetailsOrder;
    private TextView mTvExtractRecordDetailsDate;
    private TextView mTvExtractRecordDetailsMoney;
    private TextView mTvExtractDetailsFee;
    private TextView mTvExtractDetailsRealmoney;
    private TextView mTvExtractRecordDetailsAccountnumber;
    private TextView mTvExtractRecordDetailsFailHint;
    private TextView mTvExtractRecordDetailsFail;

    private BrokerageDetailsPersenter mBrokerageDetailsPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_extract_record_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvExtractRecordDetailsResult = findViewById(R.id.tv_extract_record_details_result);
        mTvExtractRecordDetailsOrder = findViewById(R.id.tv_extract_record_details_order);
        mTvExtractRecordDetailsDate = findViewById(R.id.tv_extract_record_details_date);
        mTvExtractRecordDetailsMoney = findViewById(R.id.tv_extract_record_details_money);
        mTvExtractDetailsFee = findViewById(R.id.tv_extract_details_fee);
        mTvExtractDetailsRealmoney = findViewById(R.id.tv_extract_details_realmoney);
        mTvExtractRecordDetailsAccountnumber = findViewById(R.id.tv_extract_record_details_accountnumber);
        mTvExtractRecordDetailsFailHint = findViewById(R.id.tv_extract_record_details_fail_hint);
        mTvExtractRecordDetailsFail = findViewById(R.id.tv_extract_record_details_fail);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("提现详情");

        mBrokerageDetailsPersenter = new BrokerageDetailsPersenter(this, this);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mBrokerageDetailsPersenter.brokerageDetails(bundle.getString("intoType","14"),bundle.getString("logId",""));
        }

    }

    @Override
    public void brokerageDetails(ExtractBrokerageDetailsBean.DataBean data) {
        switch (data.getStatus()) {
            case "s":
                mTvExtractRecordDetailsResult.setText("提现成功");
                mTvExtractRecordDetailsResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.tx), null, null);

                break;
            case "a":
                mTvExtractRecordDetailsResult.setText("审核中");
                mTvExtractRecordDetailsResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.js), null, null);

                break;
            case "c":
                mTvExtractRecordDetailsResult.setText("提现失败");
                mTvExtractRecordDetailsResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.shibai), null, null);
                mTvExtractRecordDetailsFailHint.setVisibility(View.VISIBLE);
                mTvExtractRecordDetailsFail.setVisibility(View.VISIBLE);
                mTvExtractRecordDetailsFail.setText(data.getRemark());
                break;
        }
        mTvExtractRecordDetailsOrder.setText(data.getBizOrderNumber());
        mTvExtractRecordDetailsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(data.getCreatedDate()));
        mTvExtractRecordDetailsMoney.setText(String.format("%.2f", data.getSrcAmt()) + "元");
        mTvExtractDetailsFee.setText(String.format("%.2f", data.getDiscountFeeAmt()) + "元");
        mTvExtractDetailsRealmoney.setText(String.format("%.2f", data.getSrcAmt() - data.getDiscountFeeAmt()) + "元");
        mTvExtractRecordDetailsAccountnumber.setText(StringUtils.getStarString(data.getAccountNumber(), 4, data.getAccountNumber().length() - 4));
    }

    @Override
    public void dealDetails(DealBrokerageDetailsBean.DataBean data) {

    }
}
