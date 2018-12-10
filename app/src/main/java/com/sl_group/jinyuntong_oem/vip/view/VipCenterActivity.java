package com.sl_group.jinyuntong_oem.vip.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.BankFeeAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.BankFeeBean;
import com.sl_group.jinyuntong_oem.bean.CreditCardBean;
import com.sl_group.jinyuntong_oem.creditcard.persenter.CreditCardListPersenter;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListActivity;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListView;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.vip.persenter.VipCenterPersenter;
import com.sl_group.jinyuntong_oem.web.X5WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/19.
 * description：vip中心
 */
public class VipCenterActivity extends BaseActivity implements VipCenterView, CreditCardListView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvVipCenterBuyContent;
    private TextView mTvVipCenterBuyDiscounts;
    private Button mBtnVipCenterImmediatelyBuy;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewBankcard;

    private VipCenterPersenter mVipCenterPersenter;
    private CreditCardListPersenter mCreditCardListPersenter;


    private List<BankFeeBean.DataBean> mNormalBeanList;
    private List<BankFeeBean.DataBean> mVipBeanList;
    private BankFeeAdapter mBankFeeAdapter;

    private String accountNumber;
    private String bankName;
    private PopupWindow popupWindow;

    @Override
    protected void onStart() {
        super.onStart();
        boolean isPaySelectCard = (boolean) SPUtil.get(VipCenterActivity.this, "isPaySelectCard", false);
        if (isPaySelectCard) {
            accountNumber = (String) SPUtil.get(VipCenterActivity.this, "payAccountNumber", "");
            bankName = (String) SPUtil.get(VipCenterActivity.this, "payBankName", "");
            popBuyVip(bankName,accountNumber);
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_vip_center;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvVipCenterBuyContent = findViewById(R.id.tv_vip_center_buy_content);
        mTvVipCenterBuyDiscounts = findViewById(R.id.tv_vip_center_buy_discounts);
        mBtnVipCenterImmediatelyBuy = findViewById(R.id.btn_vip_center_immediately_buy);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewBankcard = findViewById(R.id.recycleView_bankcard);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("购买会员");

        mVipCenterPersenter = new VipCenterPersenter(this, this);
        mCreditCardListPersenter =  new CreditCardListPersenter(this, this);

        String buyContent = getString(R.string.buy_vip_content);

        SpannableStringBuilder spannableStringBuilderContent = new SpannableStringBuilder(buyContent);
        ForegroundColorSpan foregroundColorSpanContent = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.redColor));
        spannableStringBuilderContent.setSpan(foregroundColorSpanContent, buyContent.length() - 4, buyContent.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvVipCenterBuyContent.setText(spannableStringBuilderContent);

        String buyDiscounts = getString(R.string.buy_vip_discounts);
        SpannableStringBuilder spannableStringBuilderDiscounts = new SpannableStringBuilder(buyDiscounts);
        ForegroundColorSpan foregroundColorSpanDiscounts = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.redColor));
        spannableStringBuilderDiscounts.setSpan(foregroundColorSpanDiscounts, buyDiscounts.length() - 2, buyDiscounts.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvVipCenterBuyDiscounts.setText(spannableStringBuilderDiscounts);

        mNormalBeanList = new ArrayList<>();
        mVipBeanList = new ArrayList<>();
        mBankFeeAdapter = new BankFeeAdapter(mNormalBeanList, mVipBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewBankcard.setAdapter(mBankFeeAdapter);
        mRecycleViewBankcard.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnVipCenterImmediatelyBuy.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_vip_center_immediately_buy:
                mCreditCardListPersenter.creditCardList(true);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mVipCenterPersenter.queryBankFee();
    }


    @Override
    public void getBankFeeList(List<BankFeeBean.DataBean> data) {
        for (int i = 0; i < data.size(); i++) {
            BankFeeBean.DataBean dataBean = data.get(i);
            if (dataBean.getVipLevel() == 0) {
                mNormalBeanList.add(dataBean);
            } else {
                mVipBeanList.add(dataBean);
            }
        }
        mBankFeeAdapter.notifyDataSetChanged();
    }

    @Override
    public void buyVipSuccess(String openUrl) {

        Bundle bundle = new Bundle();
        bundle.putString("url",openUrl);
        bundle.putString("title","升级VIP");
        startActivity(X5WebViewActivity.class,bundle);
        finish();
    }

    @Override
    public void getCreditCardList(List<CreditCardBean.DataBean> list) {
        if (list.size() <= 0) {
            ToastUtils.showToast("暂未绑定交易卡");
            return;
        }
        bankName = list.get(0).getBankName();
        accountNumber = list.get(0).getAccountNumber();
        popBuyVip(bankName, accountNumber);
    }

    private void popBuyVip(String bankName, final String accountNumber) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_buy_vip, null);

        ImageView mImgPopBuyVipClose = view.findViewById(R.id.img_pop_buy_vip_close);
        final TextView mTvPopBuyVipPrice = view.findViewById(R.id.tv_pop_buy_vip_price);
        TextView mTvPopBuyVipSelectcard = view.findViewById(R.id.tv_pop_buy_vip_selectcard);
        Button mBtnPopBuyVipImmediatelyPay = view.findViewById(R.id.btn_pop_buy_vip_immediately_pay);
        mTvPopBuyVipPrice.setText("99.00");
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
