package com.sl_group.jinyuntong_oem.gather.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.firstpage.persenter.FirstpagePersenter;
import com.sl_group.jinyuntong_oem.firstpage.view.FirstpageView;
import com.sl_group.jinyuntong_oem.gather.persenter.GatherPersenter;
import com.sl_group.jinyuntong_oem.gather.set_money.SetMoneyActivity;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.myshop.gather_bill.view.GatherBillActivity;
import com.sl_group.jinyuntong_oem.scan_input_money.view.ScanQrcodeInputMoneyActivity;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.QRCodeUtil;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

import java.io.File;

/**
 * Created by 马天 on 2018/11/14.
 * description：二维码收款
 */
public class GatherActivity extends BaseActivity implements GatherView, MerchantinfoView,FirstpageView {
    private static final int PERMISSIONS_READ_WRITE = 1;
    private ImageView mImgActionbarBack;
    private TextView mTvGatherRecord;
    private ImageView mImgGatherQrcode;
    private TextView mTvGatherMoney;
    private TextView mTvGatherSetMoney;
    private TextView mTvGatherSaveQrcode;

    private GatherPersenter mGatherPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private FirstpagePersenter mFirstpagePersenter;
    private Bitmap mBitmap;
    private int screenWidth;
    private String payCode;

    @Override
    public int bindLayout() {
        return R.layout.activity_gather;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvGatherRecord = findViewById(R.id.tv_gather_record);
        mImgGatherQrcode = findViewById(R.id.img_gather_qrcode);
        mTvGatherMoney = findViewById(R.id.tv_gather_money);
        mTvGatherSetMoney = findViewById(R.id.tv_gather_set_money);
        mTvGatherSaveQrcode = findViewById(R.id.tv_gather_save_qrcode);
    }

    @Override
    public void initData() {
        mGatherPersenter = new GatherPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        mFirstpagePersenter = new FirstpagePersenter(this,this);
        screenWidth = DisplayUtils.getScreenWidth(this);



        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + CommonSet.IMG_CACHE;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.showToast("检测sd是否可用");
        }



        Bundle bundle = getIntent().getExtras();
        if (bundle == null || StringUtils.isEmpty(bundle.getString("gatherSetMoney"))) {
            mTvGatherMoney.setVisibility(View.GONE);
            mTvGatherSetMoney.setText("设置金额");
            payCode = (String) SPUtil.get(this, "payCode", "");
            if (!StringUtils.isEmpty(payCode)) {
                mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
                mImgGatherQrcode.setImageBitmap(mBitmap);
            }else {
                mMerchantinfoPersenter.merchantInfo();
            }

        } else {
            mTvGatherMoney.setText("¥" + bundle.getString("gatherSetMoney"));
            mTvGatherMoney.setVisibility(View.VISIBLE);
            mTvGatherSetMoney.setText("清除金额");
            mGatherPersenter.getPayCodeMoney(bundle.getString("gatherSetMoney"));
        }

        mImgGatherQrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mFirstpagePersenter.analyzeQrcode(payCode);
                ToastUtils.showToast("正在识别二维码");
                return true;
            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvGatherRecord.setOnClickListener(this);
        mTvGatherSetMoney.setOnClickListener(this);
        mTvGatherSaveQrcode.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_gather_record:
                //收款记录
                startActivity(GatherBillActivity.class);
                break;
            case R.id.tv_gather_set_money:
                String tvDisPlay = mTvGatherSetMoney.getText().toString();
                if (tvDisPlay.contains("设置")) {
                    startActivity(SetMoneyActivity.class);
                    finish();
                } else {
                    mTvGatherSetMoney.setText("设置金额");
                    mTvGatherMoney.setVisibility(View.GONE);
                    payCode = (String) SPUtil.get(this, "payCode", "");
                    if (!StringUtils.isEmpty(payCode)) {
                        mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
                        mImgGatherQrcode.setImageBitmap(mBitmap);
                    } else {
                        mMerchantinfoPersenter.merchantInfo();
                    }

                }

                break;
            case R.id.tv_gather_save_qrcode:
                if (mBitmap == null) {
                    ToastUtils.showToast("二维码生成失败");
                    return;
                }
                saveQrcodeMethod();
                //保存二维码
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    //保存图片
    private void saveQrcodeMethod() {

        // 检查是否有相应的权限
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这权限全都拥有, 则直接执行更新
        if (isAllGranted) {
            QRCodeUtil.saveQrCodePicture(GatherActivity.this, mBitmap);
            //            saveBitmap(mBitmap, fileName);
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSIONS_READ_WRITE
        );

    }

    /**
     * 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_READ_WRITE) {

            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                QRCodeUtil.saveQrCodePicture(GatherActivity.this, mBitmap);
                //                saveBitmap(mBitmap, fileName);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }


    @Override
    public void getPayCodeMoney(String payCodeMoney) {
        payCode = payCodeMoney;
        mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
        mImgGatherQrcode.setImageBitmap(mBitmap);
    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        payCode = dataBean.getPayCode();
        SPUtil.put(this, "payCode", dataBean.getPayCode());
        mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
        mImgGatherQrcode.setImageBitmap(mBitmap);
    }

    @Override
    public void getQrcodeContent(double srcAmt, String shortName, String receivedMid) {
        Bundle bundle = new Bundle();
        bundle.putDouble("money", srcAmt);
        bundle.putString("merchant", shortName);
        bundle.putString("receivedMid", receivedMid);
        bundle.putString("qrCodeContent", payCode);
        startActivity(ScanQrcodeInputMoneyActivity.class, bundle);
    }
}
