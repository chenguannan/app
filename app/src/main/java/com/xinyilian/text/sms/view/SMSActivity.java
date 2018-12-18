package com.xinyilian.text.sms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.CommonSMSBean;
import com.xinyilian.text.gesture_change.view.GesturePasswordChangeActivity;
import com.xinyilian.text.gesture_set.persenter.GesturePasswordSetPersenter;
import com.xinyilian.text.gesture_set.view.GesturePasswordSetActivity;
import com.xinyilian.text.gesture_set.view.GesturePasswordSetView;
import com.xinyilian.text.login_password.view.LoginPasswordActivity;
import com.xinyilian.text.pay_password_change.view.PayPasswordChangeActivity;
import com.xinyilian.text.sms.persenter.SMSPersenter;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：短信验证
 */
public class SMSActivity extends BaseActivity implements SMSView,GesturePasswordSetView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtSmsTel;
    private TextView mTvSmsGetVerficcode;
    private EditText mEtSmsVerficcode;
    private Button mBtnSmsNext;

    private GesturePasswordSetPersenter mGesturePasswordSetPersenter;
    private SMSPersenter mSMSPersenter;

    private String mSMSVerfic;
    private String mSMSUUID;

    private String action;
    private String tel;

    @Override
    public int bindLayout() {
        return R.layout.activity_sms_verfic;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mEtSmsTel = findViewById(R.id.et_sms_tel);
        mTvSmsGetVerficcode = findViewById(R.id.tv_sms_get_verficcode);
        mEtSmsVerficcode = findViewById(R.id.et_sms_verficcode);
        mBtnSmsNext = findViewById(R.id.btn_sms_next);
    }

    @Override
    public void initData() {

        String userTel = (String) SPUtil.get(this, "usertel", "");
        if (!StringUtils.isEmpty(userTel)) {
            mEtSmsTel.setText(userTel);
            mEtSmsTel.setEnabled(false);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            action = bundle.getString("action");
            tel = bundle.getString("tel");
        }
        if (StringUtils.isEmpty(action)) {
            ToastUtils.showToast("数据加载失败");
            return;
        }
        switch (action) {
            case "clearGesturePassword":
                mTvActionbarTitle.setText("关闭手势密码");
                break;
            case "changeGesturePassword":
                mTvActionbarTitle.setText("修改手势密码");
                break;
            case "setGesturePassword":
                mTvActionbarTitle.setText("设置手势密码");
                break;
            case "forgetGesturePassword":
                mTvActionbarTitle.setText("忘记手势密码");
                break;
            case "changePayPassword":
                mTvActionbarTitle.setText("修改支付密码");
                break;
            case "forgetPayPassword":
                mTvActionbarTitle.setText("忘记支付密码");
                break;
            case "forgetLoginPassword":
                if (!StringUtils.isEmpty(tel)) {
                    mEtSmsTel.setText(tel);
                } else {
                    tel = (String) SPUtil.get(SMSActivity.this, "usertel", "");
                    if (!StringUtils.isEmpty(tel)) {
                        mEtSmsTel.setText(tel);
                        mEtSmsTel.setEnabled(false);
                    }
                }
                mTvActionbarTitle.setText("忘记登录密码");
                break;
            case "changeLoginPassword":
                mTvActionbarTitle.setText("修改登录密码");
                break;
        }
        mSMSPersenter = new SMSPersenter(this, this);
        mGesturePasswordSetPersenter = new GesturePasswordSetPersenter(this,this);

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvSmsGetVerficcode.setOnClickListener(this);
        mBtnSmsNext.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_sms_get_verficcode:
                String tel = mEtSmsTel.getText().toString().trim();
                mSMSPersenter.getChangeGesturePsswordSMS(tel);
                break;
            case R.id.btn_sms_next:
                if (StringUtils.isEmpty(mSMSUUID) || StringUtils.isEmpty(mSMSVerfic)) {
                    ToastUtils.showToast("请获取验证码");
                    return;
                }
                String inputVerfic = mEtSmsVerficcode.getText().toString().trim();
                if (StringUtils.isEmpty(inputVerfic)) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                if (!mSMSVerfic.equals(inputVerfic)) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("checkCode", mSMSVerfic);
                bundle.putString("uuid", mSMSUUID);
                bundle.putString("cellPhone", mEtSmsTel.getText().toString().trim());
                switch (action) {
                    case "clearGesturePassword":
                        mGesturePasswordSetPersenter.gesturePasswordSet(mEtSmsTel.getText().toString().trim(), mSMSVerfic, mSMSUUID, "");
                        break;
                    case "changeGesturePassword":
                        startActivity(GesturePasswordChangeActivity.class, bundle);
                        finish();
                        break;
                    case "setGesturePassword":
                        bundle.putString("type", "set");
                        startActivity(GesturePasswordSetActivity.class, bundle);
                        finish();
                        break;
                    case "forgetGesturePassword":
                        bundle.putString("type", "forget");
                        startActivity(GesturePasswordSetActivity.class, bundle);
                        finish();
                        break;
                    case "changePayPassword":
                        bundle.putString("type", "change");
                        startActivity(PayPasswordChangeActivity.class, bundle);
                        finish();
                        break;
                    case "forgetPayPassword":
                        bundle.putString("type", "forget");
                        startActivity(PayPasswordChangeActivity.class, bundle);
                        finish();
                        break;
                    case "forgetLoginPassword":
                        bundle.putString("type", "forget");
                        startActivity(LoginPasswordActivity.class, bundle);
                        finish();
                        break;
                    case "changeLoginPassword":
                        bundle.putString("type", "change");
                        startActivity(LoginPasswordActivity.class, bundle);
                        finish();
                        break;
                }


                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void getSMS(CommonSMSBean.DataBean data) {
        mSMSUUID = data.getUuid();
        mSMSVerfic = data.getCode();
        new TimeCount(60000, 1000).start();
    }

    @Override
    public void gesturePasswordSetSuccess() {
        finish();
    }

    @Override
    public void gesturePasswordSetFail() {
        finish();
    }

    /**
     * 倒计时
     */
    private class TimeCount extends CountDownTimer {

        private TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            mTvSmsGetVerficcode.setText(millisUntilFinished / 1000 + getString(R.string.count_down));
            mTvSmsGetVerficcode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mTvSmsGetVerficcode.setText(R.string.reset_get_verifi_code);
            mTvSmsGetVerficcode.setEnabled(true);
        }
    }
}
