package com.sl_group.jinyuntong_oem.realname.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.ScanCameraActivity;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.RealnameSMSBean;
import com.sl_group.jinyuntong_oem.realname.persenter.RealnamePersenter;
import com.sl_group.jinyuntong_oem.realname_sms.persenter.RealnameSMSPersenter;
import com.sl_group.jinyuntong_oem.realname_sms.view.RealnameSMSView;
import com.sl_group.jinyuntong_oem.upload_img.persenter.UploadImgPersenter;
import com.sl_group.jinyuntong_oem.upload_img.view.UpLoadImgView;
import com.sl_group.jinyuntong_oem.utils.CameraUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.PhotoTakeUtils;
import com.sl_group.jinyuntong_oem.utils.RandomUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 马天 on 2018/11/16.
 * description：实名认证
 */
public class RealnameActivity extends BaseActivity implements RealnameView, UpLoadImgView, RealnameSMSView {
    //动态授权请求码  读写内存，相机权限
    private static final int PERMISSIONS_READ_WRITE_CAMERA = 0;
    //扫描银行卡获取卡号请求码
    private final static int SCAN_BANKCARD_REQUEST_CODE = 1;
    //拍摄身份证照片请求码
    private final static int UPLOAD_IDCARD_REQUEST_CODE = 2;
    //拍摄银行卡正面照
    private final static int UPLOAD_BANKCARD_REQUEST_CODE = 3;
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvRealnameName;
    private TextView mTvRealnameIdcard;
    private TextView mTvRealnameIdcardLife;
    private TextView mTvRealnameIdcardAddress;
    private EditText mEtRealnameCardnumber;
    private ImageView mImgRealnameScan;
    private EditText mEtRealnameTel;
    private TextView mTvRealnameGetVerficcode;
    private EditText mEtRealnameVerficcode;
    private ImageView mImgRealnameIdcard;
    private ImageView mImgRealnameBankcard;
    private Button mBtnRealnameCommit;

    //拍照的图片保存sd地址1   手持身份证照片
    private String mIdcardPath;
    //拍照的图片保存sd地址
    private String mBankcardPath;
    //实名认证，上传照片，短信 persenter
    private RealnamePersenter mRealnamePersenter;
    private UploadImgPersenter mUploadImgPersenter;
    private RealnameSMSPersenter mRealnameSMSPersenter;
    //拍照照片位图
    private Bitmap mBitmap;
    private String bizPlaceSnapshot1ImageId;
    private String bizPlaceSnapshot2ImageId;
    //短信UUID
    private String mSMSUUID;

