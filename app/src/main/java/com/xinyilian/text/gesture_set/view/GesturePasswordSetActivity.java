package com.xinyilian.text.gesture_set.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyilian.text.MainActivity;
import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.gesture_set.persenter.GesturePasswordSetPersenter;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/18.
 * description：设置手势密码
 */
public class GesturePasswordSetActivity extends BaseActivity implements GesturePasswordSetView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvSetGesturePasswordTitle;
    private GestureLockView mGlvSetGesturePassword;
    //第一次设置的手势密码
    private String setGesturePasswordOne;
    //设置手势密码persenter
    private GesturePasswordSetPersenter mGesturePasswordSetPersenter;
    //设置和忘记  类型
    private String type;
    //手机号码
    private String cellPhone;
    //验证码
    private String checkCode;
    //UUID
    private String uuid;

    @Override
    public int bindLayout() {
        return R.layout.activity_set_gesture_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvSetGesturePasswordTitle = findViewById(R.id.tv_set_gesture_password_title);
        mGlvSetGesturePassword = findViewById(R.id.glv_set_gesture_password);
    }

    @Override
    public void initData() {
        //设置手势密码persenter
        mGesturePasswordSetPersenter = new GesturePasswordSetPersenter(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type", "");
            checkCode = bundle.getString("checkCode", "");
            uuid = bundle.getString("uuid", "");
            cellPhone = bundle.getString("cellPhone", "");
        }

        //设置标题
        if (!StringUtils.isEmpty(type) && type.equals("forget")) {
            mTvActionbarTitle.setText("忘记手势密码");
        } else {
            mTvActionbarTitle.setText("设置手势密码");
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
        //手势密码监听
        mGlvSetGesturePassword.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(String progress) {

            }

            @Override
            public void onComplete(String result) {
                //第一次设置密码是空或者长度小于4
                if (StringUtils.isEmpty(setGesturePasswordOne) && result.length() < 4) {
                    mTvSetGesturePasswordTitle.setText("设置手势密码");
                    Toast.makeText(GesturePasswordSetActivity.this, "请设置最少4个连接点", Toast.LENGTH_SHORT).show();
                    mGlvSetGesturePassword.showErrorStatus(500);
                }
                //第一次设置密码是kong，长度大于等于4
                else if (StringUtils.isEmpty(setGesturePasswordOne)) {
                    mTvSetGesturePasswordTitle.setText("确定手势密码");
                    setGesturePasswordOne = result;
                    mGlvSetGesturePassword.clearView();
                }
                //两次手势密码一致
                else if (setGesturePasswordOne.equals(result)) {
                    SPUtil.put(GesturePasswordSetActivity.this, "gesturePwd", result);
                    mGlvSetGesturePassword.clearView();
                    mGesturePasswordSetPersenter.gesturePasswordSet(cellPhone, checkCode, uuid, result);
                }
                //两次手势密码不一致
                else if (!setGesturePasswordOne.equals(result)) {
                    mTvSetGesturePasswordTitle.setText("设置手势密码");
                    setGesturePasswordOne = null;
                    ToastUtils.showToast("两次设置不一致");
                    mGlvSetGesturePassword.showErrorStatus(500);
                }
            }
        });
    }

    /**
     * 设置手势密码成功
     */
    @Override
    public void gesturePasswordSetSuccess() {
        //如果是忘记手势密码，跳至首页
        if (!StringUtils.isEmpty(type) && type.equals("forget")) {
            startActivity(MainActivity.class);
        }
        finish();
    }

    /**
     * 设置手势密码失败
     */
    @Override
    public void gesturePasswordSetFail() {
        mTvSetGesturePasswordTitle.setText("设置手势密码");
        setGesturePasswordOne = null;
    }
}
