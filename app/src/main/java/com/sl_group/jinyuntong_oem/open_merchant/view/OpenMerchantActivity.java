package com.sl_group.jinyuntong_oem.open_merchant.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.ScanCameraActivity;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.open_merchant.persenter.OpenMerchantPersenter;
import com.sl_group.jinyuntong_oem.upload_img.persenter.UploadImgPersenter;
import com.sl_group.jinyuntong_oem.upload_img.view.UpLoadImgView;
import com.sl_group.jinyuntong_oem.utils.CameraUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.PhotoTakeUtils;
import com.sl_group.jinyuntong_oem.utils.RandomUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.web.LoadWebActivity;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by 马天 on 2018/11/17.
 * description：开通商户
 */
public class OpenMerchantActivity extends BaseActivity implements OpenMerchantView ,MerchantinfoView ,UpLoadImgView {
    private static final int PERMISSIONS_WRITE_CAMERA = 0;
    private static final int SCAN_BANKCARD_REQUEST_CODE = 1;
    private static final int UPLOAD_BUSINESS_REQUEST_CODE = 2;
    private static final int UPLOAD_DOC_REQUEST_CODE = 3;
    private static final int UPLOAD_BUSINESS_PIC_REQUEST_CODE = 4;
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvOpenMerchantRealname;
    private TextView mTvOpenMerchantIdcard;
    private ImageView mImgOpenMerchantBusiness;
    private ImageView mImgOpenMerchantDoc;
    private ImageView mImgOpenMerchantBusinessPic;
    private EditText mEtOpenMerchantShopname;
    private TextView mTvOpenMerchantShopaddress;
    private EditText mEtOpenMerchantAccountNumber;
    private ImageView mImgOpenMerchantScan;
    private EditText mEtOpenMerchantTel;
    private Button mBtnOpenMerchantCommit;
    private Bitmap mBitmap;

    private String mBusinessPath;
    private String mDocPath;
    private String mBusinessPicPath;

    private OpenMerchantPersenter mOpenMerchantPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private UploadImgPersenter mUploadImgPersenter;

    private String placeOfBusinessUUID;
    private String doorheadPhotoUUID;
    private String businessLicenseUUID;


    //省份集合
    private ArrayList<String> mProList;
    //城市集合
    private ArrayList<ArrayList<String>> mTotalCityList = new ArrayList<>();
    //区集合
    private ArrayList<ArrayList<ArrayList<String>>> mTotalAreaList = new ArrayList<>();
    //显示的省份名称
    private String pro_name_dis;
    //显示的城市名称
    private String city_name_dis;
    //显示的区县名称
    private String area_name_dis;
    @Override
    public int bindLayout() {
        return R.layout.activity_open_merchant;
    }

    @Override
    public void initView(View view) {

        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvOpenMerchantRealname = findViewById(R.id.tv_open_merchant_realname);
        mTvOpenMerchantIdcard = findViewById(R.id.tv_open_merchant_idcard);
        mImgOpenMerchantBusiness = findViewById(R.id.img_open_merchant_business);
        mImgOpenMerchantDoc = findViewById(R.id.img_open_merchant_doc);
        mImgOpenMerchantBusinessPic = findViewById(R.id.img_open_merchant_business_pic);
        mEtOpenMerchantShopname = findViewById(R.id.et_open_merchant_shopname);
        mTvOpenMerchantShopaddress = findViewById(R.id.tv_open_merchant_shopaddress);
        mEtOpenMerchantAccountNumber = findViewById(R.id.et_open_merchant_accountNumber);
        mImgOpenMerchantScan = findViewById(R.id.img_open_merchant_scan);
        mEtOpenMerchantTel = findViewById(R.id.et_open_merchant_tel);
        mBtnOpenMerchantCommit = findViewById(R.id.btn_open_merchant_commit);

    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("开通商户权限");

        mOpenMerchantPersenter = new OpenMerchantPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        mUploadImgPersenter = new UploadImgPersenter(this,this);

        initJsonData();


        //初始化文件路径
        mBusinessPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";
        mDocPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";
        mBusinessPicPath = Environment.getExternalStorageDirectory().getPath() + CommonSet.IMG_CACHE + "/IMG_" + System.currentTimeMillis() + RandomUtils.randomString(CommonSet.RANDOM_STR, 6) + ".png";

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
            return;
        }

