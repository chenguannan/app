package com.xinyilian.text.pay_bill.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinyilian.text.R;
import com.xinyilian.text.adapter.PayBillAdapter;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.PayBillBean;
import com.xinyilian.text.pay_bill.persenter.PayBillPersenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 马天 on 2018/11/20.
 * description：付款账单
 */
public class PayBillActivity extends BaseActivity implements PayBillView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvPayBillStartDate;
    private TextView mTvPayBillEndDate;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewPayBill;
    //付款账单persenter
    private PayBillPersenter mPayBillPersenter;
    //付款账单集合
    private List<PayBillBean.DataBean.ResultListBean> mListBeans;
    //付款账单适配器
    private PayBillAdapter mPayBillAdapter;
    //当前页码
    private int curPage = 0;

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_bill;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvPayBillStartDate = findViewById(R.id.tv_pay_bill_start_date);
        mTvPayBillEndDate = findViewById(R.id.tv_pay_bill_end_date);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewPayBill = findViewById(R.id.recycleView_pay_bill);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("付款账单");
        //初始化付款账单persenter
        mPayBillPersenter = new PayBillPersenter(this, this);
        //初始化集合
        mListBeans = new ArrayList<>();
        //初始化适配器
        mPayBillAdapter = new PayBillAdapter(mListBeans, this);
        //初始化recycleview线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //绑定manager
        mRecycleViewPayBill.setLayoutManager(linearLayoutManager);
        //绑定适配器
        mRecycleViewPayBill.setAdapter(mPayBillAdapter);
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                //当前页置0，清空集合
                curPage = 0;
                mListBeans.clear();
                mPayBillPersenter.getPayBill(false, curPage, "10", mTvPayBillStartDate.getText().toString().trim(), mTvPayBillEndDate.getText().toString().trim());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //页码自增
                curPage++;
                mPayBillPersenter.getPayBill(false, curPage, "10", mTvPayBillStartDate.getText().toString().trim(), mTvPayBillEndDate.getText().toString().trim());

            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvPayBillStartDate.setOnClickListener(this);
        mTvPayBillEndDate.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_pay_bill_start_date:
                //开始时间
                selectDate("选择开始时间");
                break;
            case R.id.tv_pay_bill_end_date:
                //结束时间
                selectDate("选择结束时间");
                break;

        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //当前页码置0，清空集合
        curPage = 0;
        mListBeans.clear();
        mPayBillPersenter.getPayBill(true, curPage, "10", mTvPayBillStartDate.getText().toString().trim(), mTvPayBillEndDate.getText().toString().trim());
    }

    /**
     * 选择开始结束时间
     *
     * @param title 标题
     */
    private void selectDate(final String title) {

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (title.contains("开始")) {
                    //选中事件回调
                    mTvPayBillStartDate.setText(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(date));
                } else if (title.contains("结束")) {
                    //选中事件回调
                    mTvPayBillEndDate.setText(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(date));
                }
                curPage = 0;
                mListBeans.clear();
                mPayBillPersenter.getPayBill(true, 0, "10", mTvPayBillStartDate.getText().toString().trim(), mTvPayBillEndDate.getText().toString().trim());
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

    /**
      * 查询成功
      * @param dataBean 付款账单对象
      */
    @Override
    public void payBillSuccess(PayBillBean.DataBean dataBean) {
        //添加集合
        mListBeans.addAll(dataBean.getResultList());
        //刷新适配器
        mPayBillAdapter.notifyDataSetChanged();
        //结束刷新加载状态
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}
