package com.xinyilian.text.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.bean.UpVipBrokerageBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：升级VIP奖励适配器
 */
public class UpVipBrokerageAdapter extends RecyclerView.Adapter<UpVipBrokerageAdapter.ViewHolder> {
    private List<UpVipBrokerageBean.DataBean.ResultListBean> mBeanList;

    public UpVipBrokerageAdapter(List<UpVipBrokerageBean.DataBean.ResultListBean> beanList) {
        mBeanList = beanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upvip_brokerage, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UpVipBrokerageBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        holder.tvItemUpvipBrokerageMoney.setText(String.format(Locale.CHINA,"%.2f",dataBean.getAmt()));
        holder.tvItemUpvipBrokerageDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(dataBean.getCreatedDate()));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("intoType","10");
//                bundle.putString("logId",String.valueOf(dataBean.getLogId()));
//                Intent intent = new Intent(mActivity,DealBrokerageDetailsBean.class);
//                intent.putExtras(bundle);
//                mActivity.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemUpvipBrokerageDate;
        private TextView tvItemUpvipBrokerageMoney;

        ViewHolder(View itemView) {
            super(itemView);
            tvItemUpvipBrokerageDate = itemView.findViewById(R.id.tv_item_upvip_brokerage_date);
            tvItemUpvipBrokerageMoney = itemView.findViewById(R.id.tv_item_upvip_brokerage_money);
        }
    }
}
