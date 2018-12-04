package com.sl_group.jinyuntong_oem.creditcard.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.InventoryAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.CreditCardBean;
import com.sl_group.jinyuntong_oem.bindcard.view.BindCreditCardActivity;
import com.sl_group.jinyuntong_oem.creditcard.persenter.CreditCardListPersenter;
import com.sl_group.jinyuntong_oem.unbind_creditcard.percenter.UnBindCreditCardPercenter;
import com.sl_group.jinyuntong_oem.unbind_creditcard.view.UnBindCreditCardView;
import com.sl_group.jinyuntong_oem.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/15.
 * description：信用卡列表UI
 */
public class CreditCardListActivity extends BaseActivity implements CreditCardListView ,UnBindCreditCardView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private LinearLayout mLlCreditCardAdd;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewCreditCard;
    //信用卡列表适配器
//    private CreditCardAdapter mCreditCardAdapter;
    private InventoryAdapter mCreditCardAdapter;
    //信用卡列表集合
    private List<CreditCardBean.DataBean> mBeanList;
    //信用卡列表
    private CreditCardListPersenter mCreditCardListPersenter;
    private UnBindCreditCardPercenter mUnBindCreditCardPercenter;
    //是否是支付的时候选卡，要传卡号
    private boolean isPay =false;

    @Override
    protected void onStart() {
        super.onStart();
        //信用卡列表
        mCreditCardListPersenter = new CreditCardListPersenter(this,this);
        //查询信用卡列表
        mCreditCardListPersenter.creditCardList(true);
        //解绑信用卡
        mUnBindCreditCardPercenter = new UnBindCreditCardPercenter(this,this);

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_creditcard;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mLlCreditCardAdd = findViewById(R.id.ll_creditcard_add);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewCreditCard = findViewById(R.id.recycleView_creditcard);
    }

    @Override
    public void initData() {
        mTvActionbarTitle.setText("卡包");

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            //是否是支付选卡
            isPay = bundle.getBoolean("isPay");
        }
        //初始化数据
        mBeanList = new ArrayList<>();
        //初始化适配器
        mCreditCardAdapter = new InventoryAdapter(this,mBeanList);
        //recycleview 初始化 显示方向，类型等，线性显示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //绑定适配器
        mRecycleViewCreditCard.setAdapter(mCreditCardAdapter);
        mRecycleViewCreditCard.setLayoutManager(linearLayoutManager);
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                //刷新查询信用卡列表
                mCreditCardListPersenter.creditCardList(false);
            }
        });
        //禁止加载更多
        mRefreshLayout.setEnableLoadMore(false);
        //适配器点击回调
        mCreditCardAdapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果是支付选卡的话，记录下状态，卡号，银行名称
                if (isPay){
                    SPUtil.put(CreditCardListActivity.this,"isPaySelectCard",true);
                    SPUtil.put(CreditCardListActivity.this,"payAccountNumber",mBeanList.get(position).getAccountNumber());
                    SPUtil.put(CreditCardListActivity.this,"payBankName",mBeanList.get(position).getBankName());
                    finish();
                }
            }
        });
        //侧滑删除解绑信用卡
        mCreditCardAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mUnBindCreditCardPercenter.unBindCreditCard(String.valueOf(mBeanList.get(position).getFastpayBankAccountInfoId()));
            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
        mLlCreditCardAdd.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
            case R.id.ll_creditcard_add:
                //添加银行卡
                startActivity(BindCreditCardActivity.class);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    /**
      * 获取信用卡列表
      * @param list 信用卡对象集合
      */
    @Override
    public void getCreditCardList(List<CreditCardBean.DataBean> list) {
        mBeanList.clear();
        mBeanList.addAll(list);
        mCreditCardAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
    }

    /**
      * 解绑信用卡
      */
    @Override
    public void unBindCreditCardSuccess() {
//        mCreditCardListPersenter.creditCardList(true);
        finish();
    }
}
