package com.sl_group.jinyuntong_oem.new_details;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.NewDetailsBean;
import com.sl_group.jinyuntong_oem.new_details.persenter.NewDetailsPersenter;
import com.sl_group.jinyuntong_oem.new_details.view.NewDetailsView;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 马天 on 2018/11/25.
 * description：消息详情
 */
public class NewDetailsActivity extends BaseActivity implements NewDetailsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvNewDetailsTitle;
    private TextView mTvNewDetailsDate;
    private TextView mTvNewDetailsContent;
    private String messageId;
    private NewDetailsPersenter mNewDetailsPersenter;

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
        mTvActionbarTitle.setText("消息详情");

        mNewDetailsPersenter = new NewDetailsPersenter(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            messageId = bundle.getString("messageId", "");
            //如果消息ID是null，证明是直接通过推送点进来的
            if (StringUtils.isEmpty(messageId)) {
                //this必须为点击消息要跳转到页面的上下文。
                //            XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
                //            String customContent = clickedResult.getCustomContent();
                //            if (StringUtils.isEmpty(customContent)) {
                //                ToastUtils.showToast("网络异常，通知打开失败");
                //                finish();
                //                return;
                //            }
                //           String extract = bundle.getString(JPushInterface.EXTRA_EXTRA);
                //            PushNewsBean pushNewsBean = new Gson().fromJson(extract, PushNewsBean.class);
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
            mNewDetailsPersenter.newDetails(messageId, bundle.getString("isRead", ""));
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
    public void getNewDetails(NewDetailsBean.DataBean data) {
        mTvNewDetailsTitle.setText(data.getTitle());
        mTvNewDetailsDate.setText(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(data.getCreateDate()));
        mTvNewDetailsContent.setText(data.getContent());
    }
}
