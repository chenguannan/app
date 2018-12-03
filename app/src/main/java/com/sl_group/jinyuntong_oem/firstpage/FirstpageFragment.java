package com.sl_group.jinyuntong_oem.firstpage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.RealnameStates;
import com.sl_group.jinyuntong_oem.base.BaseFragment;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListActivity;
import com.sl_group.jinyuntong_oem.firstpage.persenter.FirstpagePersenter;
import com.sl_group.jinyuntong_oem.firstpage.view.FirstpageView;
import com.sl_group.jinyuntong_oem.gather.view.GatherActivity;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.myshop.MyShopActivty;
import com.sl_group.jinyuntong_oem.myshop.gather_rate.GatherRateActivity;
import com.sl_group.jinyuntong_oem.news.view.NewsActivity;
import com.sl_group.jinyuntong_oem.open_merchant.view.OpenMerchantActivity;
import com.sl_group.jinyuntong_oem.scan_input_money.view.ScanQrcodeInputMoneyActivity;
import com.sl_group.jinyuntong_oem.system_prop.persenter.SystemPropPersenter;
import com.sl_group.jinyuntong_oem.system_prop.view.SystemPropView;
import com.sl_group.jinyuntong_oem.utils.AppUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.PermissionSetDialogUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.vip.view.VipCenterActivity;
import com.sl_group.jinyuntong_oem.web.LoadWebActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;
import static com.sl_group.jinyuntong_oem.utils.AppUtils.getVersionName;

/**
 * Created by 马天 on 2018/11/13.
 * description：首页
 */
public class FirstpageFragment extends BaseFragment implements FirstpageView, SystemPropView, MerchantinfoView {
    private static final int PERMISSIONS_READ_WRITE_CAMERA = 1;
    private static final int REQUEST_CODE_SCAN = 1;
    private ImageView mImgFirstpageNews;
    private TextView mTvFirstpageScan;
    private TextView mTvFirstpageGather;
    private TextView mTvFirstpageCreditcard;
    private TextView mTvFirstpageShop;
    private TextView mTvFirstpageRecommendCard;
    private TextView mTvFirstpageGroupBuy;
    private TextView mTvFirstpageServiceCenter;
    private TextView mTvFirstpageIntegralShop;
    private RelativeLayout mRlFirstpageVipCenter;
    private TextView mTvFirstpageMyshop;
    private TextView mTvFirstpageGuide;
    private RelativeLayout mRlFirstpageShopDiscount;
    private TextView mTvFirstpageWater;
    private TextView mTvFirstpageEle;
    private TextView mTvFirstpageGas;
    private TextView mTvFirstpageShebaoQuery;
    private TextView mTvFirstpageGongjijinQuery;
    private TextView mTvFirstpageExpressQuery;
    private TextView mTvFirstpageCompanyQuery;

    private FirstpagePersenter mFirstpagePersenter;
    private SystemPropPersenter mSystemPropPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private Bundle mBundle;
    private String qrCodeContent;
    private String canReceived;
    private boolean isclickGather = false;
    private static final int PERMISSIONS_READ_WRITE = 2;
    //更新日志
    private String changelog;
    //版本名
    private String versionShort;
    //更新下载地址
    private String install_url;
    //更新进度条
    private ProgressDialog mProgressDialog;

    @Override
    public int bindLayout() {
        return R.layout.fragment_firstpage;
    }

    @Override
    public void initView(View view) {
        mImgFirstpageNews = view.findViewById(R.id.img_firstpage_news);
        mTvFirstpageScan = view.findViewById(R.id.tv_firstpage_scan);
        mTvFirstpageGather = view.findViewById(R.id.tv_firstpage_gather);
        mTvFirstpageCreditcard = view.findViewById(R.id.tv_firstpage_creditcard);
        mTvFirstpageShop = view.findViewById(R.id.tv_firstpage_shop);
        mTvFirstpageRecommendCard = view.findViewById(R.id.tv_firstpage_recommend_card);
        mTvFirstpageGroupBuy = view.findViewById(R.id.tv_firstpage_group_buy);
        mTvFirstpageServiceCenter = view.findViewById(R.id.tv_firstpage_service_center);
        mTvFirstpageIntegralShop = view.findViewById(R.id.tv_firstpage_integral_shop);
        mRlFirstpageVipCenter = view.findViewById(R.id.rl_firstpage_vip_center);
        mTvFirstpageMyshop = view.findViewById(R.id.tv_firstpage_myshop);
        mTvFirstpageGuide = view.findViewById(R.id.tv_firstpage_guide);
        mRlFirstpageShopDiscount = view.findViewById(R.id.rl_firstpage_shop_discount);
        mTvFirstpageWater = view.findViewById(R.id.tv_firstpage_water);
        mTvFirstpageEle = view.findViewById(R.id.tv_firstpage_ele);
        mTvFirstpageGas = view.findViewById(R.id.tv_firstpage_gas);
        mTvFirstpageShebaoQuery = view.findViewById(R.id.tv_firstpage_shebao_query);
        mTvFirstpageGongjijinQuery = view.findViewById(R.id.tv_firstpage_gongjijin_query);
        mTvFirstpageExpressQuery = view.findViewById(R.id.tv_firstpage_express_query);
        mTvFirstpageCompanyQuery = view.findViewById(R.id.tv_firstpage_company_query);
    }

