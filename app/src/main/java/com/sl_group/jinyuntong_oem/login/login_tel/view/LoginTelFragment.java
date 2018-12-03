package com.sl_group.jinyuntong_oem.login.login_tel.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.MainActivity;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseFragment;
import com.sl_group.jinyuntong_oem.login.LoginActivity;
import com.sl_group.jinyuntong_oem.login.login_tel.persenter.LoginTelPersenter;
import com.sl_group.jinyuntong_oem.register.view.RegisterActivity;
import com.sl_group.jinyuntong_oem.utils.CodeUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/11.
 * description：
 */
public class LoginTelFragment extends BaseFragment implements LoginTelView {
    private EditText mEtTelLoginTel;
    private TextView mTvTelLoginVerficCode;
    private EditText mEtTelLoginVerficCode;
    private EditText mEtTelLoginPicVerficCode;
    private ImageView mImgPicVerfic;
    private Button mBtnTelLogin;
    private TextView mTvTelLoginRegister;
    private TextView mTvTelLoginServiceTel;


    private LoginTelPersenter mLoginTelPersenter;
    private CodeUtils mCodeUtils;

    private String tel;
    private String uuid;
    private TimeCount mTimeCount;

    @Override
    public int bindLayout() {
        return R.layout.fragment_tel_login;
    }

    @Override
    public void initView(View view) {
        mEtTelLoginTel = view.findViewById(R.id.et_tel_login_tel);
        mTvTelLoginVerficCode = view.findViewById(R.id.tv_tel_login_get_verfic_code);
        mEtTelLoginVerficCode = view.findViewById(R.id.et_tel_login_verfic_code);
        mEtTelLoginPicVerficCode = view.findViewById(R.id.et_tel_login_pic_verfic_code);
        mImgPicVerfic = view.findViewById(R.id.img_pic_verfic);
        mBtnTelLogin = view.findViewById(R.id.btn_tel_login);
        mTvTelLoginRegister = view.findViewById(R.id.tv_tel_login_register);
        mTvTelLoginServiceTel = view.findViewById(R.id.tv_tel_login_service_tel);
    }

    @Override
    public void initData() {
        mLoginTelPersenter = new LoginTelPersenter(getActivity(), this);
        mCodeUtils = CodeUtils.getInstance();
        Bitmap bitmap = mCodeUtils.createBitmap();
        mImgPicVerfic.setImageBitmap(bitmap);

        mTimeCount = new TimeCount(120000, 1000);
    }

    @Override
    public void setListener() {
        mBtnTelLogin.setOnClickListener(this);
        mTvTelLoginVerficCode.setOnClickListener(this);
        mImgPicVerfic.setOnClickListener(this);
        mTvTelLoginRegister.setOnClickListener(this);
        mTvTelLoginServiceTel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tel_login_get_verfic_code:
                tel = mEtTelLoginTel.getText().toString().trim();
                mLoginTelPersenter.getSMSCode(tel);
                break;
            case R.id.btn_tel_login:

                String genPicVerficCode = mCodeUtils.getCode();
                String picVerficCode = mEtTelLoginPicVerficCode.getText().toString().trim();

                if (StringUtils.isEmpty(genPicVerficCode)) {
                    ToastUtils.showToast("图片验证码生成失败，请点击图片刷新");
                    return;
                }
                if (StringUtils.isEmpty(uuid)) {
                    ToastUtils.showToast("请先获取验证码");
                    return;
                }

                if (!picVerficCode.toLowerCase().equals(genPicVerficCode.toLowerCase())) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }

                LogUtils.i("UUID:" + uuid);
                tel = mEtTelLoginTel.getText().toString().trim();
                String smsCode = mEtTelLoginVerficCode.getText().toString().trim();
                mLoginTelPersenter.login(tel, smsCode, uuid);
                break;
            case R.id.img_pic_verfic:
                Bitmap bitmap = mCodeUtils.createBitmap();
                mImgPicVerfic.setImageBitmap(bitmap);
                mEtTelLoginPicVerficCode.setText(mCodeUtils.getCode());
                break;
            case R.id.tv_tel_login_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_tel_login_service_tel:
                showCallPhonePop();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void skipMainActivity() {
        mTimeCount.onFinish();
        mTimeCount.cancel();
        startActivity(MainActivity.class);
        getActivity().finish();
    }

    @Override
    public void skipLoginActivity() {
        mTimeCount.onFinish();
        mTimeCount.cancel();
        startActivity(LoginActivity.class);
        getActivity().finish();
    }

    @Override
    public void getSMSUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void startCount() {
        mTimeCount.start();
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
            mTvTelLoginVerficCode.setText(millisUntilFinished / 1000 + getString(R.string.count_down));
            mTvTelLoginVerficCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mTvTelLoginVerficCode.setText(R.string.reset_get_verifi_code);
            mTvTelLoginVerficCode.setEnabled(true);
        }
    }
    /**
     * 确认拨打电话弹窗
     */
    private void showCallPhonePop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_call_phone, null);

        TextView tvPopupCancel =  view.findViewById(R.id.btn_pop_call_phone_cancel);
        TextView tvPopupCall =  view.findViewById(R.id.btn_pop_call_phone_call);

        final PopupWindow popupWindow = PopupWindowUtils.getPop(getActivity(), view, DisplayUtils.getScreenWidth(getActivity()) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        tvPopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        tvPopupCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + getString(R.string.service_call_phone));
                intent.setData(data);
                startActivity(intent);
            }
        });

    }
}
