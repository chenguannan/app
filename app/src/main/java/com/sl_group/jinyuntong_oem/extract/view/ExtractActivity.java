package com.sl_group.jinyuntong_oem.extract.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.ExtractBean;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.extract.ExtractResultActivity;
import com.sl_group.jinyuntong_oem.extract.persenter.ExtractPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.utils.NumberUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

/**
 * Created by 马天 on 2018/11/24.
 * description：申请提现
 */
public class ExtractActivity extends BaseActivity implements MerchantinfoView,ExtractView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvApplyExtractCanExtract;
    private TextView mTvApplyExtractExtractCardnumber;
    private EditText mEtApplyExtractExtractMoney;
    private Button mBtnApplyExtractMakesure;
    //商户信息persenter
    private MerchantinfoPersenter mMerchantinfoPersenter;
    //提现persenter
    private ExtractPersenter mExtractPersenter;
    //可提现余额
    private double canExtractMoney;
    //提现持卡人姓名
    private String extractHolderName;
    //提现卡号
    private String extractAccountNumber;


    @Override
    public int bindLayout() {
        return R.layout.activity_extract;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvApplyExtractCanExtract = findViewById(R.id.tv_apply_extract_can_extract);
        mTvApplyExtractExtractCardnumber = findViewById(R.id.tv_apply_extract_extract_cardnumber);
        mEtApplyExtractExtractMoney = findViewById(R.id.et_apply_extract_extract_money);
        mBtnApplyExtractMakesure = findViewById(R.id.btn_apply_extract_makesure);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("申请提现");

        mMerchantinfoPersenter = new MerchantinfoPersenter(this,this);
        mExtractPersenter = new ExtractPersenter(this,this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            canExtractMoney = bundle.getDouble("canExtractMoney",0.00d);
        }
        //输入框设置只能输两位小数
        NumberUtils.setPricePoint(mEtApplyExtractExtractMoney);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnApplyExtractMakesure.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_apply_extract_makesure:
                //申请提现
                String extractMoney = mEtApplyExtractExtractMoney.getText().toString().trim();
                mExtractPersenter.applyExtact(extractMoney, extractHolderName,extractAccountNumber);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mMerchantinfoPersenter.merchantInfo();
    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {

        mTvApplyExtractCanExtract.setText(String.format("%.2f",canExtractMoney)+"元");
        extractHolderName = dataBean.getHolderName();
        extractAccountNumber = dataBean.getAccountNumber();
        mTvApplyExtractExtractCardnumber.setText(StringUtils.getStarString(extractAccountNumber,4,extractAccountNumber.length()-4));

    }

    @Override
    public void extractSuccess(ExtractBean.DataBean data) {
        //提现成功
        Bundle bundle = new Bundle();
        bundle.putString("bizOrderNumber",data.getBizOrderNumber());
        bundle.putDouble("srcAmt",data.getSrcAmt());
        bundle.putDouble("discountFeeAmt",data.getDiscountFeeAmt());
        bundle.putString("status",data.getStatus());
        startActivity(ExtractResultActivity.class,bundle);
        finish();
    }
}