    @Override
    public void initData() {
        mFirstpagePersenter = new FirstpagePersenter(getActivity(), this);
        mSystemPropPersenter = new SystemPropPersenter(getActivity(), this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(getActivity(), this);
        mBundle = new Bundle();

        //获取保存的版本号
        String version = (String) SPUtil.get(getActivity(), "version", "");
        changelog = (String) SPUtil.get(getActivity(), "changelog", "");
        versionShort = (String) SPUtil.get(getActivity(), "versionShort", "");
        install_url = (String) SPUtil.get(getActivity(), "install_url", "");
        if (!StringUtils.isEmpty(version) && AppUtils.getVersionCode(getActivity()) < Integer.parseInt(version)) {
            permissionCheck();
        }

    }

    @Override
    public void setListener() {
        mImgFirstpageNews.setOnClickListener(this);
        mTvFirstpageScan.setOnClickListener(this);
        mTvFirstpageGather.setOnClickListener(this);
        mTvFirstpageCreditcard.setOnClickListener(this);
        mTvFirstpageShop.setOnClickListener(this);
        mTvFirstpageRecommendCard.setOnClickListener(this);
        mTvFirstpageGroupBuy.setOnClickListener(this);
        mTvFirstpageServiceCenter.setOnClickListener(this);
        mTvFirstpageIntegralShop.setOnClickListener(this);
        mRlFirstpageVipCenter.setOnClickListener(this);
        mTvFirstpageMyshop.setOnClickListener(this);
        mTvFirstpageGuide.setOnClickListener(this);
        mRlFirstpageShopDiscount.setOnClickListener(this);
        mTvFirstpageWater.setOnClickListener(this);
        mTvFirstpageEle.setOnClickListener(this);
        mTvFirstpageGas.setOnClickListener(this);
        mTvFirstpageShebaoQuery.setOnClickListener(this);
        mTvFirstpageGongjijinQuery.setOnClickListener(this);
        mTvFirstpageExpressQuery.setOnClickListener(this);
        mTvFirstpageCompanyQuery.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (!new RealnameStates(getActivity()).isRealname()) {
            return;
        }
        switch (v.getId()) {
            case R.id.img_firstpage_news:
                startActivity(NewsActivity.class);
                break;
            case R.id.tv_firstpage_scan:
                makeCamerMethod();
                break;
            case R.id.tv_firstpage_gather:
                isclickGather = true;
                canReceived = (String) SPUtil.get(getActivity(), "canReceived", "");
                if (StringUtils.isEmpty(canReceived)) {
                    mMerchantinfoPersenter.merchantInfo();
                    return;
                }
                if ("t".equals(canReceived)) {
                    startActivity(GatherActivity.class);
                } else if ("f".equals(canReceived)) {
                    ToastUtils.showToast("请先开通商户权限");
                    startActivity(OpenMerchantActivity.class);
                }
                break;
            case R.id.tv_firstpage_creditcard:
                startActivity(CreditCardListActivity.class);
                break;
            case R.id.tv_firstpage_shop:
                isclickGather = false;
                canReceived = (String) SPUtil.get(getActivity(), "canReceived", "");
                if (StringUtils.isEmpty(canReceived)) {
                    mMerchantinfoPersenter.merchantInfo();
                    return;
                }
                if ("t".equals(canReceived)) {
                    startActivity(MyShopActivty.class);
                } else if ("f".equals(canReceived)) {
                    startActivity(OpenMerchantActivity.class);
                }
                break;
            case R.id.tv_firstpage_recommend_card:

                break;
            case R.id.tv_firstpage_group_buy:

                break;
            case R.id.tv_firstpage_service_center:
                mSystemPropPersenter.systemProp("kefu");
                break;
            case R.id.tv_firstpage_integral_shop:

                break;
            case R.id.rl_firstpage_vip_center:

                int currentLevel = (int) SPUtil.get(getActivity(), "vipLevel", 0);
                switch (currentLevel) {
                    case 0:
                        startActivity(VipCenterActivity.class);
                        break;
                    case 1:
                        startActivity(GatherRateActivity.class);
                }

                break;
            case R.id.tv_firstpage_myshop:
                isclickGather = false;
                canReceived = (String) SPUtil.get(getActivity(), "canReceived", "");
                if (StringUtils.isEmpty(canReceived)) {
                    mMerchantinfoPersenter.merchantInfo();
                    return;
                }
                if ("t".equals(canReceived)) {
                    startActivity(MyShopActivty.class);
                } else if ("f".equals(canReceived)) {
                    startActivity(OpenMerchantActivity.class);
                }

                break;
            case R.id.tv_firstpage_guide:
                mSystemPropPersenter.systemProp("xinshou");
                break;
            case R.id.rl_firstpage_shop_discount:

                break;
            case R.id.tv_firstpage_water:
//                mBundle.putString("url", CommonSet.WATER);
                mBundle.putString("url", "https://name.znyoo.cn/oss-transaction/unpay/toOpen/bdfe8293-988e-4ec2-9408-7147b00cb9f8");
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_ele:
                mBundle.putString("url", CommonSet.ELE);
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_gas:
                mBundle.putString("url", CommonSet.GAS);
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_shebao_query:
                mBundle.putString("url", CommonSet.SHEBAO);
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_gongjijin_query:
                mBundle.putString("url", CommonSet.GONGJIJIN);
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_express_query:
                mBundle.putString("url", CommonSet.EXPRESS);
                startActivity(LoadWebActivity.class, mBundle);
                break;
            case R.id.tv_firstpage_company_query:
                mBundle.putString("url", CommonSet.COMPANY);
                startActivity(LoadWebActivity.class, mBundle);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    //权限
    private void permissionCheck() {
        // 检查是否有相应的权限
        boolean isAllGranted = PermissionSetDialogUtils.checkPermissionAllGranted(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这权限全都拥有, 则直接执行更新
        if (isAllGranted) {
            showUpdateDialog();
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSIONS_READ_WRITE
            );
        }

    }


    //更新弹窗
    private void showUpdateDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("版本更新")
                .setMessage("当前版本：" + getVersionName(getActivity()) + "\n最新版本：" + versionShort + "\n更新内容：" + changelog)
                .setCancelable(false)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        mProgressDialog = new ProgressDialog(getActivity());
                        mProgressDialog.setMessage("下载中...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.setMax(100);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        DownloadFile downloadFile = new DownloadFile();
                        downloadFile.execute(install_url);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 通常，AsyncTask子类在activity类中进行声明
    // 这样，你可以很容易在这里修改UI线程
    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            try {
                URL url = new URL(sUrl[0]);
                //URLConnection connection = url.openConnection();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                connection.connect();
                //这将是有用的，这样你可以显示一个典型的0-100%的进度条
                int fileLength = connection.getContentLength();
                // 下载文件
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/jinyuntong.apk");

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            // 核心是下面几句代码
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //安卓6.0  provider
            if (Build.VERSION.SDK_INT >= 24) {
                //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.sl_group.jinyuntong_oem.fileprovider", new File("/sdcard/jinyuntong.apk"));
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(new File("/sdcard/jinyuntong.apk")), "application/vnd.android.package-archive");
            }
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            //刷新进度条
            mProgressDialog.setProgress(progress[0]);
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
                showUpdateDialog();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(getActivity());
            }
        } else if (requestCode == PERMISSIONS_READ_WRITE_CAMERA) {

            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionSetDialogUtils.showSetPermission(getActivity());
            }
        }
    }

