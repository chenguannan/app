package com.xinyilian.text.vip_center;

import android.annotation.SuppressLint;
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

import com.xinyilian.text.R;
import com.xinyilian.text.adapter.BankFeeAdapter;
import com.xinyilian.text.bank_fee.persenter.BankFeePersenter;
import com.xinyilian.text.bank_fee.view.BankFeeView;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.BankFeeBean;
import com.xinyilian.text.bean.CreditCardBean;
import com.xinyilian.text.buy_vip.persenter.BuyVipPersenter;
import com.xinyilian.text.buy_vip.view.BuyVipView;
import com.xinyilian.text.creditcard.persenter.CreditCardListPersenter;
import com.xinyilian.text.creditcard.view.CreditCardListActivity;
import com.xinyilian.text.creditcard.view.CreditCardListView;
import com.xinyilian.text.utils.DisplayUtils;
import com.xinyilian.text.utils.PopupWindowUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.ToastUtils;
import com.xinyilian.text.web.X5WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/19.
 * description：vip中心
 */
public class VipCenterActivity extends BaseActivity implements BankFeeView, BuyVipView, CreditCardListView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvVipCenterBuyContent;
    private TextView mTvVipCenterBuyDiscounts;
    private Button mBtnVipCenterImmediatelyBuy;
    private RecyclerView mRecycleViewBankcard;
    //银行费率
    private BankFeePersenter mBankFeePersenter;
    //购买VIP
    private BuyVipPersenter mBuyVipPersenter;
    //信用卡列表
    private CreditCardListPersenter mCreditCardListPersenter;
    //普通等级费率集合
    private List<BankFeeBean.DataBean> mNormalBeanList;
    //vip等级费率集合
    private List<BankFeeBean.DataBean> mVipBeanList;
    //银行费率适配器
    private BankFeeAdapter mBankFeeAdapter;
    //结算卡号
    private String accountNumber;
    //银行名称
    private String bankName;
    //支付弹窗
    private PopupWindow popupWindow;

    @Override
    protected void onStart() {
        super.onStart();
        //是否是支付选卡
        boolean isPaySelectCard = (boolean) SPUtil.get(VipCenterActivity.this, "isPaySelectCard", false);
        if (isPaySelectCard) {
            accountNumber = (String) SPUtil.get(VipCenterActivity.this, "payAccountNumber", "");
            bankName = (String) SPUtil.get(VipCenterActivity.this, "payBankName", "");
            //购买VIP弹窗
            popBuyVip(bankName, accountNumber);
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
        mRecycleViewBankcard = findViewById(R.id.recycleView_bankcard);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("购买会员");

        mBankFeePersenter = new BankFeePersenter(this, this);
        mBuyVipPersenter = new BuyVipPersenter(this, this);
        mCreditCardListPersenter = new CreditCardListPersenter(this, this);
        //购买内容    这里需要显示两张颜色
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

        //初始化集合
        mNormalBeanList = new ArrayList<>();
        mVipBeanList = new ArrayList<>();
        //初始化适配器
        mBankFeeAdapter = new BankFeeAdapter(mNormalBeanList, mVipBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //绑定适配器
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
        mBankFeePersenter.bankFee();
    }


    /**
     * 查询银行费率成功
     *
     * @param data 银行费率集合
     */
    @Override
    public void bankFeeSuccess(List<BankFeeBean.DataBean> data) {
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

    /**
     * @param openUrl 交易页面
     */
    @Override
    public void buyVipSuccess(String openUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url", openUrl);
        bundle.putString("title", "升级VIP");
        startActivity(X5WebViewActivity.class, bundle);
        finish();
    }

    /**
     * @param list 信用卡集合
     */
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

    /**
     * @param bankName      银行名称
     * @param accountNumber 结算卡号
     */
    @SuppressLint("SetTextI18n")
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
        // 选择交易卡
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
                //立即支付
                mBuyVipPersenter.buyVip(accountNumber);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtil.remove(this, "isPaySelectCard");
        SPUtil.remove(this, "payAccountNumber");
        SPUtil.remove(this, "payBankName");

    }

}