    @Override
    public int bindLayout() {
        return R.layout.activity_realname;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvRealnameName = findViewById(R.id.tv_realname_name);
        mTvRealnameIdcard = findViewById(R.id.tv_realname_idcard);
        mTvRealnameIdcardLife = findViewById(R.id.tv_realname_idcard_life);
        mTvRealnameIdcardAddress = findViewById(R.id.tv_realname_idcard_address);
        mEtRealnameCardnumber = findViewById(R.id.et_realname_cardnumber);
        mImgRealnameScan = findViewById(R.id.img_realname_scan);
        mEtRealnameTel = findViewById(R.id.et_realname_tel);
        mTvRealnameGetVerficcode = findViewById(R.id.tv_realname_get_verficcode);
        mEtRealnameVerficcode = findViewById(R.id.et_realname_verficcode);
        mImgRealnameIdcard = findViewById(R.id.img_realname_idcard);
        mImgRealnameBankcard = findViewById(R.id.img_realname_bankcard);
        mBtnRealnameCommit = findViewById(R.id.btn_realname_commit);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("实名认证");
        //初始化persenter
        mRealnamePersenter = new RealnamePersenter(this, this);
        mUploadImgPersenter = new UploadImgPersenter(this, this);
        mRealnameSMSPersenter = new RealnameSMSPersenter(this, this);
        //初始化文件路径
        mIdcardPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";
        mBankcardPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id_name = bundle.getString("id_name");
            String id_no = bundle.getString("id_no");
            String start_card = bundle.getString("start_card");
            String addr_card = bundle.getString("addr_card");
            mTvRealnameName.setText(id_name);
            mTvRealnameIdcard.setText(id_no);
            mTvRealnameIdcardLife.setText(start_card);
            mTvRealnameIdcardAddress.setText(addr_card);
        }
        //获取控件的宽度
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mImgRealnameIdcard.measure(w, h);
        int width = mImgRealnameIdcard.getMeasuredWidth();

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImgRealnameIdcard.getLayoutParams();
        //设置高度和宽度一样
        layoutParams.height = width;
        //ImageView 设置参数
        mImgRealnameIdcard.setLayoutParams(layoutParams);
        mImgRealnameBankcard.setLayoutParams(layoutParams);
        //创建图片文件夹
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

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mImgRealnameScan.setOnClickListener(this);
        mTvRealnameGetVerficcode.setOnClickListener(this);
        mImgRealnameIdcard.setOnClickListener(this);
        mImgRealnameBankcard.setOnClickListener(this);
        mBtnRealnameCommit.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.img_realname_scan:
                //扫描银行卡卡号
                Intent scanIntent = new Intent(RealnameActivity.this, ScanCameraActivity.class);
                startActivityForResult(scanIntent, SCAN_BANKCARD_REQUEST_CODE);
                break;
            case R.id.tv_realname_get_verficcode:
                String tel = mEtRealnameTel.getText().toString().trim();
                mRealnameSMSPersenter.realnameSMS(tel);
                break;
            case R.id.img_realname_idcard:
                //身份证照片
                Intent idcardIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                idcardIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mIdcardPath)));
                startActivityForResult(idcardIntent, UPLOAD_IDCARD_REQUEST_CODE);
                break;
            case R.id.img_realname_bankcard:
                //银行卡照片
                Intent bankcardIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                bankcardIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mBankcardPath)));
                startActivityForResult(bankcardIntent, UPLOAD_BANKCARD_REQUEST_CODE);
                break;
            case R.id.btn_realname_commit:
                //提交资料
                String idcard = mTvRealnameIdcard.getText().toString().trim();
                String holdname = mTvRealnameName.getText().toString().trim();
                String accountNumber = mEtRealnameCardnumber.getText().toString().trim();
                String bankcardtel = mEtRealnameTel.getText().toString().trim();
                String verficCode = mEtRealnameVerficcode.getText().toString().trim();
                mRealnamePersenter.realname(idcard, holdname, accountNumber, bankcardtel, verficCode, mSMSUUID, bizPlaceSnapshot1ImageId, bizPlaceSnapshot2ImageId);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        checkPermission();
    }


    @Override
    public void uploadImgSuccess(String data, int index) {
        if (index == UPLOAD_IDCARD_REQUEST_CODE) {
            bizPlaceSnapshot1ImageId = data;
            mImgRealnameIdcard.setImageBitmap(mBitmap);
        } else if (index == SCAN_BANKCARD_REQUEST_CODE || index == UPLOAD_BANKCARD_REQUEST_CODE) {
            bizPlaceSnapshot2ImageId = data;
            mImgRealnameBankcard.setImageBitmap(mBitmap);
        }
    }

    @Override
    public void realnameSMSSuccess(RealnameSMSBean.DataBean data) {
        mSMSUUID = data.getUuid();
        new TimeCount(60000, 1000).start();
    }

    @Override
    public void realnameSuccess() {
        SPUtil.put(this, "isReflash", true);
        finish();
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
                    mEtRealnameCardnumber.setText(cardNumber.replace(" ", ""));
                } else {
                    mEtRealnameCardnumber.setText(cardNumber);
                    mEtRealnameCardnumber.setSelection(cardNumber.length());
                }


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(mDatas, 0, mDatas.length, options);

                Bitmap bitmap1 = zoomBitmap(bitmap, 1200, 720);
                int w = bitmap1.getWidth(); // 得到图片的宽，高
                int h = bitmap1.getHeight();
                mBitmap = Bitmap.createBitmap(bitmap1, 75, 0, w - 180, h, null, false);
                saveBitmap(mBitmap, mBankcardPath);
                mUploadImgPersenter.uploadImg(mBankcardPath, SCAN_BANKCARD_REQUEST_CODE);
                break;
            case UPLOAD_IDCARD_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mIdcardPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                saveBitmap(mBitmap, mIdcardPath);
                mUploadImgPersenter.uploadImg(mIdcardPath, UPLOAD_IDCARD_REQUEST_CODE);
                break;
            case UPLOAD_BANKCARD_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mBankcardPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                saveBitmap(mBitmap, mBankcardPath);
                mUploadImgPersenter.uploadImg(mBankcardPath, UPLOAD_BANKCARD_REQUEST_CODE);
                break;


        }
    }

    /**
     * 压缩位图
     *
     * @param bitmap 需要压缩的位图
     * @param w      图片的宽度
     * @param h      图片的高度
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 保存图片
     *
     * @param destBitmap 需要保存的图片
     * @param fileName   文件名
     */
    public void saveBitmap(Bitmap destBitmap, String fileName) {
        File f = new File(fileName);
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            destBitmap.compress(Bitmap.CompressFormat.PNG, 60, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                ToastUtils.showToast("权限已获取，请继续操作");
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }


    /**
     * 倒计时
     * param
     */
    private class TimeCount extends CountDownTimer {

        private TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            mTvRealnameGetVerficcode.setText(millisUntilFinished / 1000 + getString(R.string.count_down));
            mTvRealnameGetVerficcode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mTvRealnameGetVerficcode.setText(R.string.reset_get_verifi_code);
            mTvRealnameGetVerficcode.setEnabled(true);
        }
    }
}
