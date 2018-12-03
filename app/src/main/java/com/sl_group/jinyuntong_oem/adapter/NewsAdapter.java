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
import com.sl_group.jinyuntong_oem.bean.NewsBean;
import com.sl_group.jinyuntong_oem.new_details.NewDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：收款账单适配器
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public NewsAdapter(List<NewsBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        holder.tvNewsTitle.setText(dataBean.getTitle());
        holder.tvNewsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(dataBean.getCreateDate()));
        holder.tvNewsContent.setText(dataBean.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("messageId",String.valueOf(dataBean.getMessageId()));
                bundle.putString("isRead",dataBean.getIsReady());
                Intent intent = new Intent(mActivity,NewDetailsActivity.class);
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
        private TextView tvNewsTitle;
        private TextView tvNewsDate;
        private TextView tvNewsContent;

        ViewHolder(View itemView) {
            super(itemView);
            tvNewsTitle = itemView.findViewById(R.id.tv_news_title);
            tvNewsDate = itemView.findViewById(R.id.tv_news_date);
            tvNewsContent = itemView.findViewById(R.id.tv_news_content);
        }
    }
}
