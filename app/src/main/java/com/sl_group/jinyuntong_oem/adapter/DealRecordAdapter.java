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

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.bean.DealRecordBean;
import com.sl_group.jinyuntong_oem.deal_record_details.DealRecordDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：交易记录，佣金，适配器
 */
public class DealRecordAdapter extends RecyclerView.Adapter<DealRecordAdapter.ViewHolder> {
    private List<DealRecordBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public DealRecordAdapter(List<DealRecordBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DealRecordBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        holder.tvItemDealRecordMoney.setText(String.format(Locale.CHINA,"%.2f",dataBean.getAmt()));
        holder.tvItemDealRecordDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(dataBean.getCreatedDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("intoType",CommonSet.INTOTYPE_DEAL);
                bundle.putString("logId",String.valueOf(dataBean.getLogId()));
                Intent intent = new Intent(mActivity,DealRecordDetailsActivity.class);
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
        private TextView tvItemDealRecordMoney;
        private TextView tvItemDealRecordDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvItemDealRecordMoney = itemView.findViewById(R.id.tv_item_deal_record_money);
            tvItemDealRecordDate = itemView.findViewById(R.id.tv_item_deal_record_date);

        }
    }
}
