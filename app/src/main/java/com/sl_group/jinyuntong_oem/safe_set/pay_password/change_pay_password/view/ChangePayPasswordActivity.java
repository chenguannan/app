package com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cfca.mobile.log.CodeException;
import com.cfca.mobile.sipedit.SipEditText;
import com.cfca.mobile.sipedit.grid.GridSipEditStateType;
import com.cfca.mobile.sipedit.grid.GridSipEditText;
import com.cfca.mobile.sipedit.grid.GridSipEditTextDelegator;
import com.cfca.mobile.sipkeyboard.SIPKeyboardType;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.safe_set.pay_password.change_pay_password.persenter.ChangePayPasswordPersenter;
import com.sl_group.jinyuntong_oem.set_pay_password.persenter.SetPayPasswordPersenter;
import com.sl_group.jinyuntong_oem.set_pay_password.view.SetPayPasswordView;
import com.sl_group.jinyuntong_oem.utils.StringUtils;

import static com.sl_group.jinyuntong_oem.CommonSet.SERVICE_RANDOM;

/**
 * Created by 马天 on 2018/11/22.
 * description：支付密码
 */
public class ChangePayPasswordActivity extends BaseActivity implements ChangePayPasswordView, SetPayPasswordView, GridSipEditTextDelegator {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvChangePayPasswordTitle;
    private GridSipEditText gridSip_fisrt;
    private GridSipEditText gridSip_second;

    private ChangePayPasswordPersenter mChangePayPasswordPersenter;
    private SetPayPasswordPersenter mSetPayPasswordPersenter;
    private String type;
    private String checkCode;
    private String uuid;
    private String cellPhone;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_pay_password;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvChangePayPasswordTitle = findViewById(R.id.tv_change_pay_password_title);
        gridSip_fisrt = view.findViewById(R.id.gridSip_first);
        gridSip_second = view.findViewById(R.id.gridSip_second);
    }

    @Override
    public void initData() {
        mTvChangePayPasswordTitle.setText("请输入新支付密码");
        gridSip_fisrt.setGridColor(ContextCompat.getColor(this, R.color.blackColor));
        gridSip_fisrt.setSipKeyBoardType(SIPKeyboardType.NUMBER_KEYBOARD);
        gridSip_fisrt.setStorkeWidth(1);
        gridSip_fisrt.setCirdNumber(6);
        gridSip_fisrt.setNodeColor(Color.BLACK);
        gridSip_fisrt.setEncryptState(true);
        gridSip_fisrt.setServerRandom(SERVICE_RANDOM);
        gridSip_fisrt.setCipherType(SipEditText.Algorithm_RSA1024);
        gridSip_fisrt.setOutputValueType(2);
        gridSip_fisrt.showSecurityKeyBoard();

        gridSip_second.setGridColor(ContextCompat.getColor(this, R.color.blackColor));
        gridSip_second.setSipKeyBoardType(SIPKeyboardType.NUMBER_KEYBOARD);
        gridSip_second.setStorkeWidth(1);
        gridSip_second.setCirdNumber(6);
        gridSip_second.setNodeColor(Color.BLACK);
        gridSip_second.setEncryptState(true);
        gridSip_second.setServerRandom(SERVICE_RANDOM);
        gridSip_second.setCipherType(SipEditText.Algorithm_RSA1024);
        gridSip_second.setOutputValueType(2);

        gridSip_fisrt.setGridSipEditTextDelegator(this);
        gridSip_second.setGridSipEditTextDelegator(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            checkCode = bundle.getString("checkCode","");
            uuid = bundle.getString("uuid","");
            cellPhone = bundle.getString("cellPhone","");
        }
        if (StringUtils.isEmpty(type)) {
            finish();
        }
        if (type.equals("change")) {
            mTvActionbarTitle.setText("修改支付密码");
        } else if (type.equals("forget")) {
            mTvActionbarTitle.setText("忘记支付密码");
        }

        mChangePayPasswordPersenter = new ChangePayPasswordPersenter(this, this);
        mSetPayPasswordPersenter = new SetPayPasswordPersenter(this, this);


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

    }

    @Override
    public void changePayPasswordSuccess() {
        finish();
    }

    @Override
    public void setPasswordSuccess(String payPassword) {
        finish();
    }

    @Override
    public void beforeKeyboardShow(GridSipEditText gridSipEditText, int i) {

    }

    @Override
    public void afterKeyboardHidden(GridSipEditText gridSipEditText, int i) {

    }

    @Override
    public void afterClickDown(GridSipEditText gridSipEditText) {

    }

    @Override
    public void onInputComplete(GridSipEditText gridSipEditText) {
        if (gridSipEditText == gridSip_fisrt) {
            gridSip_fisrt.setVisibility(View.GONE);
            gridSip_second.setVisibility(View.VISIBLE);
            mTvChangePayPasswordTitle.setText("请确认新支付密码");
            gridSip_second.showSecurityKeyBoard();
        } else {
            try {
                if (gridSip_second.inputEqualsWith(gridSip_fisrt)) {
                    if (type.equals("change")) {
                        mChangePayPasswordPersenter.changePayPassword(cellPhone,checkCode,uuid,gridSipEditText.getEncryptData().getEncryptRandomNum() + "#" + gridSipEditText.getEncryptData().getEncryptInput());
                    } else if (type.equals("forget")) {
                        mSetPayPasswordPersenter.setPayPassword(cellPhone,checkCode,uuid,gridSipEditText.getEncryptData().getEncryptRandomNum() + "#" + gridSipEditText.getEncryptData().getEncryptInput());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "输入不同请重新输入", Toast.LENGTH_SHORT).show();
                    mTvChangePayPasswordTitle.setText("请输入新支付密码");
                    gridSip_fisrt.clear();
                    gridSip_second.clear();
                    gridSip_fisrt.setVisibility(View.VISIBLE);
                    gridSip_second.setVisibility(View.GONE);

                }
            } catch (CodeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTextSizeChange(GridSipEditStateType gridSipEditStateType) {

    }
}
