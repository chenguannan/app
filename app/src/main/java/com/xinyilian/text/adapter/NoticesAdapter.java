package com.xinyilian.text.adapter;

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
import com.xinyilian.text.bean.MessagesBean;
import com.xinyilian.text.notice_details.NoticeDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：消息适配器
 */
public class NoticesAdapter extends RecyclerView.Adapter<NoticesAdapter.ViewHolder> {
    private List<MessagesBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public NoticesAdapter(List<MessagesBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notices, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MessagesBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        holder.tvNewsTitle.setText(dataBean.getTitle());
        holder.tvNewsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(dataBean.getCreateDate()));
        holder.tvNewsContent.setText(dataBean.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("noticeId",String.valueOf(dataBean.getNoticeId()));
                bundle.putString("isReady",dataBean.getIsReady());
                Intent intent = new Intent(mActivity,NoticeDetailsActivity.class);
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
            tvNewsTitle = itemView.findViewById(R.id.tv_notices_title);
            tvNewsDate = itemView.findViewById(R.id.tv_notices_date);
            tvNewsContent = itemView.findViewById(R.id.tv_notices_content);
        }
    }
}
