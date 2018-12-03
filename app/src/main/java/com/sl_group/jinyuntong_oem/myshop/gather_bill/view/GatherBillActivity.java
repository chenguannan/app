package com.sl_group.jinyuntong_oem.myshop.gather_bill.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.GatherBillAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.GatherBillBean;
import com.sl_group.jinyuntong_oem.myshop.gather_bill.persenter.GatherBillPersenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：收款账单
 */
public class GatherBillActivity extends BaseActivity implements GatherBillView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvGatherBillStartDate;
    private TextView mTvGatherBillEndDate;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewGatherBill;

    private GatherBillPersenter mGatherBillPersenter;

    private List<GatherBillBean.DataBean.ResultListBean> mListBeans;
    private GatherBillAdapter mGatherBillAdapter;
    private int page = 0;

    @Override
    public int bindLayout() {
        return R.layout.activity_gather_bill;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvGatherBillStartDate = findViewById(R.id.tv_gather_bill_start_date);
        mTvGatherBillEndDate = findViewById(R.id.tv_gather_bill_end_date);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewGatherBill = findViewById(R.id.recycleView_gather_bill);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("收款账单");

        mGatherBillPersenter = new GatherBillPersenter(this, this);

//        long dateMillis = System.currentTimeMillis();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        String currentDate = simpleDateFormat.format(dateMillis);
//        mTvGatherBillStartDate.setText(currentDate);
//        mTvGatherBillEndDate.setText(currentDate);


        mListBeans = new ArrayList<>();
        mGatherBillAdapter = new GatherBillAdapter(mListBeans, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewGatherBill.setAdapter(mGatherBillAdapter);
        mRecycleViewGatherBill.setLayoutManager(linearLayoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 0;
                mListBeans.clear();
                mGatherBillPersenter.getGatherBill(false, page, "10", mTvGatherBillStartDate.getText().toString().trim(), mTvGatherBillEndDate.getText().toString().trim());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mGatherBillPersenter.getGatherBill(false, page, "10", mTvGatherBillStartDate.getText().toString().trim(), mTvGatherBillEndDate.getText().toString().trim());

            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvGatherBillStartDate.setOnClickListener(this);
        mTvGatherBillEndDate.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_gather_bill_start_date:
                //开始时间
                selectDate("选择开始时间");
                break;
            case R.id.tv_gather_bill_end_date:
                //结束时间
                selectDate("选择结束时间");
                break;

        }
    }

    @Override
    public void doBusiness(Context mContext) {
        page = 0;
        mListBeans.clear();
        mGatherBillPersenter.getGatherBill(true, page, "10", mTvGatherBillStartDate.getText().toString().trim(), mTvGatherBillEndDate.getText().toString().trim());
    }

    /**
     * 选择开始结束时间
     *
     * @param title 标题
     */
    private void selectDate(final String title) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();

        // 隐藏软键盘
        if (isOpen) {
            imm.hideSoftInputFromWindow(mTvGatherBillStartDate.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(mTvGatherBillEndDate.getWindowToken(), 0);
        }

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (title.contains("开始")) {
                    //选中事件回调
                    mTvGatherBillStartDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                } else if (title.contains("结束")) {
                    //选中事件回调
                    mTvGatherBillEndDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                }
                page = 0;
                mListBeans.clear();
                mGatherBillPersenter.getGatherBill(true, 0, "10", mTvGatherBillStartDate.getText().toString().trim(), mTvGatherBillEndDate.getText().toString().trim());
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText(getString(R.string.cancel))//取消按钮文字
                .setSubmitText(getString(R.string.sure))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(ContextCompat.getColor(this, R.color.mainColor))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(this, R.color.grayColor_6))//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @Override
    public void getGatherBillList(List<GatherBillBean.DataBean.ResultListBean> resultList) {
        mListBeans.addAll(resultList);
        mGatherBillAdapter.notifyDataSetChanged();
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}
