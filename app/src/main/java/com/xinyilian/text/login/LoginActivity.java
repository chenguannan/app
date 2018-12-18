package com.xinyilian.text.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.utils.SPUtil;


/**
 * Created by 马天 on 2018/10/16.
 * description：登录
 */
public class LoginActivity extends BaseActivity {
    private TextView mTvLoginPassword;
    private TextView mTvLoginTel;

    private Fragment mLoginPasswordFragment, mLoginTelFragment;
    private FragmentManager mFragmentManager;
    //是否是强制退出登录
    private boolean isCompelLogin = false;
    private long exitTime = 0;

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
                selectFragment(1);
                break;
            case R.id.tv_login_tel:
                selectFragment(2);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //如果是强制退出登录的话，传递该参数，选择手机号登录页面
            isCompelLogin = bundle.getBoolean("isCompelLogin", false);
        }
        if (isCompelLogin) {
            selectFragment(2);
        } else {
            selectFragment(1);
        }

    }

    /**
     * 选择Fragment
     * param
     */
    private void selectFragment(int position) {
        if (mFragmentManager == null) {
            //创建事务
            mFragmentManager = this.getFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //隐藏
        hideFragments(fragmentTransaction);
        switch (position) {
            case 1:
                switchStyle(mTvLoginPassword, mTvLoginTel);
                if (mLoginPasswordFragment == null) {
                    mLoginPasswordFragment = new LoginPasswordFragment();
                    fragmentTransaction.add(R.id.fl, mLoginPasswordFragment);
                } else {
                    fragmentTransaction.show(mLoginPasswordFragment);
                }
                break;
            case 2:
                switchStyle(mTvLoginTel, mTvLoginPassword);
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

    /**
     * 切换选择风格
     *
     * @param tvSelected 选中的控件
     * @param tvNormal   正常的控件
     */
    private void switchStyle(TextView tvSelected, TextView tvNormal) {
        tvSelected.setTextSize(20);
        tvSelected.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.whiteColor));
        tvSelected.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.v_underline));
        tvNormal.setTextSize(16);
        tvNormal.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.whiteColor_alpha_7));
        tvNormal.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁的时候清除输入的手机号
        SPUtil.remove(this, "inputTel");
    }
    /**
     * 再按一次退出程序
     * param 时间类型，事件
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
