package com.sl_group.jinyuntong_oem.gather.view;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.sl_group.jinyuntong_oem.analyze_qrcode.persenter.AnalyzeQrcodePersenter;
import com.sl_group.jinyuntong_oem.analyze_qrcode.view.AnalyzeQrcodeView;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.AnalyzeQrcodeBean;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.gather.persenter.GatherPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.gather_bill.view.GatherBillActivity;
import com.sl_group.jinyuntong_oem.gather_input_money.GatherInputMoneyActivity;
import com.sl_group.jinyuntong_oem.gather_set_money.GatherSetMoneyActivity;
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
public class GatherActivity extends BaseActivity implements GatherView, MerchantinfoView, AnalyzeQrcodeView {
    //读写内存的权限，动态授权
    private static final int PERMISSIONS_READ_WRITE = 1;
    private ImageView mImgActionbarBack;
    private TextView mTvGatherRecord;
    private ImageView mImgGatherQrcode;
    private TextView mTvGatherMoney;
    private TextView mTvGatherSetMoney;
    private TextView mTvGatherSaveQrcode;
    //收款，商户信息，解析二维码persneter
    private GatherPersenter mGatherPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private AnalyzeQrcodePersenter mAnalyzeQrcodePersenter;
    //二维码位图
    private Bitmap mBitmap;
    //屏幕宽度，用以显示二维码大小
    private int screenWidth;
    //带金额二维码链接
    private String payCode;

    @Override
    public int bindLayout() {
        return R.layout.activity_gather;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        //获取intent传递的参数，如果没有数据传递，为设置金额，否则是设置好的金额
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || StringUtils.isEmpty(bundle.getString("gatherSetMoney"))) {
            mTvGatherMoney.setVisibility(View.GONE);
            mTvGatherSetMoney.setText("设置金额");
            //获取保存的二维码内容payCode
            payCode = (String) SPUtil.get(this, "payCode", "");
            //如果获取的收款二维码是空的，去查询商户信息接口，不是空就直接生成二维码
            if (!StringUtils.isEmpty(payCode)) {
                //根据payCode生成二维码位图
                mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
                //展示位图
                mImgGatherQrcode.setImageBitmap(mBitmap);
            } else {
                //查询商户信息
                mMerchantinfoPersenter.merchantInfo();
            }

        } else {
            mTvGatherMoney.setText("¥" + bundle.getString("gatherSetMoney"));
            mTvGatherMoney.setVisibility(View.VISIBLE);
            mTvGatherSetMoney.setText("清除金额");
            //调带金额收款码接口
            mGatherPersenter.gatherWithMoney(bundle.getString("gatherSetMoney"));
        }

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
        //初始化persenter
        mGatherPersenter = new GatherPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        mAnalyzeQrcodePersenter = new AnalyzeQrcodePersenter(this, this);
        //获取屏幕密度
        screenWidth = DisplayUtils.getScreenWidth(this);

        //长按识别二维码
        mImgGatherQrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调解析二维码接口
                mAnalyzeQrcodePersenter.analyzeQrcode(payCode);
                ToastUtils.showToast("正在识别二维码");
                return true;
            }
        });
        //检测SD卡是否挂载
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.showToast("检测sd是否可用");
            return;
        }
        //创建文件夹
        String path = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

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
                //返回
                finish();
                break;
            case R.id.tv_gather_record:
                //收款账单
                startActivity(GatherBillActivity.class);
                break;
            case R.id.tv_gather_set_money:
                //设置金额或者清除金额，获取显示的内容
                String tvDisPlay = mTvGatherSetMoney.getText().toString();
                if (tvDisPlay.contains("设置")) {
                    startActivity(GatherSetMoneyActivity.class);
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
                //保存二维码
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

    /**
     * 带金额的收款码
     *
     * @param gatherWithMoneyQrcode 带金额的二维码链接
     */
    @Override
    public void gatherWithMoneySuccess(String gatherWithMoneyQrcode) {
        payCode = gatherWithMoneyQrcode;
        //生成带金额的二维码
        mBitmap = QRCodeUtil.createQRImage(this.payCode, screenWidth / 2, screenWidth / 2, null);
        //显示位图
        mImgGatherQrcode.setImageBitmap(mBitmap);
    }

    /**
     * 获取商户信息
     *
     * @param dataBean 商户信息对象
     */
    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        payCode = dataBean.getPayCode();
        //保存下不带金额的二维码链接
        SPUtil.put(this, "payCode", dataBean.getPayCode());
        //生成带金额的二维码
        mBitmap = QRCodeUtil.createQRImage(payCode, screenWidth / 2, screenWidth / 2, null);
        //显示位图
        mImgGatherQrcode.setImageBitmap(mBitmap);
    }

    /**
     * 解析二维码成功
     *
     * @param dataBean 解析二维码对象
     */
    @Override
    public void analyzeQrcodeSuccess(AnalyzeQrcodeBean.DataBean dataBean) {
        Bundle bundle = new Bundle();
        bundle.putDouble("money", dataBean.getSrcAmt());
        bundle.putString("merchant", dataBean.getShortName());
        bundle.putString("receivedMid", dataBean.getReceivedMid());
        bundle.putString("qrCodeContent", payCode);
        startActivity(GatherInputMoneyActivity.class, bundle);
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

}