    //打开相机
    private void makeCamerMethod() {

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
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
            return;
        }
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                },
                PERMISSIONS_READ_WRITE_CAMERA
        );

    }


    //startActivityForResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                qrCodeContent = data.getStringExtra(Constant.CODED_CONTENT);
                if (StringUtils.isEmpty(qrCodeContent)) {
                    ToastUtils.showToast("扫码失败");
                    return;
                }
                LogUtils.i("扫描：" + qrCodeContent);
                mFirstpagePersenter.analyzeQrcode(qrCodeContent);
            }
        }

    }

    @Override
    public void getQrcodeContent(double srcAmt, String shortName, String receivedMid) {
        Bundle bundle = new Bundle();
        bundle.putDouble("money", srcAmt);
        bundle.putString("merchant", shortName);
        bundle.putString("receivedMid", receivedMid);
        bundle.putString("qrCodeContent", qrCodeContent);
        startActivity(ScanQrcodeInputMoneyActivity.class, bundle);
    }

    @Override
    public void getKeFuURL(String kefu) {
        Bundle bundle = new Bundle();
        bundle.putString("url", kefu);
        startActivity(LoadWebActivity.class, bundle);
    }

    @Override
    public void getXieYiURL(String xieyi) {
        Bundle bundle = new Bundle();
        bundle.putString("url", xieyi);
        startActivity(LoadWebActivity.class, bundle);
    }

    @Override
    public void getXinShouURL(String xinshou) {
        Bundle bundle = new Bundle();
        bundle.putString("url", xinshou);
        startActivity(LoadWebActivity.class, bundle);
    }

    @Override
    public void getMerchantInfo(MerchantInfoBean.DataBean dataBean) {
        if ("t".equals(dataBean.getCanReceived())) {
            if (isclickGather) {
                startActivity(GatherActivity.class);
            } else {
                startActivity(MyShopActivty.class);
            }

        } else if ("f".equals(dataBean.getCanReceived())) {
            startActivity(OpenMerchantActivity.class);
        }
    }
}
