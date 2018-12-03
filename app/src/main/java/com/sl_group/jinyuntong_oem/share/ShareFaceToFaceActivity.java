package com.sl_group.jinyuntong_oem.share;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.QRCodeUtil;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.web.LoadWebActivity;

/**
 * Created by 马天 on 2018/11/23.
 * description：面对面分享
 */
public class ShareFaceToFaceActivity extends BaseActivity {
    private static final int PERMISSIONS_WRITE_CAMERA = 1;
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private ImageView mImgShareFaceToFaceQrcode;
    private TextView mTvShareFaceToFaceLongclick;

    private  Bitmap qrcodeBitmap;


    @Override
    public int bindLayout() {
        return R.layout.activity_face_to_face;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mImgShareFaceToFaceQrcode = findViewById(R.id.img_share_face_to_face_qrcode);
        mTvShareFaceToFaceLongclick = findViewById(R.id.tv_share_face_to_face_longclick);
    }

    @Override
    public void initData() {

        mTvActionbarTitle.setText("面对面分享");
        //二维码宽度，，，，正常情况下是
        int qrcodeWidth = DisplayUtils.getScreenWidth(this)/3;
        //如果二维码宽度小于0或者宽度大于屏幕的一半，，是不行的，，，
        if (qrcodeWidth < 0 || qrcodeWidth > DisplayUtils.getScreenWidth(this) / 2) {
            qrcodeWidth = DisplayUtils.getScreenWidth(this) / 3;
        }

        //生成二维码
        qrcodeBitmap = QRCodeUtil.createQRImage(CommonSet.REGIST+ "?cellPhone=" + SPUtil.get(this, "inviteCode", ""),qrcodeWidth, qrcodeWidth, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mImgShareFaceToFaceQrcode.setImageBitmap(qrcodeBitmap);
//        mTvShareFaceToFaceLongclick.setText(getString(R.string.longclick_qrcode));
//        mImgShareFaceToFaceQrcode.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showShareDiscernSavePop();
//                return true;
//            }
//        });
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

    }


    /**
     * 分享识别保存弹窗
     */
    private void showShareDiscernSavePop() {
        //加载弹窗布局
        View view = LayoutInflater.from(this).inflate(R.layout.pop_share_discern_save, null);

        TextView tvPopDiscernQrcode =  view.findViewById(R.id.tv_pop_save_qrcode);
        TextView tvPopSaveQrcode = view.findViewById(R.id.tv_pop_share_qrcode);
        TextView tvPopShareCancel =  view.findViewById(R.id.tv_pop_save_qrcode_cancel);

        final PopupWindow popupWindow = PopupWindowUtils.getPop(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        //识别二维码
        tvPopDiscernQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", CommonSet.REGIST+ "?cellPhone=" + SPUtil.get(ShareFaceToFaceActivity.this, "inviteCode", ""));
                startActivity(LoadWebActivity.class, bundle);
            }
        });
        //保存二维码
        tvPopSaveQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                if (null == qrcodeBitmap) {
                    ToastUtils.showToast("请先生成二维码");
                    return;
                }
                checkPermission();

            }
        });
        //取消
        tvPopShareCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 检查拍照所需要的相应权限
     */
    private void checkPermission() {
        // 检查是否有相应的权限
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这权限全都拥有, 则直接执行更新
        if (isAllGranted) {
            QRCodeUtil.saveQrCodePicture(ShareFaceToFaceActivity.this, qrcodeBitmap);
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSIONS_WRITE_CAMERA
            );
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
                QRCodeUtil.saveQrCodePicture(ShareFaceToFaceActivity.this, qrcodeBitmap);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }
}
