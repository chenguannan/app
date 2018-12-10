package com.sl_group.jinyuntong_oem.scan_input_money.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultListener;
import com.cfca.mobile.log.CodeException;
import com.cfca.mobile.sipedit.SipEditText;
import com.cfca.mobile.sipedit.grid.GridSipEditStateType;
import com.cfca.mobile.sipedit.grid.GridSipEditText;
import com.cfca.mobile.sipedit.grid.GridSipEditTextDelegator;
import com.cfca.mobile.sipkeyboard.SIPKeyboardType;
import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CommonSet;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.AutoRealnameBean;
import com.sl_group.jinyuntong_oem.bean.CreditCardBean;
import com.sl_group.jinyuntong_oem.bean.MerchantInfoBean;
import com.sl_group.jinyuntong_oem.creditcard.persenter.CreditCardListPersenter;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListActivity;
import com.sl_group.jinyuntong_oem.creditcard.view.CreditCardListView;
import com.sl_group.jinyuntong_oem.merchant_info.persenter.MerchantinfoPersenter;
import com.sl_group.jinyuntong_oem.merchant_info.view.MerchantinfoView;
import com.sl_group.jinyuntong_oem.scan_input_money.persenter.ScanQrcodeInputMoneyPersenter;
import com.sl_group.jinyuntong_oem.sms.view.SMSActivity;
import com.sl_group.jinyuntong_oem.utils.DisplayUtils;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.PopupWindowUtils;
import com.sl_group.jinyuntong_oem.utils.SPUtil;
import com.sl_group.jinyuntong_oem.utils.StringUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;
import com.sl_group.jinyuntong_oem.web.X5WebViewActivity;
import com.sl_group.jinyuntong_oem.youdun_uuid.persenter.YouDunUUIDPersenter;
import com.sl_group.jinyuntong_oem.youdun_uuid.view.YouDunUUIDView;

import java.util.Date;
import java.util.List;

import static com.sl_group.jinyuntong_oem.CommonSet.SERVICE_RANDOM;

/**
 * Created by 马天 on 2018/11/21.
 * description：扫码付款
 */
public class ScanQrcodeInputMoneyActivity extends BaseActivity implements CreditCardListView, ScanQrcodeInputMoneyView, MerchantinfoView, YouDunUUIDView, GridSipEditTextDelegator {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvScanQrcodeInputMoneyMerchant;
    private EditText mEtScanQrcodeInputMoney;
    private Button mBtnMakesurePay;

    private CreditCardListPersenter mCreditCardListPersenter;

    private String money;
    private String tradePassword;
    private ScanQrcodeInputMoneyPersenter mScanQrcodeInputMoneyPersenter;
    private MerchantinfoPersenter mMerchantinfoPersenter;
    private YouDunUUIDPersenter mYouDunUUIDPersenter;
    private String accountNumber;
    private String bankName;
    private String receivedMid;
    private String qrcodeContent;
    private String payPassword;

    private GridSipEditText gridSip_fisrt;
    private GridSipEditText gridSip_second;
    private String title = "请设置支付密码";
    private PopupWindow popupWindow;

