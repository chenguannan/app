package com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.safe_set.gesture_password.change_gesture.persenter.ChangeGesturePasswordPersenter;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.wangnan.library.GestureLockView;
import com.wangnan.library.listener.OnGestureLockListener;

/**
 * Created by 马天 on 2018/11/18.
 * description：修改手势密码
 */
public class ChangeGesturePasswordActivity extends BaseActivity implements ChangeGesturePasswordView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvChangeGesturePasswordTitle;
    private GestureLockView mGlvChangeGesturePassword;


    private String setGesturePasswordOne ;

    private ChangeGesturePasswordPersenter mChangeGesturePasswordPersenter;
    private String checkCode;
    private String uuid;
    private String cellPhone;
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
        mTvActionbarTitle.setText("修改手势密码");
        mChangeGesturePasswordPersenter = new ChangeGesturePasswordPersenter(this,this);
        Bundle bundle =getIntent().getExtras();
        if (bundle!=null){
            checkCode = bundle.getString("checkCode","");
            uuid = bundle.getString("uuid","");
            cellPhone = bundle.getString("cellPhone","");
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
        mGlvChangeGesturePassword.setGestureLockListener(new OnGestureLockListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(String progress) {

            }

            @Override
            public void onComplete(String result) {
                if (StringUtils.isEmpty(setGesturePasswordOne) &&result.length() <4){
                    mTvChangeGesturePasswordTitle.setText("新手势密码");
                    Toast.makeText(ChangeGesturePasswordActivity.this, "请设置最少4个连接点", Toast.LENGTH_SHORT).show();
                    mGlvChangeGesturePassword.showErrorStatus(500);
                }else if (StringUtils.isEmpty(setGesturePasswordOne)){
                    mTvChangeGesturePasswordTitle.setText("确认手势密码");
                    setGesturePasswordOne = result;
                    mGlvChangeGesturePassword.clearView();
                }else if (setGesturePasswordOne.equals(result)) {
                    SPUtil.put(ChangeGesturePasswordActivity.this, "gesturePwd", result);
                    mGlvChangeGesturePassword.clearView();
                    mChangeGesturePasswordPersenter.setGesturePassword(cellPhone,checkCode,uuid,result);
                } else if (!setGesturePasswordOne.equals(result)){
                    mTvChangeGesturePasswordTitle.setText("新手势密码");
                    SPUtil.remove(ChangeGesturePasswordActivity.this, "gesturePwd");
                    ToastUtils.showToast("两次设置不一致");
                    mGlvChangeGesturePassword.showErrorStatus(500);
                }
            }
        });
    }

    @Override
    public void finshActivity() {
        finish();
    }

    @Override
    public void resetGesturePassword() {
        mTvChangeGesturePasswordTitle.setText("新手势密码");
        setGesturePasswordOne = null;
    }
}
