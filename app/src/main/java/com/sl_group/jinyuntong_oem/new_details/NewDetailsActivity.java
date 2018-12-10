package com.sl_group.jinyuntong_oem.new_details;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MessageDetailsBean;
import com.sl_group.jinyuntong_oem.message_details.persenter.MessageDetailsPersenter;
import com.sl_group.jinyuntong_oem.message_details.view.MessageDetailsView;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 马天 on 2018/11/25.
 * description：消息详情
 */
public class NewDetailsActivity extends BaseActivity implements MessageDetailsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvNewDetailsTitle;
    private TextView mTvNewDetailsDate;
    private TextView mTvNewDetailsContent;

    @Override
    public int bindLayout() {
        return R.layout.activity_new_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvNewDetailsTitle = findViewById(R.id.tv_new_details_title);
        mTvNewDetailsDate = findViewById(R.id.tv_new_details_date);
        mTvNewDetailsContent = findViewById(R.id.tv_new_details_content);
    }

    @Override
    public void initData() {
        //标题
        mTvActionbarTitle.setText("消息详情");
        //信息详情persenter
        MessageDetailsPersenter messageDetailsPersenter = new MessageDetailsPersenter(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //信息ID
            String messageId = bundle.getString("messageId", "");
            //是否已读
            String isReady = bundle.getString("isReady", "");
            //如果消息ID是null，证明是直接通过推送点进来的
            if (StringUtils.isEmpty(messageId)) {
                String extract = bundle.getString(JPushInterface.EXTRA_EXTRA);
                try {
                    org.json.JSONObject j = new org.json.JSONObject(extract);
                    mTvNewDetailsTitle.setText(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                    mTvNewDetailsDate.setText(j.getString("time"));
                    mTvNewDetailsContent.setText(bundle.getString(JPushInterface.EXTRA_ALERT));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }
            //查询信息详情
            messageDetailsPersenter.newDetails(messageId, isReady, "");
        }

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

    @Override
    public void messageDetailsSuccess(MessageDetailsBean.DataBean data) {
        mTvNewDetailsTitle.setText(data.getTitle());
        mTvNewDetailsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(data.getCreateDate()));
        mTvNewDetailsContent.setText(data.getContent());
    }
}
