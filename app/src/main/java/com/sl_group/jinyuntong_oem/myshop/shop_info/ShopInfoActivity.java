package com.sl_group.jinyuntong_oem.myshop.shop_info;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：店铺信息
 */
public class ShopInfoActivity extends BaseActivity implements MerchantinfoView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvShopInfoOpenMerchant;
    private TextView mTvShopInfoName;
    private TextView mTvShopInfoIdcard;
    private TextView mTvShopInfoShopName;
    private TextView mTvShopInfoShopAddress;

    private MerchantinfoPersenter mMerchantinfoPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_shop_info;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvShopInfoOpenMerchant = findViewById(R.id.tv_shop_info_open_merchant);
        mTvShopInfoName = findViewById(R.id.tv_shop_info_name);
        mTvShopInfoIdcard = findViewById(R.id.tv_shop_info_idcard);
        mTvShopInfoShopName = findViewById(R.id.tv_shop_info_shop_name);
        mTvShopInfoShopAddress = findViewById(R.id.tv_shop_info_shop_address);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("店铺信息");
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);

        String canReceived = (String) SPUtil.get(this, "canReceived", "f");
        String holderName = (String) SPUtil.get(this, "holderName", "");
        String idCard = (String) SPUtil.get(this, "idCard", "");
        String shortName = (String) SPUtil.get(this, "shortName", "");
        String shopAddress = (String) SPUtil.get(this, "shopAddress", "");
        if (StringUtils.isEmpty(canReceived) || canReceived.equals("f") || StringUtils.isEmpty(holderName) || StringUtils.isEmpty(idCard) ||
                StringUtils.isEmpty(shortName) || StringUtils.isEmpty(shopAddress)) {
            mMerchantinfoPersenter.merchantInfo();
        } else {
            displayInfo(canReceived, holderName, idCard, shortName, shopAddress);
        }


    }


    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        displayInfo(dataBean.getCanReceived(), dataBean.getHolderName(), dataBean.getIdCard(), dataBean.getShortName(), dataBean.getShopAddress());
    }

    private void displayInfo(String canReceived, String holderName, String idCard, String shortName, String shopAddress) {
        switch (canReceived) {
            case "t":
                mTvShopInfoOpenMerchant.setText("已开通");
                break;
            case "f":
                mTvShopInfoOpenMerchant.setText("未开通");
                break;
        }
        mTvShopInfoName.setText(holderName);
        mTvShopInfoIdcard.setText(idCard);
        mTvShopInfoShopName.setText(shortName);
        mTvShopInfoShopAddress.setText(shopAddress);
    }
}
