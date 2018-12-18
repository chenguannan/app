package com.xinyilian.text.notice_details;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.MessageDetailsBean;
import com.xinyilian.text.message_details.persenter.MessageDetailsPersenter;
import com.xinyilian.text.message_details.view.MessageDetailsView;
import com.xinyilian.text.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/25.
 * description：公告详情
 */
public class NoticeDetailsActivity extends BaseActivity implements MessageDetailsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvNoticeDetailsTitle;
    private TextView mTvNoticeDetailsDate;
    private TextView mTvNoticeDetailsContent;

    @Override
    public int bindLayout() {
        return R.layout.activity_notice_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvNoticeDetailsTitle = findViewById(R.id.tv_notice_details_title);
        mTvNoticeDetailsDate = findViewById(R.id.tv_notice_details_date);
        mTvNoticeDetailsContent = findViewById(R.id.tv_notice_details_content);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("公告详情");
        //信息persenter
        MessageDetailsPersenter messageDetailsPersenter = new MessageDetailsPersenter(this, this);
        //传递的bundle如果不是空，正常查询，是空提示错误
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String noticeId = bundle.getString("noticeId", "");
            String isReady = bundle.getString("isReady", "");
            messageDetailsPersenter.newDetails("", isReady, noticeId);
            return;
        }
        ToastUtils.showToast("查询失败");

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    /**
      *
      * @param data 信息详情对象
      */
    @Override
    public void messageDetailsSuccess(MessageDetailsBean.DataBean data) {
        mTvNoticeDetailsTitle.setText(data.getTitle());
        mTvNoticeDetailsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(data.getCreateDate()));
        mTvNoticeDetailsContent.setText(data.getContent());
    }
}
