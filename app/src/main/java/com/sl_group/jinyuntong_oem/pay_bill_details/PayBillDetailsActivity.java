package com.sl_group.jinyuntong_oem.pay_bill_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：付款详情
 */
public class PayBillDetailsActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvPayBillDetailsDate;
    private TextView mTvPayBillDetailsOrder;
    private TextView mTvPayBillDetailsPaycard;
    private TextView mTvPayBillDetailsGatherMerchant;
    private TextView mTvPayBillDetailsPayMoney;
    private TextView mTvPayBillDetailsDealState;


    @Override
    public int bindLayout() {
        return R.layout.activity_pay_bill_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvPayBillDetailsDate = findViewById(R.id.tv_pay_bill_details_date);
        mTvPayBillDetailsOrder = findViewById(R.id.tv_pay_bill_details_order);
        mTvPayBillDetailsPaycard = findViewById(R.id.tv_pay_bill_details_paycard);
        mTvPayBillDetailsGatherMerchant = findViewById(R.id.tv_pay_bill_details_gather_merchant);
        mTvPayBillDetailsPayMoney = findViewById(R.id.tv_pay_bill_details_pay_money);
        mTvPayBillDetailsDealState = findViewById(R.id.tv_pay_bill_details_deal_state);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("账单详情");
        //获取传递的数据
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            //交易时间
            mTvPayBillDetailsDate.setText(bundle.getString("date",""));
            //订单号
            mTvPayBillDetailsOrder.setText(bundle.getString("bizOrderNumber",""));
            //付款账单，前6后4
            String payAccount = bundle.getString("payAccount","");
            if (!StringUtils.isEmpty(payAccount)){
                mTvPayBillDetailsPaycard.setText(StringUtils.getStarString(payAccount,6,payAccount.length()-4));
            }
            //收款商户
            mTvPayBillDetailsGatherMerchant.setText(bundle.getString("gatherMerchant",""));
            //付款金额
            mTvPayBillDetailsPayMoney.setText("¥"+bundle.getString("payMoney",""));
            //付款状态
            switch (bundle.getString("state","")){
                case "0":
                    mTvPayBillDetailsDealState.setText("交易处理中");
                    break;
                case "1":
                    mTvPayBillDetailsDealState.setText("交易成功");
                    break;
                case "9":
                    mTvPayBillDetailsDealState.setText("交易关闭");
                    break;
            }

        }else {
            ToastUtils.showToast("查询失败");
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
}
