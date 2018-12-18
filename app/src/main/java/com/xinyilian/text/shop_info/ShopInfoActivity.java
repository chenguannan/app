package com.xinyilian.text.shop_info;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.merchant_info.persenter.MerchantinfoPersenter;
import com.xinyilian.text.merchant_info.view.MerchantinfoView;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;

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
        //设置标题
        mTvActionbarTitle.setText("店铺信息");
        MerchantinfoPersenter merchantinfoPersenter = new MerchantinfoPersenter(this, this);

        String canReceived = (String) SPUtil.get(this, "canReceived", "f");
        String holderName = (String) SPUtil.get(this, "holderName", "");
        String idCard = (String) SPUtil.get(this, "idCard", "");
        String shortName = (String) SPUtil.get(this, "shortName", "");
        String shopAddress = (String) SPUtil.get(this, "shopAddress", "");
        if (StringUtils.isEmpty(canReceived) || canReceived.equals("f") || StringUtils.isEmpty(holderName) || StringUtils.isEmpty(idCard) ||
                StringUtils.isEmpty(shortName) || StringUtils.isEmpty(shopAddress)) {
            merchantinfoPersenter.merchantInfo();
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
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        displayInfo(dataBean.getCanReceived(), dataBean.getHolderName(), dataBean.getIdCard(), dataBean.getShortName(), dataBean.getShopAddress());
    }

    /**
      *
      * @param canReceived 是否开通商户
      * @param holderName 持卡人姓名
      * @param idCard 身份证号
      * @param shortName 店铺名称
      * @param shopAddress 店铺地址
      */
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
        mTvShopInfoIdcard.setText(StringUtils.getStarString(idCard,1,idCard.length()-1));
        mTvShopInfoShopName.setText(shortName);
        mTvShopInfoShopAddress.setText(shopAddress);
    }
}
