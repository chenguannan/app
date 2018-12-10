package com.sl_group.jinyuntong_oem.share;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.ProgressDialogUtils;
import com.sl_group.jinyuntong_oem.utils.QRCodeUtil;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.ShareUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 马天 on 2018/11/25.
 * description：文案详情
 */
public class OfficialDetailsActivity extends BaseActivity implements MerchantinfoView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvActionbarShare;
    private ImageView mImgBuisnessDetailsLeft;
    private LinearLayout mLlBuisnessDetailsRight;
    private ImageView mImgBuisnessDetailsTop;
    private RelativeLayout mRlBuisnessDetailsBottom;
    private ImageView mImgBuisnessDetailsQrcode;


    //分享读写内存动态授权
    private static final int PERMISSIONS_READ_WRITE = 1;
    //截屏文件名
    private String screenShotPic;
    //选择分享方式弹窗
    private PopupWindow popupWindow;
    //上部图片高度
    private float topHeight;
    //左部图片宽度
    private float leftWidth;
    //左部位图
    private Bitmap leftBitmap;
    //上部位图
    private Bitmap topBitmap;
    //底部背景ID
    private int bottomImgId;

    private MerchantinfoPersenter mMerchantinfoPersenter;


    @Override
    public int bindLayout() {
        return R.layout.activity_official_details;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvActionbarShare = findViewById(R.id.tv_actionbar_share);
        mImgBuisnessDetailsLeft = findViewById(R.id.img_buisness_details_left);
        mLlBuisnessDetailsRight = findViewById(R.id.ll_buisness_details_right);
        mImgBuisnessDetailsTop = findViewById(R.id.img_buisness_details_top);
        mRlBuisnessDetailsBottom = findViewById(R.id.rl_buisness_details_bottom);
        mImgBuisnessDetailsQrcode = findViewById(R.id.img_buisness_details_qrcode);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("文案");

        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        //初始化截屏文件名，时间戳，，，可以加随机数的。。。
        screenShotPic = "IMG_" + System.currentTimeMillis() + ".png";
        //以下数据为了更好地适配。。。。
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt("position");
            switch (position) {
                case 2:
                    topHeight = 197;
                    leftWidth = 219;
                    leftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan3_1);
                    topBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan3_2);
                    bottomImgId = R.mipmap.wenan3_3;
                    break;
                case 0:
                    topHeight = 194;
                    leftWidth = 507;
                    leftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan2_1);
                    topBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan2_2);
                    bottomImgId = R.mipmap.wenan2_3;
                    break;
                case 3:
                    topHeight = 161;
                    leftWidth = 90;
                    leftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan4_1);
                    topBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan4_2);
                    bottomImgId = R.mipmap.wenan4_3;
                    break;
                case 1:
                    topHeight = 171;
                    leftWidth = 296;
                    leftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan1_1);
                    topBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wenan1_2);
                    bottomImgId = R.mipmap.wenan1_3;
                    break;
            }
        }

        //上部权重  按750*423来的
        float topWeight = topHeight / 423;
        //左部权重
        float leftWeight = leftWidth / 750;
        //左侧部分参数
        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, leftWeight);
        mImgBuisnessDetailsLeft.setLayoutParams(leftParams);
        //加载左侧图片
        mImgBuisnessDetailsLeft.setImageBitmap(leftBitmap);

        //右侧参数
        LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1 - leftWeight);
        mLlBuisnessDetailsRight.setLayoutParams(rightParams);
        //上部参数
        LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, topWeight);
        mImgBuisnessDetailsTop.setLayoutParams(topParams);
        //加载上部图片
        mImgBuisnessDetailsTop.setImageBitmap(topBitmap);

        //底部参数
        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1 - topWeight);
        mRlBuisnessDetailsBottom.setLayoutParams(bottomParams);
        //加载底部图片
        mRlBuisnessDetailsBottom.setBackgroundResource(bottomImgId);

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvActionbarShare.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                onBackPressed();
                break;
            case R.id.tv_actionbar_share:
                shareCheckPermission();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mMerchantinfoPersenter.merchantInfo();
    }

    /**
     * @param dataBean 商户信息对象
     */
    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        //二维码宽度
        int qrcodeWidth = DisplayUtils.getScreenWidth(this) / 4;
        //注册生成二维码
        mImgBuisnessDetailsQrcode.setImageBitmap(QRCodeUtil.createQRImage(dataBean.getUrl() + "?cellPhone=" + SPUtil.get(this, "inviteCode", ""), qrcodeWidth, qrcodeWidth, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));


    }

    /**
     * 分享权限检查
     */
    private void shareCheckPermission() {

        // 检查是否有相应的权限
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }
        );
        // 如果这权限全都拥有, 则直接执行更新
        if (isAllGranted) {
            ProgressDialogUtils.showProgress(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(100);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = screenshot();
                            if (bitmap == null) {
                                ToastUtils.showToast("截屏失败");
                                return;
                            }
                            ProgressDialogUtils.dismissProgress();
                            //显示分享弹窗
                            showShareTypePop(bitmap);
                        }
                    });
                }
            }).start();

            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    },
                    PERMISSIONS_READ_WRITE
            );
        }

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
                ProgressDialogUtils.showProgress(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = screenshot();
                                if (bitmap == null) {
                                    ToastUtils.showToast("截屏失败");
                                    return;
                                }
                                ProgressDialogUtils.dismissProgress();

                                showShareTypePop(bitmap);
                            }
                        });
                    }
                }).start();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }

    /**
     * 显示分享类型
     *
     * @param bitmap 文案截图
     */
    private void showShareTypePop(final Bitmap bitmap) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_share, null);

        TextView tvPopShareWechat = view.findViewById(R.id.tv_pop_share_wechat);
        TextView tvPopShareMoments = view.findViewById(R.id.tv_pop_share_moments);
        TextView tvPopShareQq = view.findViewById(R.id.tv_pop_share_qq);
        TextView tvPopShareQzone = view.findViewById(R.id.tv_pop_share_qzone);

        popupWindow = PopupWindowUtils.getPop(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //微信分享
        tvPopShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                shareTypeMethod(SHARE_MEDIA.WEIXIN, bitmap);
            }
        });
        //朋友圈分享
        tvPopShareMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                shareTypeMethod(SHARE_MEDIA.WEIXIN_CIRCLE, bitmap);
            }
        });
        //QQ分享
        tvPopShareQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                shareTypeMethod(SHARE_MEDIA.QQ, bitmap);
            }
        });
        //QQ空间分享
        tvPopShareQzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                shareTypeMethod(SHARE_MEDIA.QZONE, bitmap);
            }
        });
    }

    /**
     * 分享方式
     *
     * @param type   分享类型
     * @param bitmap 文案截屏
     */
    private void shareTypeMethod(SHARE_MEDIA type, Bitmap bitmap) {
        UMImage thumb = new UMImage(this, bitmap);
        new ShareAction(this)
                .setPlatform(type)
                .withMedia(thumb)
                .setCallback(ShareUtils.getUMShareListener(this))
                .share();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    /**
     * 截屏
     */
    private Bitmap screenshot() {
        Bitmap bitmap = null;
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + screenShotPic;
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();

                int width = DisplayUtils.getScreenWidth(this);
                int height = DisplayUtils.getScreenHeight(this);
                //截屏。。。。。。。。。。。。。。。。。。。。
                bitmap = Bitmap.createBitmap(
                        BitmapFactory.decodeFile(filePath)
                        , 0, (int) (height * 0.40), width, (int) (height * 0.305d));
            } catch (Exception ignored) {
            }
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            finish();
        }
    }

}