        mesureImgHeight();

    }

    private void mesureImgHeight() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mImgOpenMerchantBusiness.measure(w, h);
        int width = mImgOpenMerchantBusiness.getMeasuredWidth();

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImgOpenMerchantBusiness.getLayoutParams();
        layoutParams.height = width;
        mImgOpenMerchantBusiness.setLayoutParams(layoutParams);
        mImgOpenMerchantDoc.setLayoutParams(layoutParams);
        mImgOpenMerchantBusinessPic.setLayoutParams(layoutParams);
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mImgOpenMerchantBusiness.setOnClickListener(this);
        mImgOpenMerchantDoc.setOnClickListener(this);
        mImgOpenMerchantBusinessPic.setOnClickListener(this);
        mTvOpenMerchantShopaddress.setOnClickListener(this);
        mImgOpenMerchantScan.setOnClickListener(this);
        mBtnOpenMerchantCommit.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.img_open_merchant_business:
                Intent businessIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                businessIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mBusinessPath)));
                startActivityForResult(businessIntent, UPLOAD_BUSINESS_REQUEST_CODE);
                //上传营业场所照
                break;
            case R.id.img_open_merchant_doc:
                //上传门头照
                Intent docIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                docIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mDocPath)));
                startActivityForResult(docIntent, UPLOAD_DOC_REQUEST_CODE);
                break;
            case R.id.img_open_merchant_business_pic:
                //上传营业执照
                Intent businessPicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                businessPicIntent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtils.getUriForFile(this, new File(mBusinessPicPath)));
                startActivityForResult(businessPicIntent, UPLOAD_BUSINESS_PIC_REQUEST_CODE);
                break;
            case R.id.tv_open_merchant_shopaddress:
                //店铺地址
                pickerViewMethod();
                break;
            case R.id.img_open_merchant_scan:
                //结算卡号
                Intent scanIntent = new Intent(OpenMerchantActivity.this, ScanCameraActivity.class);
                startActivityForResult(scanIntent, SCAN_BANKCARD_REQUEST_CODE);
                break;
            case R.id.btn_open_merchant_commit:
                String shopname = mEtOpenMerchantShopname.getText().toString().trim();
                String shopaddress = mTvOpenMerchantShopaddress.getText().toString().trim();
                String accountNumber = mEtOpenMerchantAccountNumber.getText().toString().trim();
                String tel = mEtOpenMerchantTel.getText().toString().trim();
                mOpenMerchantPersenter.openMerchant(placeOfBusinessUUID,doorheadPhotoUUID,businessLicenseUUID,shopname,shopaddress,accountNumber,tel);
                break;

        }
    }

    @Override
    public void doBusiness(Context mContext) {
        checkPermission();
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
            mMerchantinfoPersenter.merchantInfo();
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
                    mEtOpenMerchantAccountNumber.setText(cardNumber.replace(" ", ""));
                } else {
                    mEtOpenMerchantAccountNumber.setText(cardNumber);
                    mEtOpenMerchantAccountNumber.setSelection(cardNumber.length());
                }

                //
                //                BitmapFactory.Options options = new BitmapFactory.Options();
                //                options.inPreferredConfig = Bitmap.Config.RGB_565;
                //                Bitmap bitmap = BitmapFactory.decodeByteArray(mDatas, 0, mDatas.length, options);
                //
                //                Bitmap bitmap1 = zoomBitmap(bitmap, 1200, 720);
                //                int w = bitmap1.getWidth(); // 得到图片的宽，高
                //                int h = bitmap1.getHeight();
                //                mBitmap= Bitmap.createBitmap(bitmap1, 75, 0, w - 180, h, null, false);
                //                saveBitmap(mBitmap, mDocPath);
                break;
            case UPLOAD_BUSINESS_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mBusinessPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                saveBitmap(mBitmap, mBusinessPath);
                mImgOpenMerchantBusiness.setImageBitmap(mBitmap);
                mUploadImgPersenter.uploadImg(mBusinessPath, UPLOAD_BUSINESS_REQUEST_CODE);
                break;
            case UPLOAD_DOC_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mDocPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                saveBitmap(mBitmap, mDocPath);
                mImgOpenMerchantDoc.setImageBitmap(mBitmap);
                mUploadImgPersenter.uploadImg(mDocPath, UPLOAD_DOC_REQUEST_CODE);
                break;
            case UPLOAD_BUSINESS_PIC_REQUEST_CODE:
                mBitmap = PhotoTakeUtils.decodeSampledBitmapFromFile(mBusinessPicPath, 1200, 720);
                if (mBitmap == null) {
                    return;
                }
                saveBitmap(mBitmap, mBusinessPicPath);
                mImgOpenMerchantBusinessPic.setImageBitmap(mBitmap);
                mUploadImgPersenter.uploadImg(mBusinessPicPath, UPLOAD_BUSINESS_PIC_REQUEST_CODE);
                break;


        }
    }

