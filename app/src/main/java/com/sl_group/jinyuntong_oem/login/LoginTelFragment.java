package com.sl_group.jinyuntong_oem.login;

import android.annotation.SuppressLint;
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
import com.sl_group.jinyuntong_oem.bean.LoginSMSBean;
import com.sl_group.jinyuntong_oem.login.persenter.LoginPersenter;
import com.sl_group.jinyuntong_oem.login.view.LoginView;
import com.sl_group.jinyuntong_oem.register.view.RegisterActivity;
import com.sl_group.jinyuntong_oem.utils.CodeUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/11.
 * description：手机号登录
 */
public class LoginTelFragment extends BaseFragment implements LoginView {
    private EditText mEtTelLoginTel;
    private TextView mTvTelLoginVerficCode;
    private EditText mEtTelLoginVerficCode;
    private EditText mEtTelLoginPicVerficCode;
    private ImageView mImgPicVerfic;
    private Button mBtnTelLogin;
    private TextView mTvTelLoginRegister;
    private TextView mTvTelLoginServiceTel;
    //登录persenter
    private LoginPersenter mLoginPersenter;
    //图片验证码生成工具
    private CodeUtils mCodeUtils;
    //图片验证码位图
    private Bitmap mBitmap;
    //验证码UUID
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
        mTimeCount = new TimeCount(120000,1000);
        //初始化登录persenter
        mLoginPersenter = new LoginPersenter(getActivity(), this);
        //图片验证码工具
        mCodeUtils = CodeUtils.getInstance();
        //生成图片位图
        mBitmap = mCodeUtils.createBitmap();
        //显示图片验证码
        mImgPicVerfic.setImageBitmap(mBitmap);
        //密码登录带过来的输入的手机号
        String inputTel = (String) SPUtil.get(getActivity(), "inputTel", "");
        if (!StringUtils.isEmpty(inputTel)) {
            mEtTelLoginTel.setText(inputTel);
            mEtTelLoginTel.setSelection(inputTel.length());
        }
        //之前登录成功保存的手机号
        String userTel = (String) SPUtil.get(getActivity(), "usertel", "");
        //是否是强制退出登录
        boolean isCompelLogin = (boolean) SPUtil.get(getActivity(), "isCompelLogin", false);
        if (!StringUtils.isEmpty(userTel) && isCompelLogin) {
            mEtTelLoginTel.setText(userTel);
            mEtTelLoginTel.setSelection(userTel.length());
        }
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
                String smsTel = mEtTelLoginTel.getText().toString().trim();
                mLoginPersenter.getSMSCode(smsTel);
                break;
            case R.id.btn_tel_login:
                if (StringUtils.isEmpty(uuid)) {
                    ToastUtils.showToast("请先获取验证码");
                    return;
                }
                //图片验证码
                String genPicVerficCode = mCodeUtils.getCode();
                //输入的图片验证码
                String picVerficCode = mEtTelLoginPicVerficCode.getText().toString().trim();
                if (StringUtils.isEmpty(genPicVerficCode)) {
                    ToastUtils.showToast("图片验证码生成失败，请点击图片刷新");
                    return;
                }
                if (!picVerficCode.toLowerCase().equals(genPicVerficCode.toLowerCase())) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }

                String loginTel = mEtTelLoginTel.getText().toString().trim();
                String smsCode = mEtTelLoginVerficCode.getText().toString().trim();
                mLoginPersenter.login("", loginTel, "", smsCode, uuid);
                break;
            case R.id.img_pic_verfic:
                mBitmap= mCodeUtils.createBitmap();
                mImgPicVerfic.setImageBitmap(mBitmap);
                break;
            case R.id.tv_tel_login_register:
                //注册
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_tel_login_service_tel:
                //拨打电话
                showCallPhonePop();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void loginSuccess() {
        startActivity(MainActivity.class);
        getActivity().finish();
    }

    @Override
    public void compelLogin() {
    }

    @Override
    public void loginSMSSuccess(LoginSMSBean.DataBean dataBean) {
        this.uuid = dataBean.getUuid();
        mTimeCount.start();
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

        TextView tvPopupCancel = view.findViewById(R.id.btn_pop_call_phone_cancel);
        TextView tvPopupCall = view.findViewById(R.id.btn_pop_call_phone_call);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTimeCount.cancel();
        mTimeCount.onFinish();
    }
}
