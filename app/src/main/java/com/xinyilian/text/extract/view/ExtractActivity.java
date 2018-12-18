package com.xinyilian.text.extract.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.ExtractBean;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.extract_result.ExtractResultActivity;
import com.xinyilian.text.extract.persenter.ExtractPersenter;
import com.xinyilian.text.merchant_info.persenter.MerchantinfoPersenter;
import com.xinyilian.text.merchant_info.view.MerchantinfoView;
import com.xinyilian.text.utils.NumberUtils;
import com.xinyilian.text.utils.StringUtils;

import java.util.Locale;

/**
 * Created by 马天 on 2018/11/24.
 * description：申请提现
 */
public class ExtractActivity extends BaseActivity implements MerchantinfoView,ExtractView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvExtractCanExtract;
    private TextView mTvExtractAccountnumber;
    private EditText mEtExtractMoney;
    private Button mBtnExtractMakesure;
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
        mTvExtractCanExtract = findViewById(R.id.tv_extract_can_extract);
        mTvExtractAccountnumber = findViewById(R.id.tv_extract_accountnumber);
        mEtExtractMoney = findViewById(R.id.et_extract_money);
        mBtnExtractMakesure = findViewById(R.id.btn_extract_makesure);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("申请提现");
        //商户信息persenter
        mMerchantinfoPersenter = new MerchantinfoPersenter(this,this);
        //提现persenter
        mExtractPersenter = new ExtractPersenter(this,this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            //可提现余额
            canExtractMoney = bundle.getDouble("canExtractMoney",0.00d);
        }
        //输入框设置只能输两位小数
        NumberUtils.setPricePoint(mEtExtractMoney);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnExtractMakesure.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_extract_makesure:
                //申请提现
                String extractMoney = mEtExtractMoney.getText().toString().trim();
                mExtractPersenter.applyExtact(extractMoney, extractHolderName,extractAccountNumber);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mMerchantinfoPersenter.merchantInfo();
    }

    /**
      * 获取商户信息
      * @param dataBean 商户信息对象
      */
    @SuppressLint("SetTextI18n")
    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        //显示可提金额
        mTvExtractCanExtract.setText(String.format(Locale.CHINA,"%.2f",canExtractMoney)+"元");
        //持卡人姓名
        extractHolderName = dataBean.getHolderName();
        //结算卡号
        extractAccountNumber = dataBean.getAccountNumber();
        //显示前四后四，中间星号处理
        mTvExtractAccountnumber.setText(StringUtils.getStarString(extractAccountNumber,4,extractAccountNumber.length()-4));

    }

    /**
      *
      * @param data 提现成功回调
      */
    @Override
    public void extractSuccess(ExtractBean.DataBean data) {
        //提现成功，显示提现详情，传递参数
        Bundle bundle = new Bundle();
        bundle.putString("bizOrderNumber",data.getBizOrderNumber());
        bundle.putDouble("srcAmt",data.getSrcAmt());
        bundle.putDouble("discountFeeAmt",data.getDiscountFeeAmt());
        bundle.putString("status",data.getStatus());
        startActivity(ExtractResultActivity.class,bundle);
        finish();
    }
}
