package com.sl_group.jinyuntong_oem.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.bean.BankFeeBean;

import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：费率适配器
 */
public class GatherRateAdapter extends RecyclerView.Adapter<GatherRateAdapter.ViewHolder> {
    private List<BankFeeBean.DataBean> mBeanList;

    public GatherRateAdapter(List<BankFeeBean.DataBean> beanList) {
        mBeanList = beanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gather_fee, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            BankFeeBean.DataBean dataBean = mBeanList.get(position);
            holder.tvItemGatherFeeBankname.setText(dataBean.getBankName());
            holder.tvItemGatherFeeFee.setText(String.format(Locale.CHINA,"%.2f",dataBean.getFastpayFee())+"%");

    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemGatherFeeBankname;
        private TextView tvItemGatherFeeFee;

        ViewHolder(View itemView) {
            super(itemView);

            tvItemGatherFeeBankname = itemView.findViewById(R.id.tv_item_gather_fee_bankname);
            tvItemGatherFeeFee = itemView.findViewById(R.id.tv_item_gather_fee_fee);
        }
    }
}
