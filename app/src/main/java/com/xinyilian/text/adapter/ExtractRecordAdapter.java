package com.xinyilian.text.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyilian.text.CommonSet;
import com.xinyilian.text.R;
import com.xinyilian.text.bean.ExtractRecordBean;
import com.xinyilian.text.extract_record_details.ExtractRecordDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：提现记录，累计提现适配器
 */
public class ExtractRecordAdapter extends RecyclerView.Adapter<ExtractRecordAdapter.ViewHolder> {
    private List<ExtractRecordBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public ExtractRecordAdapter(List<ExtractRecordBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extract_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExtractRecordBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        holder.tvItemExtractRecordMoney.setText(String.format(Locale.CHINA,"%.2f",dataBean.getSrcAmt()));
        holder.tvItemExtractRecordDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(dataBean.getCreatedDate()));
        //提现状态
        switch (dataBean.getStatus()) {
            case "s":
                holder.tvItemExtractRecordState.setTextColor(ContextCompat.getColor(mActivity, R.color.successColor));
                holder.tvItemExtractRecordState.setText("提现成功");
                break;
            case "a":
                holder.tvItemExtractRecordState.setTextColor(ContextCompat.getColor(mActivity, R.color.ingColor));
                holder.tvItemExtractRecordState.setText("审核中");
                break;
            case "c":
                holder.tvItemExtractRecordState.setTextColor(ContextCompat.getColor(mActivity, R.color.failColor));
                holder.tvItemExtractRecordState.setText("提现失败");
                break;
            case "i":
                holder.tvItemExtractRecordState.setTextColor(ContextCompat.getColor(mActivity, R.color.failColor));
                holder.tvItemExtractRecordState.setText("冻结");
                break;
            case "p":
                holder.tvItemExtractRecordState.setTextColor(ContextCompat.getColor(mActivity, R.color.ingColor));
                holder.tvItemExtractRecordState.setText("处理中");
                break;

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("intoType",CommonSet.INTOTYPE_EXTRACT);
                bundle.putString("logId",String.valueOf(dataBean.getLogId()));
                Intent intent = new Intent(mActivity,ExtractRecordDetailsActivity.class);
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
        private TextView tvItemExtractRecordMoney;
        private TextView tvItemExtractRecordDate;
        private TextView tvItemExtractRecordState;


        ViewHolder(View itemView) {
            super(itemView);
            tvItemExtractRecordMoney = itemView.findViewById(R.id.tv_item_extract_record_money);
            tvItemExtractRecordDate = itemView.findViewById(R.id.tv_item_extract_record_date);
            tvItemExtractRecordState = itemView.findViewById(R.id.tv_item_extract_record_state);

        }
    }
}
