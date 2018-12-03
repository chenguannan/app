package com.sl_group.jinyuntong_oem.gather.set_money;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.gather.view.GatherActivity;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/14.
 * description：设置金额
 */
public class SetMoneyActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtGatherSetMoney;
    private Button mBtnGatherSetMoneySure;



    @Override
    public int bindLayout() {
        return R.layout.activity_gather_set_money;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mEtGatherSetMoney = findViewById(R.id.et_gather_set_money);
        mBtnGatherSetMoneySure = findViewById(R.id.btn_gather_set_money_sure);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("设置金额");
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnGatherSetMoneySure.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                startActivity(GatherActivity.class);
                finish();
                break;
            case R.id.btn_gather_set_money_sure:
                String money = mEtGatherSetMoney.getText().toString().trim();
                if (StringUtils.isEmpty(money)){
                    ToastUtils.showToast("请输入收款金额");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("gatherSetMoney",money);
                startActivity(GatherActivity.class,bundle);
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(GatherActivity.class);
        finish();
    }
}
