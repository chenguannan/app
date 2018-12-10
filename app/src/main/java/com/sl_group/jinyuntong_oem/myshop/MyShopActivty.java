package com.sl_group.jinyuntong_oem.myshop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.my_team.view.MyTeamActivity;
import com.sl_group.jinyuntong_oem.gather_bill.view.GatherBillActivity;
import com.sl_group.jinyuntong_oem.gather_rate.GatherRateActivity;
import com.sl_group.jinyuntong_oem.settle_card_info.SettleCardInfoActivity;
import com.sl_group.jinyuntong_oem.shop_info.ShopInfoActivity;

/**
 * Created by 马天 on 2018/11/14.
 * description 我的店铺
 */
public class MyShopActivty extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private RelativeLayout mRlMyShopShopInfo;
    private RelativeLayout mRlMyShopSettleInfo;
    private RelativeLayout mRlMyShopGatherBill;
    private RelativeLayout mRlMyShopGatherRate;
    private RelativeLayout mRlMyShopMyTeam;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_shop;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mRlMyShopShopInfo = findViewById(R.id.rl_my_shop_shop_info);
        mRlMyShopSettleInfo = findViewById(R.id.rl_my_shop_settle_info);
        mRlMyShopGatherBill = findViewById(R.id.rl_my_shop_gather_bill);
        mRlMyShopGatherRate = findViewById(R.id.rl_my_shop_gather_rate);
        mRlMyShopMyTeam = findViewById(R.id.rl_my_shop_my_team);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("店铺");
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mRlMyShopShopInfo.setOnClickListener(this);
        mRlMyShopSettleInfo.setOnClickListener(this);
        mRlMyShopGatherBill.setOnClickListener(this);
        mRlMyShopGatherRate.setOnClickListener(this);
        mRlMyShopMyTeam.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.rl_my_shop_shop_info:
                //店铺信息
                startActivity(ShopInfoActivity.class);
                break;
            case R.id.rl_my_shop_settle_info:
                //结算信息
                startActivity(SettleCardInfoActivity.class);
                break;
            case R.id.rl_my_shop_gather_bill:
                //收款账单
                startActivity(GatherBillActivity.class);
                break;
            case R.id.rl_my_shop_gather_rate:
                //收款费率
                startActivity(GatherRateActivity.class);
                break;
            case R.id.rl_my_shop_my_team:
                //我的团队
                startActivity(MyTeamActivity.class);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
