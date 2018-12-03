package com.sl_group.jinyuntong_oem.sms.view;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.view.ChangeGesturePasswordActivity;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.view.SetGesturePasswordActivity;
import com.sl_group.jinyuntong_oem.safe_set.login_password.view.ChangeLoginPasswordActivity;
import com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.view.ChangePayPasswordActivity;
import com.sl_group.jinyuntong_oem.sms.persenter.SMSPersenter;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/18.
 * description：短信验证
 */
public class SMSActivity extends BaseActivity implements SMSView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtSmsTel;
    private TextView mTvSmsGetVerficcode;
    private EditText mEtSmsVerficcode;
    private Button mBtnSmsNext;


    private SMSPersenter mSMSPersenter;

    private String mSMSVerfic;

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
       if (!StringUtils.isEmpty(userTel)){
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
            case "changeGesturePassword":
                mTvActionbarTitle.setText("修改手势密码");
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
                if (!StringUtils.isEmpty(tel)){
                    mEtSmsTel.setText(tel);
                }else {
                    tel = (String) SPUtil.get(SMSActivity.this, "usertel", "");
                    if (!StringUtils.isEmpty(tel)){
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
                if (StringUtils.isEmpty(mSMSVerfic)) {
                    ToastUtils.showToast("请获取验证码");
                    return;
                }
                String inputVerfic = mEtSmsVerficcode.getText().toString().trim();
                if (!mSMSVerfic.equals(inputVerfic)) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }
                Bundle bundle = new Bundle();
                switch (action) {
                    case "changeGesturePassword":
                        startActivity(ChangeGesturePasswordActivity.class);
                        finish();
                        break;
                    case "forgetGesturePassword":
                        bundle.putString("type", "forget");
                        startActivity(SetGesturePasswordActivity.class, bundle);
                        finish();
                        break;
                    case "changePayPassword":
                        bundle.putString("type", "change");
                        startActivity(ChangePayPasswordActivity.class, bundle);
                        finish();
                        break;
                    case "forgetPayPassword":
                        bundle.putString("type", "forget");
                        startActivity(ChangePayPasswordActivity.class, bundle);
                        finish();
                        break;
                    case "forgetLoginPassword":
                        bundle.putString("type", "forget");
                        bundle.putString("tel", mEtSmsTel.getText().toString().trim());
                        startActivity(ChangeLoginPasswordActivity.class, bundle);
                        finish();
                        break;
                    case "changeLoginPassword":
                        bundle.putString("type", "change");
                        bundle.putString("tel", mEtSmsTel.getText().toString().trim());
                        startActivity(ChangeLoginPasswordActivity.class, bundle);
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
    public void getSMS(String data) {
        mSMSVerfic = data;
        new TimeCount(120000, 1000).start();
    }

    /**
     * 倒计时
     */
    private class TimeCount extends CountDownTimer {

        private TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

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
