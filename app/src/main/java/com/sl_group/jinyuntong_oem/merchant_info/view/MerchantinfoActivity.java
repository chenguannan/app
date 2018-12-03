package com.sl_group.jinyuntong_oem.merchant_info.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.bean.SetHeadImgBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.set_head.persenter.SetHeadPersenter;
import com.sl_group.jinyuntong_oem.set_head.view.SetHeadView;
import com.sl_group.jinyuntong_oem.upload_img.persenter.UploadImgPersenter;
import com.sl_group.jinyuntong_oem.upload_img.view.UpLoadImgView;
import com.sl_group.jinyuntong_oem.utils.CameraUtils;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.PhotoTakeUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.QRCodeUtil;
import com.sl_group.jinyuntong_oem.utils.RandomUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.utils.UriPathHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 马天 on 2018/11/18.
 * description：个人信息
 */
public class MerchantinfoActivity extends BaseActivity implements MerchantinfoView, UpLoadImgView, SetHeadView {
    private static final int PERMISSIONS_WRITE_CAMERA = 1;
    private static final int UPLOAD_HEADIMG_REQUEST_CODE = 2;
    private static final int UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE = 3;
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private CircleImageView mImgUserinfoHeadPortrait;
    private TextView mTvUserinfoName;
    private TextView mTvUserinfoLevel;
    private TextView mTvUserinfoRealname;
    private TextView mTvUserinfoOpenMerchant;
    private TextView mTvUserinfoServiceProvide;

    private String mHeadImgPath;
    private Bitmap mBitmap;
    private UploadImgPersenter mUploadImgPersenter;
    private SetHeadPersenter mSetHeadPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mImgUserinfoHeadPortrait = findViewById(R.id.img_userinfo_head_portrait);
        mTvUserinfoName = findViewById(R.id.tv_userinfo_name);
        mTvUserinfoLevel = findViewById(R.id.tv_userinfo_level);
        mTvUserinfoRealname = findViewById(R.id.tv_userinfo_realname);
        mTvUserinfoOpenMerchant = findViewById(R.id.tv_userinfo_open_merchant);
        mTvUserinfoServiceProvide = findViewById(R.id.tv_userinfo_service_provide);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("个人信息");
        mUploadImgPersenter = new UploadImgPersenter(this, this);
        mSetHeadPersenter = new SetHeadPersenter(this, this);
        //初始化文件路径
        mHeadImgPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";
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

