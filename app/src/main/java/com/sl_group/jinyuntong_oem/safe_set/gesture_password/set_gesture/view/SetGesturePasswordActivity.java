package com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sl_group.jinyuntong_oem.MainActivity;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.set_gesture.persenter.SetGesturePasswordPersenter;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/18.
 * description：设置手势密码
 */
public class SetGesturePasswordActivity extends BaseActivity implements SetGesturePasswordView{
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvSetGesturePasswordTitle;
    private GestureLockView mGlvSetGesturePassword;

    private String setGesturePasswordOne ;

    private SetGesturePasswordPersenter mSetGesturePasswordPersenter;

    private String type;
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
        mTvActionbarTitle.setText("设置手势密码");
        mSetGesturePasswordPersenter = new SetGesturePasswordPersenter(this,this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            type= bundle.getString("type","");
        }

        if (!StringUtils.isEmpty(type)&&type.equals("forget")){
            mTvActionbarTitle.setText("忘记手势密码");
        }
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mGlvSetGesturePassword.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(String progress) {

            }

            @Override
            public void onComplete(String result) {
                if (StringUtils.isEmpty(setGesturePasswordOne) &&result.length() <4){
                    mTvSetGesturePasswordTitle.setText("设置手势密码");
                    Toast.makeText(SetGesturePasswordActivity.this, "请设置最少4个连接点", Toast.LENGTH_SHORT).show();
                    mGlvSetGesturePassword.showErrorStatus(500);
                }else if (StringUtils.isEmpty(setGesturePasswordOne)){
                    mTvSetGesturePasswordTitle.setText("确定手势密码");
                    setGesturePasswordOne = result;
                    mGlvSetGesturePassword.clearView();
                }else if (setGesturePasswordOne.equals(result)) {
                    SPUtil.put(SetGesturePasswordActivity.this, "gesturePwd", result);
                    mGlvSetGesturePassword.clearView();
                    mSetGesturePasswordPersenter.setGesturePassword(result);
                } else if (!setGesturePasswordOne.equals(result)){
                    mTvSetGesturePasswordTitle.setText("设置手势密码");
                    SPUtil.remove(SetGesturePasswordActivity.this, "gesturePwd");
                    ToastUtils.showToast("两次设置不一致");
                    mGlvSetGesturePassword.showErrorStatus(500);
                }
            }
        });
    }

    @Override
    public void finshActivity() {
        if (!StringUtils.isEmpty(type)&&type.equals("forget")){
            startActivity(MainActivity.class);
        }
        finish();
    }

    @Override
    public void resetGesturePassword() {
        mTvSetGesturePasswordTitle.setText("设置手势密码");
        setGesturePasswordOne = null;
    }
}
