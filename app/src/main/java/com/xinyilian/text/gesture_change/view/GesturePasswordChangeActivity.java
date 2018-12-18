package com.xinyilian.text.gesture_change.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.gesture_change.persenter.GesturePasswordChangePersenter;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/18.
 * description：修改手势密码
 */
public class GesturePasswordChangeActivity extends BaseActivity implements GesturePasswordChangeView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvChangeGesturePasswordTitle;
    private GestureLockView mGlvChangeGesturePassword;
    //第一次设置手势密码
    private String setGesturePasswordOne;
    //初始化persenter
    private GesturePasswordChangePersenter mGesturePasswordChangePersenter;
    //手机号
    private String cellPhone;
    //验证码
    private String checkCode;
    //短信uuid
    private String uuid;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_gesture_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvChangeGesturePasswordTitle = findViewById(R.id.tv_change_gesture_password_title);
        mGlvChangeGesturePassword = findViewById(R.id.glv_change_gesture_password);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("修改手势密码");
        //初始化persenter
        mGesturePasswordChangePersenter = new GesturePasswordChangePersenter(this, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            checkCode = bundle.getString("checkCode", "");
            uuid = bundle.getString("uuid", "");
            cellPhone = bundle.getString("cellPhone", "");
        }
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //手势监听
        mGlvChangeGesturePassword.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(String progress) {

            }

            @Override
            public void onComplete(String result) {
                //第一次设置密码并且长度小于4
                if (StringUtils.isEmpty(setGesturePasswordOne) && result.length() < 4) {
                    mTvChangeGesturePasswordTitle.setText("新手势密码");
                    Toast.makeText(GesturePasswordChangeActivity.this, "请设置最少4个连接点", Toast.LENGTH_SHORT).show();
                    mGlvChangeGesturePassword.showErrorStatus(500);
                }
                //第一次设置密码，长度大于等于4
                else if (StringUtils.isEmpty(setGesturePasswordOne)) {
                    mTvChangeGesturePasswordTitle.setText("确认手势密码");
                    setGesturePasswordOne = result;
                    mGlvChangeGesturePassword.clearView();
                }
                //两次密码一致
                else if (setGesturePasswordOne.equals(result)) {
                    SPUtil.put(GesturePasswordChangeActivity.this, "gesturePwd", result);
                    mGlvChangeGesturePassword.clearView();
                    mGesturePasswordChangePersenter.setGesturePassword(cellPhone, checkCode, uuid, result);
                }
                //两次密码不一致
                else if (!setGesturePasswordOne.equals(result)) {
                    mTvChangeGesturePasswordTitle.setText("新手势密码");
                    setGesturePasswordOne = null;
                    ToastUtils.showToast("两次设置不一致");
                    mGlvChangeGesturePassword.showErrorStatus(500);
                }
            }
        });
    }

    /**
     * 修改手势密码成功
     */
    @Override
    public void gesturePasswordChangeSuccess() {
        finish();
    }

    /**
     * 修改手势密码失败
     */
    @Override
    public void gesturePasswordChangeFail() {
        mTvChangeGesturePasswordTitle.setText("新手势密码");
        setGesturePasswordOne = null;
    }
}
