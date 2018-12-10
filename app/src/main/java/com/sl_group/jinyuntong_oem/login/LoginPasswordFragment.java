package com.sl_group.jinyuntong_oem.login;

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
import com.sl_group.jinyuntong_oem.bean.LoginSMSBean;
import com.sl_group.jinyuntong_oem.login.persenter.LoginPersenter;
import com.sl_group.jinyuntong_oem.login.view.LoginView;
import com.sl_group.jinyuntong_oem.regist.view.RegistActivity;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;
import com.sl_group.jinyuntong_oem.utils.CodeUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/11.
 * description：密码登录
 */
public class LoginPasswordFragment extends BaseFragment implements LoginView {
    private EditText mEtPasswordLoginTel;
    private EditText mEtPasswordLoginPassword;
    private Button mBtnPasswordLoginForgetPwd;
    private EditText mEtPasswordLoginPicVerficCode;
    private ImageView mImgPicVerfic;
    private Button mBtnPasswordLogin;
    private TextView mTvPasswordLoginRegister;
    private TextView mTvPasswordLoginServiceTel;
    //登录persenter
    private LoginPersenter mLoginPersenter;
    //图片验证码
    private CodeUtils mCodeUtils;
    private Bitmap mBitmap;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //切换fragment时把输入的手机号传过去
            SPUtil.put(getActivity(),"inputTel", mEtPasswordLoginTel.getText().toString().trim());
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_password_login;
    }

    @Override
    public void initView(View view) {
        mEtPasswordLoginTel = view.findViewById(R.id.et_password_login_tel);
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
        //初始化登录persenter
        mLoginPersenter = new LoginPersenter(getActivity(), this);
        //初始化图片验证码
        mCodeUtils = CodeUtils.getInstance();
        //生成图片验证码
         mBitmap = mCodeUtils.createBitmap();
        //展示图片验证码
        mImgPicVerfic.setImageBitmap(mBitmap);
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
                //忘记登录密码
                Bundle bundle = new Bundle();
                bundle.putString("action","forgetLoginPassword");
                bundle.putString("tel", mEtPasswordLoginTel.getText().toString().trim());
                startActivity(SMSActivity.class,bundle);
                break;
            case R.id.btn_password_login:
                //登录
                //生成的图片验证码
                String genPicVerficCode = mCodeUtils.getCode();
                //输入的图片验证码
                String picVerficCode = mEtPasswordLoginPicVerficCode.getText().toString().trim();
                if (StringUtils.isEmpty(genPicVerficCode)) {
                    ToastUtils.showToast("图片验证码生成失败，请点击图片刷新");
                    return;
                }
                if (!picVerficCode.toLowerCase().equals(genPicVerficCode.toLowerCase())) {
                    ToastUtils.showToast("验证码错误");
                    return;
                }
                String tel = mEtPasswordLoginTel.getText().toString().trim();
                String password = mEtPasswordLoginPassword.getText().toString().trim();
                mLoginPersenter.login("UsePwd",tel, password,"","");
                break;
            case R.id.img_pic_verfic:
                //点击图片验证码刷新验证码
                mBitmap= mCodeUtils.createBitmap();
                mImgPicVerfic.setImageBitmap(mBitmap);
                break;
            case R.id.tv_password_login_register:
                //注册
                startActivity(RegistActivity.class);
                break;
            case R.id.tv_password_login_service_tel:
                //拨打电话
                showCallPhonePop();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
    /**
      * 登录成功
      */
    @Override
    public void loginSuccess() {
        startActivity(MainActivity.class);
        getActivity().finish();
    }

    /**
      * 强制退出登录
      */
    @Override
    public void compelLogin() {
        popChangeLogin();
    }

    @Override
    public void loginSMSSuccess(LoginSMSBean.DataBean uuid) {

    }

    /**
      *切换手机号登录模式
      */
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
                Bundle bundle = new Bundle();
                bundle.putBoolean("isCompelLogin", true);
                startActivity(LoginActivity.class,bundle);
                getActivity().finish();
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
