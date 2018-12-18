package com.xinyilian.text.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.bean.CreditCardBean;
import com.xinyilian.text.utils.StringUtils;

import java.util.List;

/**
 * 清单列表adapter
 * <p>
 * Created by DavidChen on 2018/5/30.
 */

public class InventoryAdapter extends BaseRecyclerViewAdapter<CreditCardBean.DataBean> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapter(Context context, List<CreditCardBean.DataBean> data) {
        super(context, data, R.layout.item_credit_card);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, CreditCardBean.DataBean bean, final int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
        ((TextView) holder.getView(R.id.tv_bankcard_name)).setText(bean.getBankName());
        ((TextView) holder.getView(R.id.tv_bankcard_cardnumber)).setText(StringUtils.getStarString(bean.getAccountNumber(),6,bean.getAccountNumber().length()-4));

        ((TextView) holder.getView(R.id.tv_bankcard_banktype)).setText(bean.getCardType());
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
