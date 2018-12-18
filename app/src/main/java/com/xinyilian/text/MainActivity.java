package com.xinyilian.text;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.firstpage.FirstpageFragment;
import com.xinyilian.text.mine.MineFragment;
import com.xinyilian.text.share.ShareFragment;
import com.xinyilian.text.treasure.view.TreasureFragment;
import com.xinyilian.text.utils.LogUtils;
import com.xinyilian.text.utils.SPUtil;
import com.xinyilian.text.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRgMainBottom;
    private RadioButton mRbMainFirstpage;
    private RadioButton mRbMainTreasure;
    private RadioButton mRbMainShare;
    private RadioButton mRbMainMine;
    private long exitTime = 0;

    private Fragment mFirstpageFragment, mTreasureFragment, mShareFragment, mMineFragment;



    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mRgMainBottom = findViewById(R.id.rg_main_bottom);
        mRbMainFirstpage = findViewById(R.id.rb_main_firstpage);
        mRbMainTreasure = findViewById(R.id.rb_main_treasure);
        mRbMainShare = findViewById(R.id.rb_main_share);
        mRbMainMine = findViewById(R.id.rb_main_mine);
    }

    @Override
    public void initData() {
        registerMessageReceiver();
        init();


        mRgMainBottom.setOnCheckedChangeListener(this);

        Set<String> stringSet = new HashSet<>();
        stringSet.add("merchant");
        JPushInterface.setTags(this, 1, stringSet);
        JPushInterface.setAlias(this, 1, "stage" + SPUtil.get(this, "mid", ""));
        LogUtils.i("极光：" + "stage" + SPUtil.get(this, "mid", ""));
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness(Context mContext) {
        selectFragment(0);

    }

    /**
     * 选择Fragment
     * param
     */
    private void selectFragment(int i) {
        //创建事务
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //隐藏
        hideFragments(ft);
        resetDrawable();
        switch (i) {
            case 0:
                mRbMainFirstpage.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
                if (mFirstpageFragment == null) {
                    mFirstpageFragment = new FirstpageFragment();
                    ft.add(R.id.fl_main, mFirstpageFragment);
                } else {
                    ft.show(mFirstpageFragment);
                }
                break;

            case 1:
                mRbMainTreasure.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
                if (mTreasureFragment == null) {
                    mTreasureFragment = new TreasureFragment();
                    ft.add(R.id.fl_main, mTreasureFragment);
                } else {
                    ft.show(mTreasureFragment);
                }

                break;
            case 2:
                mRbMainShare.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
                if (mShareFragment == null) {
                    mShareFragment = new ShareFragment();
                    ft.add(R.id.fl_main, mShareFragment);
                } else {
                    ft.show(mShareFragment);
                }

                break;
            case 3:
//                if (!new RealnameStates(this).isRealname()) {
//                    return;
//                }
                mRbMainMine.setTextColor(ContextCompat.getColor(this, R.color.mainColor));
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    ft.add(R.id.fl_main, mMineFragment);
                } else {
                    ft.show(mMineFragment);
                }

                break;
        }
        //事务提交
        ft.commit();
    }

    private void resetDrawable() {
        mRbMainFirstpage.setTextColor(ContextCompat.getColor(this, R.color.purpleColor));
        mRbMainTreasure.setTextColor(ContextCompat.getColor(this, R.color.purpleColor));
        mRbMainShare.setTextColor(ContextCompat.getColor(this, R.color.purpleColor));
        mRbMainMine.setTextColor(ContextCompat.getColor(this, R.color.purpleColor));
    }

    //隐藏所有Fragment
    private void hideFragments(FragmentTransaction ft) {
        /*隐藏所有Fragment视图*/
        if (mFirstpageFragment != null) {
            ft.hide(mFirstpageFragment);
        }
        if (mTreasureFragment != null) {
            ft.hide(mTreasureFragment);
        }
        if (mShareFragment != null) {
            ft.hide(mShareFragment);
        }
        if (mMineFragment != null) {
            ft.hide(mMineFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (mFirstpageFragment == null && fragment instanceof FirstpageFragment) {
            mFirstpageFragment = fragment;
        }

        if (mTreasureFragment == null && fragment instanceof TreasureFragment) {
            mTreasureFragment = fragment;
        }

        if (mShareFragment == null && fragment instanceof ShareFragment) {
            mShareFragment = fragment;
        }
        if (mMineFragment == null && fragment instanceof MineFragment) {
            mMineFragment = fragment;
        }


    }



    /**
     * 再按一次退出程序
     * param 时间类型，事件
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (mRbMainFirstpage.getId() == checkedId) {
            selectFragment(0);
        } else if (mRbMainTreasure.getId() == checkedId) {
            selectFragment(1);
        } else if (mRbMainShare.getId() == checkedId) {
            selectFragment(2);
        } else if (mRbMainMine.getId() == checkedId) {
            selectFragment(3);
        }

    }


    public static boolean isForeground = false;

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : ").append(messge).append("\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : ").append(extras).append("\n");
                }
                LogUtils.i("消息推送：" + showMsg.toString());
            }
        }
    }

}
