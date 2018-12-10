package com.sl_group.jinyuntong_oem.extract_result;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;

/**
 * Created by 马天 on 2018/11/24.
 * description：提现结果
 */
public class ExtractResultActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvExtractResult;
    private TextView mTvExtractResultOrder;
    private TextView mTvExtractResultMoney;
    private TextView mTvExtractResultFee;


    @Override
    public int bindLayout() {
        return R.layout.activity_extract_result;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvExtractResult = findViewById(R.id.tv_extract_result);
        mTvExtractResultOrder = findViewById(R.id.tv_extract_result_order);
        mTvExtractResultMoney = findViewById(R.id.tv_extract_result_money);
        mTvExtractResultFee = findViewById(R.id.tv_extract_result_fee);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("提现结果");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String status = bundle.getString("status", "");
            switch (status) {
                case "s":
                    mTvExtractResult.setText("提现成功");
                    mTvExtractResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.tx), null, null);
                    break;
                case "a":
                    mTvExtractResult.setText("申请提现中");
                    mTvExtractResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.js), null, null);
                    break;
                case "i":
                    mTvExtractResult.setText("冻结");
                    mTvExtractResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.shibai), null, null);
                    break;
                case "c":
                    mTvExtractResult.setText("提现失败");
                    mTvExtractResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.shibai), null, null);
                    break;
                case "p":
                    mTvExtractResult.setText("提现处理中");
                    mTvExtractResult.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.js), null, null);
                    break;
            }
            //订单号
            mTvExtractResultOrder.setText(bundle.getString("bizOrderNumber",""));
            //提现金额
            mTvExtractResultMoney.setText(bundle.getDouble("srcAmt")+"元");
            //提现手续费
            mTvExtractResultFee.setText(bundle.getDouble("discountFeeAmt")+"元");
        }
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

    }

    @Override
    protected void onDestroy() {
        //提现成功后，发送广播，刷新财富页面数据
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(ExtractResultActivity.this).sendBroadcast(intent);
        sendBroadcast(intent);

        super.onDestroy();
    }
}
