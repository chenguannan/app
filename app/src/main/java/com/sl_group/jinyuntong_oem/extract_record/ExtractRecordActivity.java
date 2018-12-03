package com.sl_group.jinyuntong_oem.extract_record;

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
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.ExtractRecordAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.DealRecordBean;
import com.sl_group.jinyuntong_oem.bean.ExtractRecordBean;
import com.sl_group.jinyuntong_oem.bean.UpVipBrokerageBean;
import com.sl_group.jinyuntong_oem.brokerage.persenter.BrokeragePersenter;
import com.sl_group.jinyuntong_oem.brokerage.view.BrokerageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 马天 on 2018/11/24.
 * description：提现记录
 */
public class ExtractRecordActivity extends BaseActivity implements BrokerageView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvExtractRecordMoney;
    private TextView mTvSelectDate;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewExtractRecord;

    private int curPage = 0;

    private List<ExtractRecordBean.DataBean.ResultListBean> mListBeans;
    private ExtractRecordAdapter mExtractRecordAdapter;
    private BrokeragePersenter mBrokeragePersenter;


    @Override
    public int bindLayout() {
        return R.layout.activity_extract_record;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvExtractRecordMoney = findViewById(R.id.tv_extract_record_money);
        mTvSelectDate = findViewById(R.id.tv_select_date);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewExtractRecord = findViewById(R.id.recycleView_extract_record);

    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("累计提现");

        mBrokeragePersenter = new BrokeragePersenter(this,this);

        mListBeans = new ArrayList<>();
        mExtractRecordAdapter = new ExtractRecordAdapter(mListBeans,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewExtractRecord.setAdapter(mExtractRecordAdapter);
        mRecycleViewExtractRecord.setLayoutManager(linearLayoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                curPage = 0;
                mListBeans.clear();
                mBrokeragePersenter.brokerage("14",false, curPage, "10", mTvSelectDate.getText().toString().trim());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mBrokeragePersenter.brokerage("14",false, curPage, "10", mTvSelectDate.getText().toString().trim());
            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mTvSelectDate.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.tv_select_date:
                selectDate();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        mBrokeragePersenter.brokerage("14",true, curPage, "10", mTvSelectDate.getText().toString().trim());
    }


    /**
     * 选择查询时间
     */
    private void selectDate() {

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中事件回调
                mTvSelectDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                curPage = 0;
                mListBeans.clear();
                mBrokeragePersenter.brokerage("14",true, curPage, "10", mTvSelectDate.getText().toString().trim());
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText(getString(R.string.cancel))//取消按钮文字
                .setSubmitText(getString(R.string.sure))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("请选择查询日期")//标题文字
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
    public void extractRecord(ExtractRecordBean.DataBean data) {
        mTvExtractRecordMoney.setText(String.format("%.2f",data.getEncAmt()));
        mListBeans.addAll(data.getResultList());
        mExtractRecordAdapter.notifyDataSetChanged();
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void dealRecord(DealRecordBean.DataBean data) {

    }

    @Override
    public void upVipBrokerage(UpVipBrokerageBean.DataBean data) {

    }
}
