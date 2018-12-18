package com.xinyilian.text.bindcard.view;

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
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.ScanCameraActivity;
import com.xinyilian.text.bindcard.persenter.BindCreditCardPersenter;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.merchant_info.persenter.MerchantinfoPersenter;
import com.xinyilian.text.merchant_info.view.MerchantinfoView;
import com.xinyilian.text.utils.BankCardTextWatcherUtils;
import com.xinyilian.text.utils.PermissionSetDialogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;
import com.xinyilian.text.utils.ToastUtils;
import com.xinyilian.text.web.LoadWebActivity;

/**
 * Created by 马天 on 2018/11/15.
 * description：绑定信用卡
 * 实现绑定信用卡和商户信息接口
 */
public class BindCreditCardActivity extends BaseActivity implements BindCreditCardView,MerchantinfoView {
    //动态授权请求码
    private static final int PERMISSIONS_READ_WRITE_CAMERA = 0;
    //扫描银行卡回调请求码
    private static final int SCAN_BANKCARD_REQUEST_CODE = 1;

    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvBindCredirCardUsername;
    private TextView mTvBindCredirCardIdcard;
    private EditText mEtBindCredirCardCardnumber;
    private ImageView mImgBindCredirCardScan;
    private EditText mEtBindCredirCardCardtel;
    private Button mBtnBindCredirCard;
    //绑定信用卡
    private BindCreditCardPersenter mBindCreditCardPersenter;
    //商户信息
    private MerchantinfoPersenter mMerchantinfoPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_bind_creditcard;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvBindCredirCardUsername = findViewById(R.id.tv_bind_creditcard_username);
        mTvBindCredirCardIdcard = findViewById(R.id.tv_bind_creditcard_idcard);
        mEtBindCredirCardCardnumber = findViewById(R.id.et_bind_creditcard_cardnumber);
        mImgBindCredirCardScan = findViewById(R.id.img_bind_creditcard_scan);
       // mEtBindCredirCardCardtel = findViewById(R.id.et_bind_creditcard_cardtel);
        mBtnBindCredirCard = findViewById(R.id.btn_bind_creditcard);
        BankCardTextWatcherUtils.bind(mEtBindCredirCardCardnumber);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("添加银行卡");
        //初始化
        mBindCreditCardPersenter = new BindCreditCardPersenter(this,this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this,this);

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mImgBindCredirCardScan.setOnClickListener(this);
        mBtnBindCredirCard.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.img_bind_creditcard_scan:
                //扫描银行卡，检查权限
                checkPermission();
                break;
            case R.id.btn_bind_creditcard:
                //卡号
                String accountNumber = mEtBindCredirCardCardnumber.getText().toString().replaceAll(" ","");
                //手机号
                String bankcardTel = (String) SPUtil.get(this,"cellPhone","");

                //绑卡
                mBindCreditCardPersenter.bindBankcard(accountNumber,bankcardTel);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mMerchantinfoPersenter.merchantInfo();
    }


    /**
      * 跳转到银联页面开通
      * @param data  银联URL
      */
    @Override
    public void openUnionpay(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("url",data);
        startActivity(LoadWebActivity.class,bundle);
        finish();
    }

    /**
      * 获取商户信息
      * @param dataBean 商户信息对象
      */
    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        mTvBindCredirCardUsername.setText(dataBean.getHolderName());
        mTvBindCredirCardIdcard.setText(StringUtils.getStarString(dataBean.getIdCard(),1,dataBean.getIdCard().length()-1));
    }

    /**
     * 检查拍照所需要的相应权限，相机，读写内存
     * 考虑写个通用方法
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
            //打开相机扫描识别银行卡
            Intent scanIntent = new Intent(BindCreditCardActivity.this, ScanCameraActivity.class);
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
                    PERMISSIONS_READ_WRITE_CAMERA
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
                    mEtBindCredirCardCardnumber.setText(cardNumber.replace(" ", ""));
                } else {
                    mEtBindCredirCardCardnumber.setText(cardNumber);
                    mEtBindCredirCardCardnumber.setSelection(cardNumber.length());
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

        if (requestCode == PERMISSIONS_READ_WRITE_CAMERA) {
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
                Intent scanIntent = new Intent(BindCreditCardActivity.this, ScanCameraActivity.class);
                startActivityForResult(scanIntent, SCAN_BANKCARD_REQUEST_CODE);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }
}
