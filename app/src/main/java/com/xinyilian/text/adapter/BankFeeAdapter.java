package com.xinyilian.text.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.bean.BankFeeBean;

import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：费率适配器
 */
public class BankFeeAdapter extends RecyclerView.Adapter<BankFeeAdapter.ViewHolder> {
    private List<BankFeeBean.DataBean> mNormalBeanList;
    private List<BankFeeBean.DataBean> mVipBeanList;

    public BankFeeAdapter(List<BankFeeBean.DataBean> normalBeanList, List<BankFeeBean.DataBean> vipBeanList) {
        mNormalBeanList = normalBeanList;
        mVipBeanList = vipBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_fee, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mNormalBeanList.size()>=mVipBeanList.size()){
            BankFeeBean.DataBean normalDataBean = mNormalBeanList.get(position);
            holder.tvItemBankfeeBankname.setText(normalDataBean.getBankName());
            holder.tvItemBankfeeNormalFee.setText(String.format(Locale.CHINA,"%.2f",normalDataBean.getFastpayFee())+"%");
            for (int i = 0; i <mVipBeanList.size() ; i++) {
                if (mVipBeanList.get(i).getBankName().equals(holder.tvItemBankfeeBankname.getText().toString())){
                    holder.tvItemBankfeeVipFee.setText(String.format(Locale.CHINA,"%.2f",mVipBeanList.get(i).getFastpayFee())+"%");
                }
            }
        }else {
            BankFeeBean.DataBean vipDataBean = mVipBeanList.get(position);
            holder.tvItemBankfeeBankname.setText(vipDataBean.getBankName());
            holder.tvItemBankfeeNormalFee.setText(String.format(Locale.CHINA,"%.2f",vipDataBean.getFastpayFee())+"%");
            for (int i = 0; i <mNormalBeanList.size() ; i++) {
                if (mNormalBeanList.get(i).getBankName().equals(holder.tvItemBankfeeBankname.getText().toString())){
                    holder.tvItemBankfeeVipFee.setText(String.format(Locale.CHINA,"%.2f",mNormalBeanList.get(i).getFastpayFee())+"%");
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mNormalBeanList == null || mVipBeanList == null) {
            return 0;
        } else if (mNormalBeanList.size() >= mVipBeanList.size()) {
            return mNormalBeanList.size();
        } else {
            return mVipBeanList.size();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemBankfeeBankname;
        private TextView tvItemBankfeeNormalFee;
        private TextView tvItemBankfeeVipFee;


        ViewHolder(View itemView) {
            super(itemView);
            tvItemBankfeeBankname = itemView.findViewById(R.id.tv_item_bankfee_bankname);
            tvItemBankfeeNormalFee = itemView.findViewById(R.id.tv_item_bankfee_normal_fee);
            tvItemBankfeeVipFee = itemView.findViewById(R.id.tv_item_bankfee_vip_fee);

        }
    }
}
