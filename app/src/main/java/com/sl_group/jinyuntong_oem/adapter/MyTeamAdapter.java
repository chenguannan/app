package com.sl_group.jinyuntong_oem.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.bean.MyTeamBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/15.
 * description：收款账单适配器
 */
public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder> {
    private List<MyTeamBean.DataBean.ResultListBean> mBeanList;
    private Activity mActivity;

    public MyTeamAdapter(List<MyTeamBean.DataBean.ResultListBean> beanList, Activity activity) {
        mBeanList = beanList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_team, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyTeamBean.DataBean.ResultListBean dataBean = mBeanList.get(position);
        switch (dataBean.getVipLevel()){
            case 0:
                holder.imgItemMyTeamIcon.setImageBitmap(BitmapFactory.decodeResource(mActivity.getResources(),R.mipmap.center_icon_vip));
                holder.tvItemMyTeamLevel.setText(dataBean.getLevelName());
                break;
            case 1:
                holder.imgItemMyTeamIcon.setImageBitmap(BitmapFactory.decodeResource(mActivity.getResources(),R.mipmap.center_icon_ldz));
                holder.tvItemMyTeamLevel.setText(dataBean.getLevelName());
                break;
        }
        holder.tvItemMyTeamName.setText(dataBean.getMerchantName());
        holder.tvItemMyTeamTel.setText(dataBean.getCellPhone());
        holder.tvItemMyTeamDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",Locale.getDefault()).format(dataBean.getCreatedDate()));

        //实名状态
        String qualifiedState = dataBean.getQualifiedState();
        String isRealAuth = dataBean.getIsRealAuth();
        switch (qualifiedState) {
            case "N":
                holder.tvItemMyTeamState.setText("未通过");
                holder.tvItemMyTeamState.setTextColor(ContextCompat.getColor(mActivity, R.color.failColor));
                break;
            case "Y":
                holder.tvItemMyTeamState.setText("已通过");
                holder.tvItemMyTeamState.setTextColor(ContextCompat.getColor(mActivity, R.color.successColor));
                break;
            case "a":
                if ("N".equals(isRealAuth)) {
                    holder.tvItemMyTeamState.setText("未实名");
                    holder.tvItemMyTeamState.setTextColor(ContextCompat.getColor(mActivity, R.color.failColor));
                } else if ("Y".equals(isRealAuth)) {
                    holder.tvItemMyTeamState.setText("已实名");
                    holder.tvItemMyTeamState.setTextColor(ContextCompat.getColor(mActivity, R.color.successColor));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItemMyTeamIcon;
        private TextView tvItemMyTeamName;
        private TextView tvItemMyTeamTel;
        private TextView tvItemMyTeamLevel;
        private TextView tvItemMyTeamDate;
        private TextView tvItemMyTeamState;


        ViewHolder(View itemView) {
            super(itemView);
            imgItemMyTeamIcon = itemView.findViewById(R.id.img_item_my_team_icon);
            tvItemMyTeamName = itemView.findViewById(R.id.tv_item_my_team_name);
            tvItemMyTeamTel = itemView.findViewById(R.id.tv_item_my_team_tel);
            tvItemMyTeamLevel = itemView.findViewById(R.id.tv_item_my_team_level);
            tvItemMyTeamDate = itemView.findViewById(R.id.tv_item_my_team_date);
            tvItemMyTeamState = itemView.findViewById(R.id.tv_item_my_team_state);
        }
    }
}
