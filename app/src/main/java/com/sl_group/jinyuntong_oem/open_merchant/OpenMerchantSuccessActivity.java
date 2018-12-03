package com.sl_group.jinyuntong_oem.open_merchant;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;

/**
 * Created by 马天 on 2018/11/17.
 * description：开通商户成功
 */
public class OpenMerchantSuccessActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private Button mBtnOpenMerchantSuccessComplete;

    @Override
    public int bindLayout() {
        return R.layout.activity_open_merchant_success;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mBtnOpenMerchantSuccessComplete = findViewById(R.id.btn_open_merchant_success_complete);

    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText(getString(R.string.open_success));
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnOpenMerchantSuccessComplete.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_open_merchant_success_complete:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
