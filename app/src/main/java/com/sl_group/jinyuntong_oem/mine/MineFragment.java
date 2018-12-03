package com.sl_group.jinyuntong_oem.mine;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.RealnameStates;
import com.sl_group.jinyuntong_oem.base.BaseFragment;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.bean.SetHeadImgBean;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListActivity;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoActivity;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.pay_bill.view.PayBillActivity;
import com.sl_group.jinyuntong_oem.safe_set.SafeSetActivity;
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
 * Created by 马天 on 2018/11/13.
 * description：我的
 */
public class MineFragment extends BaseFragment implements MerchantinfoView, UpLoadImgView, SetHeadView {
    private static final int PERMISSIONS_WRITE_CAMERA = 1;
    private static final int UPLOAD_HEADIMG_REQUEST_CODE = 2;
    private static final int UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE = 3;
    private ImageView mImgMineMenu;
    private CircleImageView mImgMineHeadPortrait;
    private TextView mTvMineTel;
    private ImageView mImgMineLevel;
    private TextView mTvMineRealnameStates;
    private ImageView mImgMineRealnameStates;
    private TextView mTvMinePayBill;
    private TextView mTvMineMyPackage;
    private TextView mTvMineSystemNotice;
    private TextView mTvMineMyService;
    private TextView mTvMineSaveSet;

    private MerchantinfoPersenter mMerchantinfoPersenter;

    private String mHeadImgPath;
    private Bitmap mBitmap;
    private UploadImgPersenter mUploadImgPersenter;
    private SetHeadPersenter mSetHeadPersenter;

    private String headPortraitDirectoryName;
    private String headPortraitFilePrefix;
    private String cellPhone;
    private int vipLevel;
    private String qualifiedState;
    private String accountNumber;
    private String holderName;
    private String idCard;
    private String tel;
    private boolean isQueryRealname = false;

    @Override
    public void onStart() {
        super.onStart();
        boolean isReflash = (boolean) SPUtil.get(getActivity(), "isReflash", false);
        if (isReflash) {
            mMerchantinfoPersenter.merchantInfo();
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View view) {
        mImgMineMenu = view.findViewById(R.id.img_mine_menu);
        mImgMineHeadPortrait = view.findViewById(R.id.img_mine_head_portrait);
        mTvMineTel = view.findViewById(R.id.tv_mine_tel);
        mImgMineLevel = view.findViewById(R.id.img_mine_level);
        mTvMineRealnameStates = view.findViewById(R.id.tv_mine_realname_states);
        mImgMineRealnameStates = view.findViewById(R.id.img_mine_realname_states);
        mTvMinePayBill = view.findViewById(R.id.tv_mine_pay_bill);
        mTvMineMyPackage = view.findViewById(R.id.tv_mine_my_package);
        mTvMineSystemNotice = view.findViewById(R.id.tv_mine_system_notice);
        mTvMineMyService = view.findViewById(R.id.tv_mine_my_service);
        mTvMineSaveSet = view.findViewById(R.id.tv_mine_save_set);
    }

    @Override
    public void initData() {
        mMerchantinfoPersenter = new MerchantinfoPersenter(getActivity(), this);
        mUploadImgPersenter = new UploadImgPersenter(getActivity(), this);
        mSetHeadPersenter = new SetHeadPersenter(getActivity(), this);

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


        headPortraitDirectoryName = (String) SPUtil.get(getActivity(), "headPortraitDirectoryName", "");
        headPortraitFilePrefix = (String) SPUtil.get(getActivity(), "headPortraitFilePrefix", "");
        cellPhone = (String) SPUtil.get(getActivity(), "cellPhone", "");
        vipLevel = (int) SPUtil.get(getActivity(), "vipLevel", 0);
        qualifiedState = (String) SPUtil.get(getActivity(), "qualifiedState", "");
        accountNumber = (String) SPUtil.get(getActivity(), "accountNumber", "");
        holderName = (String) SPUtil.get(getActivity(), "holderName", "");
        idCard = (String) SPUtil.get(getActivity(), "idCard", "");
        tel = (String) SPUtil.get(getActivity(), "tel", "");

        if (StringUtils.isEmpty(cellPhone) || !StringUtils.isEmpty(qualifiedState) && !qualifiedState.equals("Y")
                || StringUtils.isEmpty(accountNumber) || StringUtils.isEmpty(holderName) || StringUtils.isEmpty(idCard)
                || StringUtils.isEmpty(tel)) {
            mMerchantinfoPersenter.merchantInfo();
            return;
        }
        displayMerchantInfo(headPortraitDirectoryName, headPortraitFilePrefix, cellPhone, vipLevel, qualifiedState, accountNumber);

    }

    @Override
    public void setListener() {
        mImgMineMenu.setOnClickListener(this);
        mImgMineRealnameStates.setOnClickListener(this);
        mTvMinePayBill.setOnClickListener(this);
        mTvMineMyPackage.setOnClickListener(this);
        mTvMineSystemNotice.setOnClickListener(this);
        mTvMineMyService.setOnClickListener(this);
        mTvMineSaveSet.setOnClickListener(this);
        mImgMineHeadPortrait.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_mine_menu:
                //个人信息
                startActivity(MerchantinfoActivity.class);
                break;
            case R.id.img_mine_head_portrait:
                checkPermission();
                break;
            case R.id.img_mine_realname_states:
                if (!new RealnameStates(getActivity()).isRealname()) {
                    return;
                }
                if (StringUtils.isEmpty(accountNumber) || StringUtils.isEmpty(holderName) || StringUtils.isEmpty(idCard) || StringUtils.isEmpty(tel)) {
                    isQueryRealname = true;
                    mMerchantinfoPersenter.merchantInfo();
                    return;
                }
                popRealnameInfo();
                break;
            case R.id.tv_mine_pay_bill:
                //付款账单
                startActivity(PayBillActivity.class);
                break;
            case R.id.tv_mine_my_package:
                //我的卡包
                startActivity(CreditCardListActivity.class);
                break;
            case R.id.tv_mine_system_notice:
                //系统公告
                break;
            case R.id.tv_mine_my_service:
                //我的客服
                break;
            case R.id.tv_mine_save_set:
                //安全设置
                startActivity(SafeSetActivity.class);
                break;
        }
    }


