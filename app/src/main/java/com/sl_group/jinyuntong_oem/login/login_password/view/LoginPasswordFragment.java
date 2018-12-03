package com.sl_group.jinyuntong_oem.login.login_password.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import com.sl_group.jinyuntong_oem.login.login_password.persenter.LoginPasswordPersenter;
import com.sl_group.jinyuntong_oem.register.view.RegisterActivity;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;
import com.sl_group.jinyuntong_oem.utils.CodeUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/11.
 * description：
 */
public class LoginPasswordFragment extends BaseFragment implements LoginPasswordView {
    private EditText mEtPasswordLoginUsername;
    private EditText mEtPasswordLoginPassword;
    private Button mBtnPasswordLoginForgetPwd;
    private EditText mEtPasswordLoginPicVerficCode;
    private ImageView mImgPicVerfic;
    private Button mBtnPasswordLogin;
    private TextView mTvPasswordLoginRegister;
    private TextView mTvPasswordLoginServiceTel;


    private LoginPasswordPersenter mLoginPasswordPersenter;
    private CodeUtils mCodeUtils;

    @Override
    public int bindLayout() {
        return R.layout.fragment_password_login;
    }

    @Override
    public void initView(View view) {
        mEtPasswordLoginUsername = view.findViewById(R.id.et_password_login_username);
        mEtPasswordLoginPassword = view.findViewById(R.id.et_password_login_password);
        mBtnPasswordLoginForgetPwd = view.findViewById(R.id.btn_password_login_forget_pwd);
        mEtPasswordLoginPicVerficCode = view.findViewById(R.id.et_password_login_pic_verfic_code);
        mImgPicVerfic = view.findViewById(R.id.img_pic_verfic);
        mBtnPasswordLogin = view.findViewById(R.id.btn_password_login);
        mTvPasswordLoginRegister = view.findViewById(R.id.tv_password_login_register);
        mTvPasswordLoginServiceTel = view.findViewById(R.id.tv_password_login_service_tel);
    }

    @Override
    public void initData() {
        mLoginPasswordPersenter = new LoginPasswordPersenter(getActivity(), this);
        mCodeUtils = CodeUtils.getInstance();
        Bitmap bitmap = mCodeUtils.createBitmap();
        mImgPicVerfic.setImageBitmap(bitmap);
    }

    @Override
    public void setListener() {
        mBtnPasswordLogin.setOnClickListener(this);
        mImgPicVerfic.setOnClickListener(this);
        mTvPasswordLoginRegister.setOnClickListener(this);
        mTvPasswordLoginServiceTel.setOnClickListener(this);
        mBtnPasswordLoginForgetPwd.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_password_login_forget_pwd:
                Bundle bundle = new Bundle();
                bundle.putString("action","forgetLoginPassword");
                bundle.putString("tel",mEtPasswordLoginUsername.getText().toString().trim());
                startActivity(SMSActivity.class,bundle);
                break;
            case R.id.btn_password_login:
                String genPicVerficCode = mCodeUtils.getCode();
                String picVerficCode = mEtPasswordLoginPicVerficCode.getText().toString().trim();
                if (StringUtils.isEmpty(genPicVerficCode)) {
                    ToastUtils.showToast("图片验证码生成失败，请点击图片刷新");
                    return;
                }
                if (!picVerficCode.toLowerCase().equals(genPicVerficCode.toLowerCase())) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }
                String username = mEtPasswordLoginUsername.getText().toString().trim();
                String password = mEtPasswordLoginPassword.getText().toString().trim();
                mLoginPasswordPersenter.login(username, password);
                break;
            case R.id.img_pic_verfic:
                Bitmap bitmap = mCodeUtils.createBitmap();
                mImgPicVerfic.setImageBitmap(bitmap);
                mEtPasswordLoginPicVerficCode.setText(mCodeUtils.getCode());
                break;
            case R.id.tv_password_login_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_password_login_service_tel:
                showCallPhonePop();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void skipMainActivity() {
        startActivity(MainActivity.class);
        getActivity().finish();
    }

    @Override
    public void skipLoginActivity() {

        popChangeLogin();

    }



    private void popChangeLogin() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_change_phone, null);
        Button cancel = view.findViewById(R.id.btn_pop_change_phone_cancel);
        Button sure = view.findViewById(R.id.btn_pop_change_phone_sure);
        final PopupWindow popupWindow = PopupWindowUtils.getPop(getActivity(), view, DisplayUtils.getScreenWidth(getActivity()) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
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