//    /**
//     * 压缩位图
//     *
//     * @param bitmap 需要压缩的位图
//     * @param w      图片的宽度
//     * @param h      图片的高度
//     */
//    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        Matrix matrix = new Matrix();
//        float scaleWidth = ((float) w / width);
//        float scaleHeight = ((float) h / height);
//        matrix.postScale(scaleWidth, scaleHeight);
//        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//    }

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
                mMerchantinfoPersenter.merchantInfo();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(this);
            }
        }
    }


    @Override
    public void skipActivity(String data) {
        Bundle bundle = new Bundle();
        bundle.putString("url",data);
        startActivity(LoadWebActivity.class,bundle);
        finish();
    }


    //滚动选择
    private void pickerViewMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();

        // 隐藏软键盘
        if (isOpen) {
            imm.hideSoftInputFromWindow(mTvOpenMerchantShopaddress.getWindowToken(), 0);
        }

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(OpenMerchantActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                pro_name_dis = mProList.get(options1);
                city_name_dis = mTotalCityList.get(options1).get(option2);
                area_name_dis = mTotalAreaList.get(options1).get(option2).get(options3);
                if (pro_name_dis.equals(city_name_dis)) {
                    mTvOpenMerchantShopaddress.setText(pro_name_dis + "-" + area_name_dis);
                } else {
                    mTvOpenMerchantShopaddress.setText(pro_name_dis + "-" + city_name_dis + "-" + area_name_dis);
                }
            }
        })
                .setSubmitText(getString(R.string.sure))//确定按钮文字
                .setCancelText(getString(R.string.cancel))//取消按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(OpenMerchantActivity.this, R.color.mainColor))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(OpenMerchantActivity.this, R.color.grayColor_6))//取消按钮文字颜色
                .setTitleText("选择店铺地址")//标题
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(mProList, mTotalCityList, mTotalAreaList);
        pvOptions.show();
    }

    /**
     * 从assert文件夹中获取省市json数据
     */
    private void initJsonData() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "gb2312"));//根据字节长度设置编码
            }
            is.close();//关闭流
            org.json.JSONObject jsonObject = new org.json.JSONObject(sb.toString());//为json赋值
            JSONArray pro_array = jsonObject.getJSONArray("province");//获取整个json数据
            mProList = new ArrayList<>();//封装数据
            for (int i = 0; i < pro_array.length(); i++) {
                org.json.JSONObject jsonP = pro_array.getJSONObject(i);//jsonArray转jsonObject
                String pro_name = jsonP.getString("name");//获取所有的省
                mProList.add(pro_name);//封装所有的省

                JSONArray city_array = jsonP.getJSONArray("city");
                ArrayList<String> city_list = new ArrayList<>();
                ArrayList<ArrayList<String>> area_list = new ArrayList<>();
                for (int j = 0; j < city_array.length(); j++) {
                    org.json.JSONObject city_obj = city_array.getJSONObject(j);//jsonArray转jsonObject
                    String city_name = city_obj.getString("name");//获取所有的市
                    city_list.add(city_name);

                    JSONArray area_array = city_obj.getJSONArray("area");
                    ArrayList<String> area_list_1 = new ArrayList<>();
                    for (int k = 0; k < area_array.length(); k++) {
                        org.json.JSONObject area_obj = area_array.getJSONObject(k);//jsonArray转jsonObject
                        String area_name = area_obj.getString("name");//获取所有的县区
                        area_list_1.add(area_name);
                    }
                    area_list.add(area_list_1);
                }
                mTotalCityList.add(city_list);
                mTotalAreaList.add(area_list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        mTvOpenMerchantRealname.setText(dataBean.getHolderName());
        mTvOpenMerchantIdcard.setText(dataBean.getIdCard());
    }

    @Override
    public void uploadImgSuccess(String data, int index) {
        switch (index) {
            case UPLOAD_BUSINESS_REQUEST_CODE:
                placeOfBusinessUUID = data;
                mImgOpenMerchantBusiness.setImageBitmap(mBitmap);
                break;
            case UPLOAD_DOC_REQUEST_CODE:
                doorheadPhotoUUID = data;
                mImgOpenMerchantDoc.setImageBitmap(mBitmap);
                break;
            case UPLOAD_BUSINESS_PIC_REQUEST_CODE:
                businessLicenseUUID = data;
                mImgOpenMerchantBusinessPic.setImageBitmap(mBitmap);
                break;
        }

    }
}