    @Override
    protected void onStart() {
        super.onStart();
        tradePassword = (String) SPUtil.get(this, "tradePassword", "");
        boolean isPaySelectCard = (boolean) SPUtil.get(ScanQrcodeInputMoneyActivity.this, "isPaySelectCard", false);
        if (isPaySelectCard) {
            accountNumber = (String) SPUtil.get(ScanQrcodeInputMoneyActivity.this, "payAccountNumber", "");
            bankName = (String) SPUtil.get(ScanQrcodeInputMoneyActivity.this, "payBankName", "");
            popScanQrcodePay();
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scan_qrcode_input_money;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvScanQrcodeInputMoneyMerchant = findViewById(R.id.tv_scan_qrcode_input_money_merchant);
        mEtScanQrcodeInputMoney = findViewById(R.id.et_scan_qrcode_input_money);
        mBtnMakesurePay = findViewById(R.id.btn_makesure_pay);


    }

    @SuppressLint("DefaultLocale")
    @Override
    public void initData() {
        mTvActionbarTitle.setText("付款");

        mCreditCardListPersenter = new CreditCardListPersenter(this, this);
        mScanQrcodeInputMoneyPersenter = new ScanQrcodeInputMoneyPersenter(this, this);
        mMerchantinfoPersenter = new MerchantinfoPersenter(this, this);
        mYouDunUUIDPersenter = new YouDunUUIDPersenter(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            double money = bundle.getDouble("money");
            String merchant = bundle.getString("merchant");
            receivedMid = bundle.getString("receivedMid");
            qrcodeContent = bundle.getString("qrCodeContent");
            mTvScanQrcodeInputMoneyMerchant.setText(merchant);
            if (money > 0) {
                mEtScanQrcodeInputMoney.setText(String.format("%.2f", money));
                mEtScanQrcodeInputMoney.setEnabled(false);
            }
        } else {
            ToastUtils.showToast("数据加载失败");
        }

    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mBtnMakesurePay.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.btn_makesure_pay:
                money = mEtScanQrcodeInputMoney.getText().toString().trim();
                if (StringUtils.isEmpty(money)) {
                    ToastUtils.showToast("请输入收款金额");
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                // 获取软键盘的显示状态
                boolean isOpen = imm.isActive();

                // 隐藏软键盘
                if (isOpen) {
                    imm.hideSoftInputFromWindow(mEtScanQrcodeInputMoney.getWindowToken(), 0);
                }

                mCreditCardListPersenter.creditCardList(true);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void getCreditCardList(List<CreditCardBean.DataBean> list) {

        if (list.size() <= 0) {
            ToastUtils.showToast("暂未绑定交易卡");
            return;
        }
        accountNumber = list.get(0).getAccountNumber();
        bankName = list.get(0).getBankName();
        popScanQrcodePay();
    }

    private void popScanQrcodePay() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_scan_qrcode_pay, null);

        ImageView imgPopScanQrcodePayClose = view.findViewById(R.id.img_pop_scan_qrcode_pay_close);
        TextView tvPopScanQrcodePayPrice = view.findViewById(R.id.tv_pop_scan_qrcode_pay_price);
        TextView tvPopScanQrcodePaySelectcard = view.findViewById(R.id.tv_pop_scan_qrcode_pay_selectcard);
        Button btnPopScanQrcodePayImmediatelyPay = view.findViewById(R.id.btn_pop_scan_qrcode_pay_immediately_pay);

        tvPopScanQrcodePayPrice.setText(String.format("%.2f", Double.parseDouble(money)));
        tvPopScanQrcodePaySelectcard.setText(bankName + "(" + accountNumber.substring(accountNumber.length() - 4, accountNumber.length()) + ")");

        final PopupWindow popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationBottom);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        imgPopScanQrcodePayClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        tvPopScanQrcodePaySelectcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isPay", true);
                startActivity(CreditCardListActivity.class, bundle);
            }
        });
        btnPopScanQrcodePayImmediatelyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                if (StringUtils.isEmpty(tradePassword)) {
                    title = "请设置支付密码";
                    mMerchantinfoPersenter.merchantInfo();
                } else {
                    title = "请输入支付密码";
                    showPasswordPop(title);
                }
            }
        });
    }

    private void showPasswordPop(final String title) {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_password, null);

        TextView tvPopPasswordTitle = view.findViewById(R.id.tv_pop_password_title);
        TextView tvPopPasswordForget = view.findViewById(R.id.tv_pop_password_forget);
        if (title.contains("设置")) {
            tvPopPasswordForget.setVisibility(View.GONE);
        }else {
            tvPopPasswordForget.setVisibility(View.VISIBLE);
        }

        gridSip_fisrt = view.findViewById(R.id.gridSip_first);
        gridSip_second = view.findViewById(R.id.gridSip_second);
        gridSip_fisrt.setGridColor(ContextCompat.getColor(this, R.color.blackColor));
        gridSip_fisrt.setSipKeyBoardType(SIPKeyboardType.NUMBER_KEYBOARD);
        gridSip_fisrt.setStorkeWidth(1);
        gridSip_fisrt.setCirdNumber(6);
        gridSip_fisrt.setNodeColor(Color.BLACK);
        gridSip_fisrt.setEncryptState(true);
        gridSip_fisrt.setServerRandom(SERVICE_RANDOM);
        gridSip_fisrt.setCipherType(SipEditText.Algorithm_RSA1024);
        gridSip_fisrt.setOutputValueType(2);
        gridSip_fisrt.showSecurityKeyBoard();

        gridSip_second.setGridColor(ContextCompat.getColor(this, R.color.blackColor));
        gridSip_second.setSipKeyBoardType(SIPKeyboardType.NUMBER_KEYBOARD);
        gridSip_second.setStorkeWidth(1);
        gridSip_second.setCirdNumber(6);
        gridSip_second.setNodeColor(Color.BLACK);
        gridSip_second.setEncryptState(true);
        gridSip_second.setServerRandom(SERVICE_RANDOM);
        gridSip_second.setCipherType(SipEditText.Algorithm_RSA1024);
        gridSip_second.setOutputValueType(2);

        gridSip_fisrt.setGridSipEditTextDelegator(this);
        gridSip_second.setGridSipEditTextDelegator(this);


        tvPopPasswordTitle.setText(title);

        tvPopPasswordForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("action", "forgetPayPassword");
                startActivity(SMSActivity.class, bundle);
            }
        });
        popupWindow = PopupWindowUtils.getPop(this, view, DisplayUtils.getScreenWidth(this), ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimationCenter);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }

    //验证是否为同一人交易
    private void peopleVerfic(String payPassword) {
        if (receivedMid.equals(SPUtil.get(ScanQrcodeInputMoneyActivity.this, "mid", ""))) {
            mScanQrcodeInputMoneyPersenter.planceOrder("", qrcodeContent, accountNumber, payPassword, money);
        } else {
            mYouDunUUIDPersenter.getYouDunUUID();
            //获取有盾UUID
        }
    }


    @Override
    public void setPasswordSuccess(String payPassword) {
        peopleVerfic(payPassword);
    }

    @Override
    public void placeOrderSuccess(String openUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url", openUrl);
        startActivity(X5WebViewActivity.class, bundle);
        finish();
    }

    @Override
    public void merchantInfoSuccess(MerchantInfoBean.DataBean dataBean) {
        this.tradePassword = dataBean.getTradePassword();
        if (StringUtils.isEmpty(dataBean.getTradePassword())) {
            SPUtil.put(this, "tradePassword", "");
            title = "请设置支付密码";
            showPasswordPop(title);
        } else {
            SPUtil.put(this, "tradePassword", dataBean.getTradePassword());
            title = "请输入支付密码";
            showPasswordPop(title);
        }
    }


    @Override
    public void getYouDunUUIDSuccess(final String uuid) {
        String id = "jyt_pay_" + new Date().getTime();
        AuthBuilder mAuthBuilder = new AuthBuilder(id, CommonSet.authKey, CommonSet.urlNotify + SPUtil.get(this, "mid", "") + "/" + uuid, new OnResultListener() {
            @Override
            public void onResult(String s) {
                LogUtils.i("交易活体认证结果：" + s);
                AutoRealnameBean autoRealnameBean = new Gson().fromJson(s, AutoRealnameBean.class);
                if (!"000000".equals(autoRealnameBean.getRet_code())) {
                    ToastUtils.showToast(autoRealnameBean.getRet_msg());
                    return;
                }
                mScanQrcodeInputMoneyPersenter.planceOrder(uuid, qrcodeContent, accountNumber, payPassword, money);
            }
        });
        //下文调用方法做为范例，请以对接文档中的调用方法为准
        mAuthBuilder.faceAuth(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtil.remove(this, "isPaySelectCard");
        SPUtil.remove(this, "payAccountNumber");
        SPUtil.remove(this, "payBankName");
    }

    @Override
    public void beforeKeyboardShow(GridSipEditText gridSipEditText, int i) {

    }

    @Override
    public void afterKeyboardHidden(GridSipEditText gridSipEditText, int i) {

    }

    @Override
    public void afterClickDown(GridSipEditText gridSipEditText) {

    }

    @Override
    public void onInputComplete(GridSipEditText gridSipEditText) {
        try {
            payPassword = gridSipEditText.getEncryptData().getEncryptRandomNum() + "#" + gridSipEditText.getEncryptData().getEncryptInput();

            if (title.contains("设置")) {
                if (gridSipEditText == gridSip_fisrt) {
                    gridSip_fisrt.setVisibility(View.GONE);
                    gridSip_second.setVisibility(View.VISIBLE);
                    gridSip_second.showSecurityKeyBoard();
                } else {
                    if (gridSip_second.inputEqualsWith(gridSip_fisrt)) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        mScanQrcodeInputMoneyPersenter.setPayPassword(payPassword);
                    } else {
                        Toast.makeText(getApplicationContext(), "输入不同请重新输入", Toast.LENGTH_SHORT).show();
                        gridSip_fisrt.clear();
                        gridSip_second.clear();
                        gridSip_fisrt.setVisibility(View.VISIBLE);
                        gridSip_second.setVisibility(View.GONE);

                    }

                }
            } else {
                peopleVerfic(payPassword);
            }
        } catch (CodeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTextSizeChange(GridSipEditStateType gridSipEditStateType) {

    }
}
