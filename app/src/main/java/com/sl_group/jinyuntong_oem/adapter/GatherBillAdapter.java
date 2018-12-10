package com.sl_group.jinyuntong_oem.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.bean.GatherBillBean;
import com.sl_group.jinyuntong_oem.gather_bill_details.GatherBillDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：收款账单适配器
 */
public class GatherBillAdapter extends RecyclerView.Adapter<GatherBillAdapter.ViewHolder> {
    private List<GatherBillBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public GatherBillAdapter(List<GatherBillBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gather_bill, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GatherBillBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        final String bizOrderNumber = dataBean.getBizOrderNumber();
        final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(dataBean.getCreatedDate());
        final String state = dataBean.getState();
        final String dealfee = String.format(Locale.CHINA,"%.2f", dataBean.getDiscountFeeAmt());
        final String extractfee = String.format(Locale.CHINA,"%.2f", dataBean.getExtraFee());
        final String gatherMoney = String.format(Locale.CHINA,"%.2f", dataBean.getSrcAmt());
        final String realmoney = String.format(Locale.CHINA,"%.2f", dataBean.getSrcAmt() - dataBean.getDiscountFeeAmt() - dataBean.getExtraFee());

        switch (state){
            case "0":
                holder.mTvGatherBillState.setText("交易处理中");
                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.redColor));
                break;
            case "1":
                holder.mTvGatherBillState.setText("交易成功");
                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.successColor));
                break;
            case "9":
                holder.mTvGatherBillState.setText("交易关闭");
                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.redColor));
                break;
        }
//        switch (state) {
//            case "a":
//                holder.mTvGatherBillState.setText("初始状态");
//                break;
//            case "p":
//                holder.mTvGatherBillState.setText("处理中");
//                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.redColor));
//                break;
//            case "s":
//                holder.mTvGatherBillState.setText("成功");
//                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.successColor));
//                break;
//            case "c":
//                holder.mTvGatherBillState.setText("失败");
//                holder.mTvGatherBillState.setTextColor(mActivity.getResources().getColor(R.color.redColor));
//                break;
//
//        }
        holder.mTvGatherBillOrder.setText(bizOrderNumber);
        holder.mTvGatherBillDate.setText(date);
        holder.mTvGatherBillDealfee.setText(String.format(mActivity.getResources().getString(R.string.rmb_fromat),dealfee));
        holder.mTvGatherBillExtractfee.setText(String.format(mActivity.getResources().getString(R.string.rmb_fromat),extractfee));
        holder.mTvGatherBillGatherMoney.setText(String.format(mActivity.getResources().getString(R.string.rmb_fromat),gatherMoney));
        holder.mTvGatherBillRealMoney.setText(String.format(mActivity.getResources().getString(R.string.rmb_fromat),realmoney));
        holder.mTvGatherBillQueryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, GatherBillDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                bundle.putString("bizOrderNumber", bizOrderNumber);
                bundle.putString("payAccount", dataBean.getPayAccount());
                bundle.putString("payAccountName", dataBean.getPayAccountName());
                bundle.putString("gatherMoney", gatherMoney);
                bundle.putString("dealfee", dealfee);
                bundle.putString("extractfee", extractfee);
                bundle.putString("realmoney", realmoney);
                bundle.putString("realstate", dataBean.getState());
                bundle.putString("settleCard", dataBean.getAccountNumber());
                bundle.putString("settleState", dataBean.getSettlementStatus());
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
        private TextView mTvGatherBillOrder;
        private TextView mTvGatherBillDate;
        private TextView mTvGatherBillState;
        private TextView mTvGatherBillDealfee;
        private TextView mTvGatherBillExtractfee;
        private TextView mTvGatherBillGatherMoney;
        private TextView mTvGatherBillRealMoney;
        private TextView mTvGatherBillQueryDetails;


        ViewHolder(View itemView) {
            super(itemView);
            mTvGatherBillOrder = itemView.findViewById(R.id.tv_gather_bill_order);
            mTvGatherBillDate = itemView.findViewById(R.id.tv_gather_bill_date);
            mTvGatherBillState = itemView.findViewById(R.id.tv_gather_bill_state);
            mTvGatherBillDealfee = itemView.findViewById(R.id.tv_gather_bill_dealfee);
            mTvGatherBillExtractfee = itemView.findViewById(R.id.tv_gather_bill_extractfee);
            mTvGatherBillGatherMoney = itemView.findViewById(R.id.tv_gather_bill_gather_money);
            mTvGatherBillRealMoney = itemView.findViewById(R.id.tv_gather_bill_real_money);
            mTvGatherBillQueryDetails = itemView.findViewById(R.id.tv_gather_bill_query_details);
        }
    }
}
