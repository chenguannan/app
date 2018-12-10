package com.sl_group.jinyuntong_oem.settle_info.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.ScanCameraActivity;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.settle_info.persenter.SettleInfoPersenter;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.web.LoadWebActivity;

/**
 * Created by 马天 on 2018/11/20.
 * description：结算卡信息
 */
public class SettleInfoActivity extends BaseActivity implements SettleInfoView, MerchantinfoView {
    private static final int PERMISSIONS_WRITE_CAMERA = 2;
    private static final int SCAN_BANKCARD_REQUEST_CODE = 1;
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvSettleInfoName;
    private TextView mTvSettleInfoIdcard;
    private RelativeLayout mRlSettleInfoDisplay;
    private TextView mTvSettleInfoCardnumber;
    private TextView mTvSettleInfoTel;
    private TextView mTvSettleInfoBankname;
    private RelativeLayout mRlSettleInfoChange;
    private EditText mEtSettleInfoCardnumber;
    private ImageView mImgSettleInfoScan;
    private EditText mEtSettleInfoTel;
    private Button mBtnSettleInfoChangeFinsh;


    private SettleInfoPersenter mSettleInfoPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_settle_info;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvSettleInfoName = findViewById(R.id.tv_settle_info_name);
        mTvSettleInfoIdcard = findViewById(R.id.tv_settle_info_idcard);
        mRlSettleInfoDisplay = findViewById(R.id.rl_settle_info_display);
        mTvSettleInfoCardnumber = findViewById(R.id.tv_settle_info_cardnumber);
        mTvSettleInfoTel = findViewById(R.id.tv_settle_info_tel);
        mTvSettleInfoBankname = findViewById(R.id.tv_settle_info_bankname);
        mRlSettleInfoChange = findViewById(R.id.rl_settle_info_change);
        mEtSettleInfoCardnumber = findViewById(R.id.et_settle_info_cardnumber);
        mImgSettleInfoScan = findViewById(R.id.img_settle_info_scan);
        mEtSettleInfoTel = findViewById(R.id.et_settle_info_tel);
        mBtnSettleInfoChangeFinsh = findViewById(R.id.btn_settle_info_change_finsh);


    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("结算卡信息");

        mSettleInfoPersenter = new SettleInfoPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);

        String holderName = (String) SPUtil.get(this, "holderName", "");
        String idCard = (String) SPUtil.get(this, "idCard", "");
        String accountNumber = (String) SPUtil.get(this, "accountNumber", "");
        String cellPhone = (String) SPUtil.get(this, "cellPhone", "");
        String depositBank = (String) SPUtil.get(this, "depositBank", "");
        if (StringUtils.isEmpty(holderName)  || StringUtils.isEmpty(idCard) || StringUtils.isEmpty(accountNumber) ||
                StringUtils.isEmpty(cellPhone) || StringUtils.isEmpty(depositBank)) {
            mMerchantinfoPersenter.merchantInfo();
        } else {
            displayInfo(holderName, idCard, accountNumber, cellPhone, depositBank);
        }

    }


    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnSettleInfoChangeFinsh.setOnClickListener(this);
        mImgSettleInfoScan.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_settle_info_change_finsh:
                String btnDisplay = mBtnSettleInfoChangeFinsh.getText().toString().trim();
                if (btnDisplay.contains("结算卡")) {
                    mRlSettleInfoDisplay.setVisibility(View.GONE);
                    mRlSettleInfoChange.setVisibility(View.VISIBLE);
                    mBtnSettleInfoChangeFinsh.setText("完成修改");
                } else {
                    //上传数据
                    String accountNumber = mEtSettleInfoCardnumber.getText().toString().trim();
                    String tel = mEtSettleInfoTel.getText().toString().trim();
                    mSettleInfoPersenter.setSettleCard(accountNumber, tel);
                }
                break;
            case R.id.img_settle_info_scan:
                checkPermission();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
    }


    /**
     * 检查拍照所需要的相应权限
     */
    private void checkPermission() {
        // 检查是否有相应的权限
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }
        );
        // 如果这权限全都拥有, 则直接执行更新
        if (isAllGranted) {
            Intent scanIntent = new Intent(SettleInfoActivity.this, ScanCameraActivity.class);
            startActivityForResult(scanIntent, SCAN_BANKCARD_REQUEST_CODE);
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    },
                    PERMISSIONS_WRITE_CAMERA
            );
        }

    }

    /**
     * 拍照完成后，回调的方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        switch (requestCode) {
            //扫描回调
            case SCAN_BANKCARD_REQUEST_CODE:
                char[] StringR = data.getCharArrayExtra("StringR");
                byte[] mDatas = data.getByteArrayExtra("mData");
                if (null == StringR || null == mDatas) {
                    ToastUtils.showToast("扫描失败");
                    return;
                }
                if (StringR.length == 0 || mDatas.length == 0) {
                    ToastUtils.showToast("扫描失败");
                    return;
                }
                String cardNumber = String.valueOf(StringR).trim();
                if (cardNumber.contains(" ")) {
                    mEtSettleInfoCardnumber.setText(cardNumber.replace(" ", ""));
                } else {
                    mEtSettleInfoCardnumber.setText(cardNumber);
                    mEtSettleInfoCardnumber.setSelection(cardNumber.length());
                }
                break;

        }
    }


    /**
     * 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_WRITE_CAMERA) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                Intent scanIntent = new Intent(SettleInfoActivity.this, ScanCameraActivity.class);
                startActivityForResult(scanIntent, SCAN_BANKCARD_REQUEST_CODE);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }

    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        displayInfo(dataBean.getHolderName(), dataBean.getIdCard(), dataBean.getAccountNumber(), dataBean.getCellPhone(), dataBean.getDepositBank());
    }


    private void displayInfo(String holderName, String idCard, String accountNumber, String cellPhone, String depositBank) {
        mTvSettleInfoName.setText(holderName);
        mTvSettleInfoIdcard.setText(idCard);
        mTvSettleInfoCardnumber.setText(accountNumber);
        mTvSettleInfoTel.setText(cellPhone);
        mTvSettleInfoBankname.setText(depositBank);
    }

    @Override
    public void openURL(String data) {
        Bundle bundle = new Bundle();
        bundle.getString("url",data);
        startActivity(LoadWebActivity.class,bundle);
        finish();
    }
}
