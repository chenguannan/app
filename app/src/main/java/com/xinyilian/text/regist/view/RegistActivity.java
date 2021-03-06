package com.xinyilian.text.regist.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.google.gson.Gson;
import com.xinyilian.text.CommonSet;
import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.AutoRealnameBean;
import com.xinyilian.text.bean.RegisterSMSBean;
import com.xinyilian.text.realname.view.RealnameActivity;
import com.xinyilian.text.regist.persenter.RegistPersenter;
import com.xinyilian.text.regist_sms.persenter.RegistSMSPersenter;
import com.xinyilian.text.regist_sms.view.RegistSMSView;
import com.xinyilian.text.system_prop.persenter.SystemPropPersenter;
import com.xinyilian.text.system_prop.view.SystemPropView;
import com.xinyilian.text.utils.DisplayUtils;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.PopupWindowUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.ToastUtils;
import com.xinyilian.text.web.LoadWebActivity;

import java.util.Date;

/**
 * Created by 马天 on 2018/11/10.
 * description：注册
 */
public class RegistActivity extends BaseActivity implements RegistSMSView, RegistView, SystemPropView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private EditText mEtRegisterInvitecode;
    private EditText mEtRegisterTel;
    private Button mBtnRegisterGetVerfictionCode;
    private EditText mEtRegisterSmsVerficCode;
    private EditText mEtRegisterPassword;
    private EditText mEtRegisterPasswordAgain;
    private CheckBox mCbRegisterAgreement;
    private TextView mTvRegisterAgreement;
    private Button mBtnRegister;
    private TextView mTvRegisterClickGetInvitecode;

    //注册短信，注册，各种连接persenter
    private RegistSMSPersenter mRegistSMSPersenter;
    private RegistPersenter mRegistPersenter;
    private SystemPropPersenter mSystemPropPersenter;
    //短信UUID
    private String mSMSUUID;

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mEtRegisterInvitecode = findViewById(R.id.et_register_invitecode);
        mEtRegisterTel = findViewById(R.id.et_register_tel);
        mBtnRegisterGetVerfictionCode = findViewById(R.id.btn_register_get_verfiction_code);
        mEtRegisterSmsVerficCode = findViewById(R.id.et_register_sms_verfic_code);
        mEtRegisterPassword = findViewById(R.id.et_register_password);
        mEtRegisterPasswordAgain = findViewById(R.id.et_register_password_again);
        mCbRegisterAgreement = findViewById(R.id.cb_register_agreement);
        mTvRegisterAgreement = findViewById(R.id.tv_register_agreement);
        mBtnRegister = findViewById(R.id.btn_register);
        mTvRegisterClickGetInvitecode = findViewById(R.id.tv_register_click_get_invitecode);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("注册");
        //初始化persenter
        mRegistSMSPersenter = new RegistSMSPersenter(this, this);
        mRegistPersenter = new RegistPersenter(this, this);
        mSystemPropPersenter = new SystemPropPersenter(this, this);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnRegisterGetVerfictionCode.setOnClickListener(this);
        mCbRegisterAgreement.setOnClickListener(this);
        mTvRegisterAgreement.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvRegisterClickGetInvitecode.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_register_get_verfiction_code:
                //获取验证码
                String smsInvitecode = mEtRegisterInvitecode.getText().toString().trim();
                String smsTel = mEtRegisterTel.getText().toString().trim();
                mRegistSMSPersenter.registSMS(smsInvitecode, smsTel);
                break;
            case R.id.btn_register:
                String invitecode = mEtRegisterInvitecode.getText().toString().trim();
                String tel = mEtRegisterTel.getText().toString().trim();
                String smsVerficcode = mEtRegisterSmsVerficCode.getText().toString().trim();
                String password = mEtRegisterPassword.getText().toString().trim();
                String passwordAgain = mEtRegisterPasswordAgain.getText().toString().trim();
                boolean isSelected = mCbRegisterAgreement.isChecked();
                mRegistPersenter.register(invitecode, tel, smsVerficcode, mSMSUUID, password, passwordAgain, isSelected);
                break;
            case R.id.tv_register_agreement:
                mSystemPropPersenter.systemProp("xieyi");
                break;
            case R.id.tv_register_click_get_invitecode:
                mSystemPropPersenter.systemProp("yaoqingma");
                break;
        }
    }


    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void registSMSSuccess(RegisterSMSBean.DataBean data) {
        mSMSUUID = data.getUuid();
        new TimeCount(60000, 1000).start();
    }

    @Override
    public void registSuccess() {
        popRegistSuccess();
    }


    @Override
    public void getKeFuURL(String kefu) {

    }

    @Override
    public void getXieYiURL(String xieyi) {
        Bundle bundle = new Bundle();
        bundle.putString("url", xieyi);
        startActivity(LoadWebActivity.class, bundle);
    }

    @Override
    public void getXinShouURL(String xinshou) {

    }

    @Override
    public void getYaoQingMaURL(String yaoqing) {
        Bundle bundle = new Bundle();
        bundle.putString("url", yaoqing);
        startActivity(LoadWebActivity.class, bundle);
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
            mBtnRegisterGetVerfictionCode.setText(millisUntilFinished / 1000 + getString(R.string.count_down));
            mBtnRegisterGetVerfictionCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mBtnRegisterGetVerfictionCode.setText(R.string.reset_get_verifi_code);
            mBtnRegisterGetVerfictionCode.setEnabled(true);
        }
    }

    /**
      * 注册成功弹窗
      */
    private void popRegistSuccess() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_regist_success, null);

        Button btnPopRegistSuccessRealname = view.findViewById(R.id.btn_pop_regist_success_realname);
        ImageView imgPopRegistSuccessClose = view.findViewById(R.id.img_pop_regist_success_close);

        final PopupWindow popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this) * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        btnPopRegistSuccessRealname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                autoRealname();
            }
        });
        imgPopRegistSuccessClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
    }

    /**
      * 活体实名，自动审核
      */
    private void autoRealname() {

        String id = "jyt_" + new Date().getTime();
        AuthBuilder mAuthBuilder = new AuthBuilder(id, CommonSet.authKey, CommonSet.urlNotify + SPUtil.get(this, "mid", ""), new OnResultListener() {
            @Override
            public void onResult(String s) {
                LogUtils.i("活体认证结果：" + s);
                AutoRealnameBean autoRealnameBean = new Gson().fromJson(s, AutoRealnameBean.class);
                if (!"000000".equals(autoRealnameBean.getRet_code())) {
                    ToastUtils.showToast(autoRealnameBean.getRet_msg());
                    return;
                }
                Intent intent = new Intent(RegistActivity.this, RealnameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_name", autoRealnameBean.getId_name());
                bundle.putString("id_no", autoRealnameBean.getId_no());
                bundle.putString("start_card", autoRealnameBean.getStart_card());
                bundle.putString("addr_card", autoRealnameBean.getAddr_card());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        //下文调用方法做为范例，请以对接文档中的调用方法为准
        mAuthBuilder.faceAuth(this);

    }
}