        String headPortraitDirectoryName = (String) SPUtil.get(this, "headPortraitDirectoryName", "");
        String headPortraitFilePrefix = (String) SPUtil.get(this, "headPortraitFilePrefix", "");
        String holderName = (String) SPUtil.get(this, "holderName", "");
        int vipLevel = (int) SPUtil.get(this, "vipLevel", 0);
        String qualifiedState = (String) SPUtil.get(this, "qualifiedState", "");
        String accountNumber = (String) SPUtil.get(this, "accountNumber", "");
        String canReceived = (String) SPUtil.get(this, "canReceived", "");
        String agentName = (String) SPUtil.get(this, "agentName", "");
        if (StringUtils.isEmpty(holderName) || !StringUtils.isEmpty(qualifiedState) && !qualifiedState.equals("Y") || StringUtils.isEmpty(accountNumber) || StringUtils.isEmpty(canReceived) || StringUtils.isEmpty(agentName)) {
            MerchantinfoPersenter merchantinfoPersenter = new MerchantinfoPersenter(this, this);
            merchantinfoPersenter.merchantInfo();
            return;
        }
        displayInfo(headPortraitDirectoryName, headPortraitFilePrefix, holderName, vipLevel, qualifiedState, accountNumber, canReceived, agentName);


    }

    private void displayInfo(String headPortraitDirectoryName, String headPortraitFilePrefix, String holderName, int vipLevel, String qualifiedState, String accountNumber, String canReceived, String agentName) {
        if (!StringUtils.isEmpty(headPortraitDirectoryName) && !StringUtils.isEmpty(headPortraitFilePrefix)) {
            Picasso.with(this).load("https://name.znyoo.cn/oss-transaction/general/reviewImg?fileName=" + headPortraitDirectoryName + "&filePrefix=" + headPortraitFilePrefix).into(mImgUserinfoHeadPortrait);
        } else {
            mImgUserinfoHeadPortrait.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_head_portrait));
        }
        mTvUserinfoName.setText(holderName);
        switch (vipLevel) {
            case 0:
                mTvUserinfoLevel.setText("普通用户");
                break;
            case 1:
                mTvUserinfoLevel.setText("VIP用户");
                break;
        }
        switch (qualifiedState) {
            case "Y":
                mTvUserinfoRealname.setText("已认证");
                break;
            case "N":
                mTvUserinfoRealname.setText("未认证");
                break;
            case "a":
                if (StringUtils.isEmpty(accountNumber)) {
                    mTvUserinfoRealname.setText("未认证");
                } else {
                    mTvUserinfoRealname.setText("审核中");
                }
        }
        switch (canReceived) {
            case "t":
                mTvUserinfoOpenMerchant.setText("已开通");
                break;
            case "f":
                mTvUserinfoOpenMerchant.setText("未开通");
                break;
        }
        mTvUserinfoServiceProvide.setText(agentName);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mImgUserinfoHeadPortrait.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;

            case R.id.img_userinfo_head_portrait:
                //上传照片
                checkPermission();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        displayInfo(dataBean.getHeadPortraitDirectoryName(), dataBean.getHeadPortraitFilePrefix(), dataBean.getHolderName(), dataBean.getVipLevel(), dataBean.getQualifiedState(), dataBean.getAccountNumber(), dataBean.getCanReceived(), dataBean.getAgentName());
    }


    private void popHeadImg() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_upload_headimg, null);


        TextView tvUploadimgTakePicture = view.findViewById(R.id.tv_uploadimg_take_picture);
        TextView tvUploadimgGetPhone = view.findViewById(R.id.tv_uploadimg_get_phone);
        TextView tvUploadimgCancel = view.findViewById(R.id.tv_uploadimg_cancel);


        final PopupWindow popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this) * 9 / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        tvUploadimgTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                makeCamerMethod();
            }
        });

        tvUploadimgGetPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                getPictureRes();

            }
        });
        tvUploadimgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

    }

    //打开相机
    private void makeCamerMethod() {
        Intent idcardIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        idcardIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mHeadImgPath)));
        startActivityForResult(idcardIntent, UPLOAD_HEADIMG_REQUEST_CODE);
    }

    /**
     * 获取相册图片
     * param
     */
    private void getPictureRes() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE);
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
            popHeadImg();
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
            case UPLOAD_HEADIMG_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mHeadImgPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                QRCodeUtil.saveQrCodePicture(this, mBitmap);

                mUploadImgPersenter.uploadImg(mHeadImgPath, UPLOAD_HEADIMG_REQUEST_CODE);
                break;
            case UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE:
                String photo_path = UriPathHelper.getPath(this, data.getData());
                if (TextUtils.isEmpty(photo_path)) {
                    ToastUtils.showToast("请选择图片");
                    return;
                }
                //显示选择的图片
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(photo_path, mImgUserinfoHeadPortrait.getWidth(), mImgUserinfoHeadPortrait.getHeight());

                QRCodeUtil.saveQrCodePicture(this, mBitmap);

                mUploadImgPersenter.uploadImg(photo_path, UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE);
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
                popHeadImg();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }

    @Override
    public void uploadImgSuccess(String data, int index) {
        mSetHeadPersenter.setHeadImg(data);

    }

    @Override
    public void setHeadImgSuccess(SetHeadImgBean.DataBean data) {
        Picasso.with(this).load("https://name.znyoo.cn/oss-transaction/general/reviewImg?fileName=" + data.getHeadPortraitDirectoryName() + "&filePrefix=" + data.getHeadPortraitFilePrefix()).into(mImgUserinfoHeadPortrait);

    }
}
