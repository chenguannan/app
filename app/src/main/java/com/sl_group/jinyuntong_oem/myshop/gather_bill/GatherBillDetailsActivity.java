package com.sl_group.jinyuntong_oem.myshop.gather_bill;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/20.
 * description：收款详情
 */
public class GatherBillDetailsActivity extends BaseActivity {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvGatherBillDetailsDate;
    private TextView mTvGatherBillDetailsOrder;
    private TextView mTvGatherBillDetailsPaycard;
    private TextView mTvGatherBillDetailsPaypeople;
    private TextView mTvGatherBillDetailsGatherMoney;
    private TextView mTvGatherBillDetailsPayfee;
    private TextView mTvGatherBillDetailsExtractfee;
    private TextView mTvGatherBillDetailsRealmoney;
    private TextView mTvGatherBillDetailsDealstate;
    private TextView mTvGatherBillDetailsSettlecard;
    private TextView mTvGatherBillDetailsSettleState;

    @Override
    public int bindLayout() {
        return R.layout.activity_gather_bill_details;
    }

    @Override
    public void initView(View view) {

        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);

        mTvGatherBillDetailsDate = findViewById(R.id.tv_gather_bill_details_date);
        mTvGatherBillDetailsOrder = findViewById(R.id.tv_gather_bill_details_order);
        mTvGatherBillDetailsPaycard = findViewById(R.id.tv_gather_bill_details_paycard);
        mTvGatherBillDetailsPaypeople = findViewById(R.id.tv_gather_bill_details_paypeople);
        mTvGatherBillDetailsGatherMoney = findViewById(R.id.tv_gather_bill_details_gather_money);
        mTvGatherBillDetailsPayfee = findViewById(R.id.tv_gather_bill_details_payfee);
        mTvGatherBillDetailsExtractfee = findViewById(R.id.tv_gather_bill_details_extractfee);
        mTvGatherBillDetailsRealmoney = findViewById(R.id.tv_gather_bill_details_realmoney);
        mTvGatherBillDetailsDealstate = findViewById(R.id.tv_gather_bill_details_dealstate);
        mTvGatherBillDetailsSettlecard = findViewById(R.id.tv_gather_bill_details_settlecard);
        mTvGatherBillDetailsSettleState = findViewById(R.id.tv_gather_bill_details_settle_state);

    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("账单详情");
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mTvGatherBillDetailsDate.setText(bundle.getString("date"));
            mTvGatherBillDetailsOrder.setText(bundle.getString("bizOrderNumber"));
            mTvGatherBillDetailsPaycard.setText(bundle.getString("payAccount"));
            mTvGatherBillDetailsPaypeople.setText(bundle.getString("payAccountName"));
            mTvGatherBillDetailsGatherMoney.setText("¥"+bundle.getString("gatherMoney"));
            mTvGatherBillDetailsPayfee.setText("¥"+bundle.getString("dealfee"));
            mTvGatherBillDetailsExtractfee.setText("¥"+bundle.getString("extractfee"));
            mTvGatherBillDetailsRealmoney.setText("¥"+bundle.getString("realmoney"));
            mTvGatherBillDetailsDealstate.setText(bundle.getString("realstate"));
            mTvGatherBillDetailsSettlecard.setText(bundle.getString("settleCard"));
            mTvGatherBillDetailsSettleState.setText(bundle.getString("settleState"));
            switch (bundle.getString("settleState")){
                case "a":
                    mTvGatherBillDetailsSettleState.setText("");
                    break;
                case "p":
                    mTvGatherBillDetailsSettleState.setText("结算处理中");
                    break;
                case "s":
                    mTvGatherBillDetailsSettleState.setText("结算成功");
                    break;
                case "c":
                    mTvGatherBillDetailsSettleState.setText("结算失败");
                    break;

            }
            switch (bundle.getString("realstate")){
                case "0":
                    mTvGatherBillDetailsDealstate.setText("交易处理中");
                    break;
                case "1":
                    mTvGatherBillDetailsDealstate.setText("交易成功");
                    break;
                case "9":
                    mTvGatherBillDetailsDealstate.setText("交易关闭");
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
