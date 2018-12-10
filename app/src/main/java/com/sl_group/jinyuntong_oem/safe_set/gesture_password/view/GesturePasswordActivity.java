package com.sl_group.jinyuntong_oem.safe_set.gesture_password.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.persenter.GesturePasswordPersenter;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.view.SetGesturePasswordActivity;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：手势密码
 */
public class GesturePasswordActivity extends BaseActivity implements GesturePasswordView, MerchantinfoView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvGetturePasswordOpenClose;
    private View mVGesturePassword;
    private TextView mTvGesturePasswordChange;
    private GesturePasswordPersenter gesturePasswordPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private String gesturePwd;

    @Override
    protected void onStart() {
        super.onStart();

        gesturePasswordPersenter = new GesturePasswordPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);

        mMerchantinfoPersenter.merchantInfo();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_gesture_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvGetturePasswordOpenClose = findViewById(R.id.tv_getture_password_open_close);
        mVGesturePassword = findViewById(R.id.v_gesture_password);
        mTvGesturePasswordChange = findViewById(R.id.tv_gesture_password_change);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText(getString(R.string.gesture_password));

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvGetturePasswordOpenClose.setOnClickListener(this);
        mTvGesturePasswordChange.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_getture_password_open_close:
                if (StringUtils.isEmpty(gesturePwd)) {
                    startActivity(SetGesturePasswordActivity.class);
                } else {
                    gesturePasswordPersenter.clearGesturePassword();
                }
                break;
            case R.id.tv_gesture_password_change:
                Bundle bundle = new Bundle();
                bundle.putString("action","changeGesturePassword");
                startActivity(SMSActivity.class,bundle);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resetGesturePassword() {
        mTvGetturePasswordOpenClose.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.close_icon), null);
        mTvGesturePasswordChange.setVisibility(View.GONE);
        mVGesturePassword.setVisibility(View.GONE);
        gesturePwd = null;
    }

    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        gesturePwd = dataBean.getGesturePassword();
        if (!StringUtils.isEmpty(dataBean.getGesturePassword())) {
            mTvGetturePasswordOpenClose.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.open_icon), null);
            mTvGesturePasswordChange.setVisibility(View.VISIBLE);
            mVGesturePassword.setVisibility(View.VISIBLE);
        } else {
            mTvGetturePasswordOpenClose.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.close_icon), null);
            mTvGesturePasswordChange.setVisibility(View.GONE);
            mVGesturePassword.setVisibility(View.GONE);
        }
    }
}
