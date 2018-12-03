package com.sl_group.jinyuntong_oem.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 马天 on 2018/3/16.
 * description：
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contextView = inflater.inflate(bindLayout(), null);

        initView(contextView);
        initData();
        setListener();
        doBusiness(getActivity());
        return contextView;
    }

    /**
     * [绑定布局]
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     */
    public abstract void initView(View view);
    /**
     * [初始化参数]
     */
    public abstract void initData();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    /**
     * [业务操作]
     *
     * @param mContext Context
     */
    public abstract void doBusiness(Context mContext);


    /**
     * [页面跳转]
     *
     * @param clz Class
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    Class
     * @param bundle Bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls         Class
     * @param bundle      Bundle
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

}

