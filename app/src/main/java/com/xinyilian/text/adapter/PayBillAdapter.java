package com.xinyilian.text.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.bean.PayBillBean;
import com.xinyilian.text.pay_bill_details.PayBillDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：支付账单适配器
 */
public class PayBillAdapter extends RecyclerView.Adapter<PayBillAdapter.ViewHolder> {
    private List<PayBillBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public PayBillAdapter(List<PayBillBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_bill, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PayBillBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        final String bizOrderNumber = dataBean.getBizOrderNumber();
        final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(dataBean.getCreatedDate());
        final String state = dataBean.getState();
        final String payMoney = String.format(Locale.CHINA,"%.2f", dataBean.getSrcAmt());
        final String gatherMerchant = dataBean.getShortName();
        switch (state) {
            case "0":
                holder.tvPayBillState.setText("交易处理中");
                break;
            case "1":
                holder.tvPayBillState.setText("交易成功");
                holder.tvPayBillState.setTextColor(mActivity.getResources().getColor(R.color.successColor));
                break;
            case "9":
                holder.tvPayBillState.setText("交易关闭");
                holder.tvPayBillState.setTextColor(mActivity.getResources().getColor(R.color.redColor));
                break;
        }
        holder.tvPayBillOrder.setText(bizOrderNumber);
        holder.tvPayBillDate.setText(date);
        holder.tvPayBillPaymoney.setText("¥" + payMoney);
        holder.tvPayBillGatherMerchant.setText(gatherMerchant);
        holder.tvPayBillQueryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PayBillDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                bundle.putString("bizOrderNumber", bizOrderNumber);
                bundle.putString("payAccount", dataBean.getPayAccount());
                bundle.putString("gatherMerchant",gatherMerchant);
                bundle.putString("payMoney", payMoney);
                bundle.putString("state", state);
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPayBillOrder;
        private TextView tvPayBillDate;
        private TextView tvPayBillState;
        private TextView tvPayBillPaymoney;
        private TextView tvPayBillGatherMerchant;
        private TextView tvPayBillQueryDetails;


        ViewHolder(View itemView) {
            super(itemView);
            tvPayBillOrder = itemView.findViewById(R.id.tv_pay_bill_order);
            tvPayBillDate = itemView.findViewById(R.id.tv_pay_bill_date);
            tvPayBillState = itemView.findViewById(R.id.tv_pay_bill_state);
            tvPayBillPaymoney = itemView.findViewById(R.id.tv_pay_bill_paymoney);
            tvPayBillGatherMerchant = itemView.findViewById(R.id.tv_pay_bill_gather_merchant);
            tvPayBillQueryDetails = itemView.findViewById(R.id.tv_pay_bill_query_details);
        }
    }
}
