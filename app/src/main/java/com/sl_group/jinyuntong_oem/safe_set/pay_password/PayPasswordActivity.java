package com.sl_group.jinyuntong_oem.safe_set.pay_password;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;

/**
 * Created by 马天 on 2018/11/22.
 * description：支付密码
 */
public class PayPasswordActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvPayPasswordChange;
    private TextView mTvPayPasswordForget;


    @Override
    public int bindLayout() {
        return R.layout.activity_pay_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvPayPasswordChange = findViewById(R.id.tv_pay_password_change);
        mTvPayPasswordForget = findViewById(R.id.tv_pay_password_forget);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("支付密码");
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvPayPasswordChange.setOnClickListener(this);
        mTvPayPasswordForget.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_pay_password_change:
                //修改
                Bundle bundle = new Bundle();
                bundle.putString("action","changePayPassword");
                startActivity(SMSActivity.class,bundle);
                break;
            case R.id.tv_pay_password_forget:
                Bundle forgetBundle = new Bundle();
                forgetBundle.putString("action","forgetPayPassword");
                startActivity(SMSActivity.class,forgetBundle);
                break;
        }
    }


    @Override
    public void doBusiness(Context mContext) {

    }

}
