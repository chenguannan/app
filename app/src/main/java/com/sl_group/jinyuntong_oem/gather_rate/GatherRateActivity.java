package com.sl_group.jinyuntong_oem.gather_rate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.GatherRateAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.BankFeeBean;
import com.sl_group.jinyuntong_oem.bean.CreditCardBean;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.bindcard.view.BindCreditCardActivity;
import com.sl_group.jinyuntong_oem.creditcard.persenter.CreditCardListPersenter;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListActivity;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListView;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.vip.persenter.VipCenterPersenter;
import com.sl_group.jinyuntong_oem.vip.view.VipCenterView;
import com.sl_group.jinyuntong_oem.web.X5WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/21.
 * description：收款费率
 */
public class GatherRateActivity extends BaseActivity implements VipCenterView, CreditCardListView,MerchantinfoView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvGatherRateCurrentLevel;
    private TextView mTvGatherRateUpvipDateHint;
    private TextView mTvGatherRateUpvipDate;
    private View mVGatherRateUpvipDateUnderline;
    private TextView mTvGatherRateVipDateHint;
    private TextView mTvGatherRateVipDate;
    private View mVGatherRateVipDateUnderline;
    private TextView mTvGatherRateExtractFee;
    private RecyclerView mRecycleViewBankcard;
    private Button mBtnGatherRateUplevel;


    private VipCenterPersenter mVipCenterPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private CreditCardListPersenter mCreditCardListPersenter;
    private GatherRateAdapter mGatherRateAdapter;
    private List<BankFeeBean.DataBean> mBeanList;
    private int currentLevel;

    private PopupWindow popupWindow;
    private String accountNumber;
    private String bankName;

    @Override
    protected void onStart() {
        super.onStart();
        //判断是不是选卡
        boolean isPaySelectCard = (boolean) SPUtil.get(GatherRateActivity.this, "isPaySelectCard", false);
        if (isPaySelectCard) {
            accountNumber = (String) SPUtil.get(GatherRateActivity.this, "payAccountNumber", "");
            bankName = (String) SPUtil.get(GatherRateActivity.this, "payBankName", "");
            popBuyVip(bankName,accountNumber);
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_gather_rate;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvGatherRateCurrentLevel = findViewById(R.id.tv_gather_rate_current_level);

        mTvGatherRateUpvipDateHint = findViewById(R.id.tv_gather_rate_upvip_date_hint);
        mTvGatherRateUpvipDate = findViewById(R.id.tv_gather_rate_upvip_date);
        mVGatherRateUpvipDateUnderline = findViewById(R.id.v_gather_rate_upvip_date_underline);

        mTvGatherRateVipDateHint = findViewById(R.id.tv_gather_rate_vip_date_hint);
        mTvGatherRateVipDate = findViewById(R.id.tv_gather_rate_vip_date);
        mVGatherRateVipDateUnderline = findViewById(R.id.v_gather_rate_vip_date_underline);
        mTvGatherRateExtractFee = findViewById(R.id.tv_gather_rate_extract_fee);
        mRecycleViewBankcard = findViewById(R.id.recycleView_bankcard);
        mBtnGatherRateUplevel = findViewById(R.id.btn_gather_rate_uplevel);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("收款费率");
        //vip中心  商户信息  信用卡列表persenter
        mVipCenterPersenter = new VipCenterPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        mCreditCardListPersenter = new CreditCardListPersenter(this, this);
        //费率数据集合
        mBeanList = new ArrayList<>();
        //初始化适配器
        mGatherRateAdapter = new GatherRateAdapter(mBeanList);
        //初始化recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //绑定适配器
        mRecycleViewBankcard.setAdapter(mGatherRateAdapter);
        mRecycleViewBankcard.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnGatherRateUplevel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_gather_rate_uplevel:
                mCreditCardListPersenter.creditCardList(true);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //当前等级
        currentLevel = (int) SPUtil.get(this, "vipLevel", 0);
        switch (currentLevel) {
            case 0:
                mMerchantinfoPersenter.merchantInfo();
                break;
            case 1:
                displayInfo((String) SPUtil.get(this, "vipTime", ""));
                mVipCenterPersenter.queryBankFee();
                break;

        }

    }

    /**
      * 获取银行费率
      * @param data 费率集合对象
      */
    @SuppressLint("SetTextI18n")
    @Override
    public void getBankFeeList(List<BankFeeBean.DataBean> data) {
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            BankFeeBean.DataBean dataBean = data.get(i);
            if (currentLevel == dataBean.getVipLevel()) {
                mBeanList.add(dataBean);
                mTvGatherRateExtractFee.setText(String.format(Locale.CHINA,"%.2f", dataBean.getExtraFee()) + "元/笔");
            }
        }
        mGatherRateAdapter.notifyDataSetChanged();
    }

    /**
      * 获取信用卡列表
      * @param list 绑定信用卡列表
      */
    @Override
    public void getCreditCardList(List<CreditCardBean.DataBean> list) {
        if (list.size() <= 0) {
            ToastUtils.showToast("请先交易卡");
            startActivity(BindCreditCardActivity.class);
            return;
        }
        //默认显示绑的第一张卡
        bankName = list.get(0).getBankName();
        accountNumber = list.get(0).getAccountNumber();
        popBuyVip(bankName, accountNumber);
    }

    /**
      * 购买VIP下单成功
      * @param openUrl 购买VIP链接
      */
    @Override
    public void buyVipSuccess(String openUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url",openUrl);
        startActivity(X5WebViewActivity.class,bundle);
        finish();
    }

    /**
      * 商户信息查询成功
      * @param dataBean 商户信息对象
      */
    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        //当前用户等级
        currentLevel = dataBean.getVipLevel();
        displayInfo(dataBean.getVipTime());
        mVipCenterPersenter.queryBankFee();
    }

    /**
      * 显示信息
      * @param vipDate 开通VIP时间
      */
    private void displayInfo(String vipDate) {
        switch (currentLevel) {
            case 0:
                mTvGatherRateVipDateHint.setVisibility(View.GONE);
                mTvGatherRateVipDate.setVisibility(View.GONE);
                mVGatherRateVipDateUnderline.setVisibility(View.GONE);
                mTvGatherRateUpvipDateHint.setVisibility(View.GONE);
                mTvGatherRateUpvipDate.setVisibility(View.GONE);
                mVGatherRateUpvipDateUnderline.setVisibility(View.GONE);
                mBtnGatherRateUplevel.setVisibility(View.VISIBLE);
                mTvGatherRateCurrentLevel.setText("普通商户");
                break;
            case 1:
                mTvGatherRateVipDateHint.setVisibility(View.VISIBLE);
                mTvGatherRateVipDate.setVisibility(View.VISIBLE);
                mVGatherRateVipDateUnderline.setVisibility(View.VISIBLE);
                mTvGatherRateUpvipDateHint.setVisibility(View.VISIBLE);
                mTvGatherRateUpvipDate.setVisibility(View.VISIBLE);
                mVGatherRateUpvipDateUnderline.setVisibility(View.VISIBLE);
                mBtnGatherRateUplevel.setVisibility(View.GONE);
                mTvGatherRateCurrentLevel.setText("VIP商户");
                mTvGatherRateUpvipDate.setText(vipDate);
                mTvGatherRateVipDate.setText("一年");

                break;
        }
    }


    /**
      * 购买VIP弹窗
      * @param bankName 银行名称
      * @param accountNumber 结算卡账号
      */
    @SuppressLint("SetTextI18n")
    private void popBuyVip(String bankName, final String accountNumber) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_buy_vip, null);

        ImageView mImgPopBuyVipClose = view.findViewById(R.id.img_pop_buy_vip_close);
        final TextView mTvPopBuyVipPrice = view.findViewById(R.id.tv_pop_buy_vip_price);
        TextView mTvPopBuyVipSelectcard = view.findViewById(R.id.tv_pop_buy_vip_selectcard);
        Button mBtnPopBuyVipImmediatelyPay = view.findViewById(R.id.btn_pop_buy_vip_immediately_pay);
        mTvPopBuyVipPrice.setText(CommonSet.VIP_PRICE);
        mTvPopBuyVipSelectcard.setText(bankName + "(" + accountNumber.substring(accountNumber.length() - 4, accountNumber.length()) + ")");


        popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        mImgPopBuyVipClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //选择信用卡
        mTvPopBuyVipSelectcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isPay", true);
                startActivity(CreditCardListActivity.class, bundle);
            }
        });
        //立即支付
        mBtnPopBuyVipImmediatelyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                mVipCenterPersenter.buyVip(accountNumber);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtil.remove(this,"isPaySelectCard");
        SPUtil.remove(this,"payAccountNumber");
        SPUtil.remove(this,"payBankName");
    }
}
