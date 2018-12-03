package com.sl_group.jinyuntong_oem.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.login.login_password.view.LoginPasswordFragment;
import com.sl_group.jinyuntong_oem.login.login_tel.view.LoginTelFragment;


/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public class LoginActivity extends BaseActivity {
    private TextView mTvLoginPassword;
    private TextView mTvLoginTel;


    private Fragment mLoginPasswordFragment, mLoginTelFragment;
    private FragmentManager mFragmentManager;


    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        mTvLoginPassword = findViewById(R.id.tv_login_password);
        mTvLoginTel = findViewById(R.id.tv_login_tel);
    }

    @Override
    public void initData() {
        //创建事务
        mFragmentManager = this.getFragmentManager();


    }

    @Override
    public void setListener() {
        mTvLoginPassword.setOnClickListener(this);
        mTvLoginTel.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_password:
                mTvLoginPassword.setTextSize(20);
                mTvLoginPassword.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.whiteColor));
                mTvLoginPassword.setCompoundDrawablesWithIntrinsicBounds(null,null,null,getResources().getDrawable(R.drawable.v_underline));
                mTvLoginTel.setTextSize(16);
                mTvLoginTel.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.whiteColor_alpha_7));
                mTvLoginTel.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                selectFragment(1);
                break;
            case R.id.tv_login_tel:
                mTvLoginTel.setTextSize(20);
                mTvLoginTel.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.whiteColor));
                mTvLoginTel.setCompoundDrawablesWithIntrinsicBounds(null,null,null,getResources().getDrawable(R.drawable.v_underline));
                mTvLoginPassword.setTextSize(16);
                mTvLoginPassword.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.whiteColor_alpha_7));
                mTvLoginPassword.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                selectFragment(2);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        selectFragment(1);
    }

    /**
     * 选择Fragment
     * param
     */
    private void selectFragment(int position) {
        if (mFragmentManager==null){
            //创建事务
            mFragmentManager = this.getFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //隐藏
        hideFragments(fragmentTransaction);
        switch (position) {
            case 1:
                if (mLoginPasswordFragment == null) {
                    mLoginPasswordFragment = new LoginPasswordFragment();
                    fragmentTransaction.add(R.id.fl, mLoginPasswordFragment);
                } else {
                    fragmentTransaction.show(mLoginPasswordFragment);
                }
                break;
            case 2:
                if (mLoginTelFragment == null) {
                    mLoginTelFragment = new LoginTelFragment();
                    fragmentTransaction.add(R.id.fl, mLoginTelFragment);
                } else {
                    fragmentTransaction.show(mLoginTelFragment);
                }
                break;
        }
        //事务提交
        fragmentTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideFragments(FragmentTransaction ft) {
        if (mLoginPasswordFragment != null) {
            ft.hide(mLoginPasswordFragment);
        }
        if (mLoginTelFragment != null) {
            ft.hide(mLoginTelFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (mLoginPasswordFragment == null && fragment instanceof LoginPasswordFragment) {
            mLoginPasswordFragment = fragment;
        }
        if (mLoginTelFragment == null && fragment instanceof LoginTelFragment) {
            mLoginTelFragment = fragment;
        }

    }
}