    @Override
    public void doBusiness(Context mContext) {

    }


    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        SPUtil.remove(getActivity(), "isReflash");
        headPortraitDirectoryName = dataBean.getHeadPortraitDirectoryName();
        headPortraitFilePrefix = dataBean.getHeadPortraitFilePrefix();
        cellPhone = dataBean.getCellPhone();
        vipLevel = dataBean.getVipLevel();
        qualifiedState = dataBean.getQualifiedState();
        accountNumber = dataBean.getAccountNumber();
        if (isQueryRealname) {
            popRealnameInfo();
        } else {
            displayMerchantInfo(headPortraitDirectoryName, headPortraitFilePrefix, cellPhone, vipLevel, qualifiedState, accountNumber);

        }
    }

    private void displayMerchantInfo(String headPortraitDirectoryName, String headPortraitFilePrefix, String cellPhone, int vipLevel, String qualifiedState, String accountNumber) {
        if (!StringUtils.isEmpty(headPortraitDirectoryName) && !StringUtils.isEmpty(headPortraitFilePrefix)) {
            Picasso.with(getActivity()).load("https://name.znyoo.cn/oss-transaction/general/reviewImg?fileName=" + headPortraitDirectoryName + "&filePrefix=" + headPortraitFilePrefix).into(mImgMineHeadPortrait);
        } else {
            mImgMineHeadPortrait.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_head_portrait));
        }
        mTvMineTel.setText(cellPhone);
        switch (vipLevel) {
            case 0:
                mImgMineLevel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_putong));
                break;
            case 1:
                mImgMineLevel.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_vip));
                break;
        }

        switch (qualifiedState) {
            case "Y":
                mTvMineRealnameStates.setVisibility(View.GONE);
                mImgMineRealnameStates.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_authenticated));
                break;
            case "N":
                mTvMineRealnameStates.setVisibility(View.VISIBLE);
                mTvMineRealnameStates.setText("实名认证：未认证");
                mImgMineRealnameStates.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_go_certification));
                break;
            case "a":
                if (StringUtils.isEmpty(accountNumber)) {
                    mTvMineRealnameStates.setVisibility(View.VISIBLE);
                    mTvMineRealnameStates.setText("实名认证：未认证");
                    mImgMineRealnameStates.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_go_certification));
                } else {
                    mTvMineRealnameStates.setVisibility(View.VISIBLE);
                    mTvMineRealnameStates.setText("实名认证：审核中");
                    mImgMineRealnameStates.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.my_audit));
                }
        }
    }

    private void popRealnameInfo() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_realname_info, null);

        ImageView imgUsercenterRealnameClose = view.findViewById(R.id.img_usercenter_realname_close);
        TextView tvUsercenterRealnameName = view.findViewById(R.id.tv_usercenter_realname_name);
        TextView tvUsercenterRealnameIdcard = view.findViewById(R.id.tv_usercenter_realname_idcard);
        TextView tvUsercenterRealnameCardnumber = view.findViewById(R.id.tv_usercenter_realname_cardnumber);
        TextView tvUsercenterRealnameTel = view.findViewById(R.id.tv_usercenter_realname_tel);
        //显示真实姓名
        tvUsercenterRealnameName.setText(StringUtils.getStarString(holderName, 0, holderName.length() - 1));
        //身份证号
        tvUsercenterRealnameIdcard.setText(StringUtils.getStarString(idCard, 4, idCard.length() - 4));
        //银行卡号
        tvUsercenterRealnameCardnumber.setText(StringUtils.getStarString(accountNumber, 4, accountNumber.length() - 3));
        //手机号
        tvUsercenterRealnameTel.setText(StringUtils.getStarString(tel, 3, tel.length() - 4));

        final PopupWindow popupWindow = PopupWindowUtils.getPop(getActivity(), view, DisplayUtils.getScreenWidth(getActivity()) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        //屏幕中心缩放弹出动画
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        //弹出位置
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        imgUsercenterRealnameClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isQueryRealname = false;
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    private void popHeadImg() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_upload_headimg, null);

        TextView tvUploadimgTakePicture = view.findViewById(R.id.tv_uploadimg_take_picture);
        TextView tvUploadimgGetPhone = view.findViewById(R.id.tv_uploadimg_get_phone);
        TextView tvUploadimgCancel = view.findViewById(R.id.tv_uploadimg_cancel);


        final PopupWindow popupWindow = PopupWindowUtils.getPop(getActivity(), view, DisplayUtils.getScreenWidth(getActivity()) * 9 / 10, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

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
        idcardIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(getActivity(), new File(mHeadImgPath)));
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
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(getActivity(),
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
            requestPermissions(
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
                QRCodeUtil.saveQrCodePicture(getActivity(), mBitmap);
                mUploadImgPersenter.uploadImg(mHeadImgPath, UPLOAD_HEADIMG_REQUEST_CODE);
                break;
            case UPLOAD_HEADIMG_GET_PHONE_REQUEST_CODE:
                String photo_path = UriPathHelper.getPath(getActivity(), data.getData());
                if (TextUtils.isEmpty(photo_path)) {
                    ToastUtils.showToast("请选择图片");
                    return;
                }
                //显示选择的图片
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(photo_path, mImgMineHeadPortrait.getWidth(), mImgMineHeadPortrait.getHeight());

                QRCodeUtil.saveQrCodePicture(getActivity(), mBitmap);
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
                PermissionSetDialogUtils.showSetPermission(getActivity());
            }
        }
    }

    @Override
    public void uploadImgSuccess(String data, int index) {
        mSetHeadPersenter.setHeadImg(data);

    }

    @Override
    public void setHeadImgSuccess(SetHeadImgBean.DataBean data) {
        Picasso.with(getActivity()).load("https://name.znyoo.cn/oss-transaction/general/reviewImg?fileName=" + data.getHeadPortraitDirectoryName() + "&filePrefix=" + data.getHeadPortraitFilePrefix()).into(mImgMineHeadPortrait);
    }
}
