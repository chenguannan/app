package com.sl_group.jinyuntong_oem.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.bean.CreditCardBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/15.
 * description：银行卡适配器
 */
public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private List<CreditCardBean.DataBean> mBeanList;

    public CreditCardAdapter(List<CreditCardBean.DataBean> beanList) {
        mBeanList = beanList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CreditCardBean.DataBean dataBean = mBeanList.get(position);
        holder.tvBankcardName.setText(dataBean.getBankName());
        holder.tvBankcardCardnumber.setText(dataBean.getAccountNumber());
        holder.tvBankcardBanktype.setText(dataBean.getCardType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnItemClickListener.onItemClick(v,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBankcardName;
        private TextView tvBankcardCardnumber;
        private TextView tvBankcardBanktype;


        ViewHolder(View itemView) {
            super(itemView);
            tvBankcardName = itemView.findViewById(R.id.tv_bankcard_name);
            tvBankcardCardnumber = itemView.findViewById(R.id.tv_bankcard_cardnumber);
            tvBankcardBanktype = itemView.findViewById(R.id.tv_bankcard_banktype);
        }
    }

//    private OnItemClickListener mOnItemClickListener;
//
//
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//    }
}
