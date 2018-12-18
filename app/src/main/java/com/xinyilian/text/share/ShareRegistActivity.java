package com.xinyilian.text.share;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyilian.text.R;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.MerchantInfoBean;
import com.xinyilian.text.merchant_info.persenter.MerchantinfoPersenter;
import com.xinyilian.text.merchant_info.view.MerchantinfoView;
import com.xinyilian.text.utils.PermissionSetDialogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.ShareUtils;
import com.xinyilian.text.utils.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by 马天 on 2018/11/23.
 * description：分享注册
 */
public class ShareRegistActivity extends BaseActivity implements MerchantinfoView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvShareRegistWechat;
    private TextView mTvShareRegistMoments;
    private TextView mTvShareRegistQq;
    private TextView mTvShareRegistQzone;

    //分享需要读写内存的权限
    private static final int PERMISSIONS_READ_WRITE = 1;
    //分享类型
    private SHARE_MEDIA shareType;

    private MerchantinfoPersenter mMerchantinfoPersenter;
    //分享标题
    private String title;
    //分享内容
    private String content;
    //分享链接
    private String url;


    @Override
    public int bindLayout() {
        return R.layout.activity_share_regist;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvShareRegistWechat = findViewById(R.id.tv_share_regist_wechat);
        mTvShareRegistMoments = findViewById(R.id.tv_share_regist_moments);
        mTvShareRegistQq = findViewById(R.id.tv_share_regist_qq);
        mTvShareRegistQzone = findViewById(R.id.tv_share_regist_qzone);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("分享注册");
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvShareRegistWechat.setOnClickListener(this);
        mTvShareRegistMoments.setOnClickListener(this);
        mTvShareRegistQq.setOnClickListener(this);
        mTvShareRegistQzone.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_share_regist_wechat:
                shareType = SHARE_MEDIA.WEIXIN;
                shareCheckPermission();
                break;
            case R.id.tv_share_regist_moments:
                shareType = SHARE_MEDIA.WEIXIN_CIRCLE;
                shareCheckPermission();
                break;
            case R.id.tv_share_regist_qq:
                shareType = SHARE_MEDIA.QQ;
                shareCheckPermission();
                break;
            case R.id.tv_share_regist_qzone:
                shareType = SHARE_MEDIA.QZONE;
                shareCheckPermission();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

        mMerchantinfoPersenter.merchantInfo();

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
            shareTypeMethod(shareType);
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
     * 分享
     *
     * @param type 分享类型
     */
    private void shareTypeMethod(SHARE_MEDIA type) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            mMerchantinfoPersenter.merchantInfo();
            return;
        }

        UMImage thumb = new UMImage(this, R.mipmap.share_logo);
        UMWeb web = new UMWeb(url + "?cellPhone=" + SPUtil.get(this, "inviteCode", ""));
        web.setTitle(title);//标题
        web.setThumb(thumb);//缩略图
        web.setDescription(content);//描述

        new ShareAction(this)
                .setPlatform(type)
                .withMedia(web)
                .setCallback(ShareUtils.getUMShareListener(this))
                .share();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
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
                shareTypeMethod(shareType);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }

    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        url = dataBean.getUrl();
        title = dataBean.getTitle();
        content = dataBean.getContent();
    }
}
