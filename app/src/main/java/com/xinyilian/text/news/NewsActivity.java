package com.xinyilian.text.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinyilian.text.R;
import com.xinyilian.text.adapter.NewsAdapter;
import com.xinyilian.text.base.BaseActivity;
import com.xinyilian.text.bean.MessagesBean;
import com.xinyilian.text.messages.persenter.MessagesPersenter;
import com.xinyilian.text.messages.view.MessagesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/25.
 * description：消息列表
 */
public class NewsActivity extends BaseActivity implements MessagesView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewNews;
    //信息对象集合
    private List<MessagesBean.DataBean.ResultListBean> mListBeans;
    //消息适配器
    private NewsAdapter mNewsAdapter;
    //当前页码
    private int curPage = 0;
    //信息persenter
    private MessagesPersenter mMessagesPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_news;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewNews = findViewById(R.id.recycleView_news);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("消息");
        //初始化persenter
        mMessagesPersenter = new MessagesPersenter(this,this);
        //初始化集合
        mListBeans = new ArrayList<>();
        //初始化适配器
        mNewsAdapter = new NewsAdapter(mListBeans,this);
        //初始化recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置适配器
        mRecycleViewNews.setAdapter(mNewsAdapter);
        mRecycleViewNews.setLayoutManager(linearLayoutManager);
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                curPage = 0;
                mListBeans.clear();
                mMessagesPersenter.messages("news",false, curPage, "10","");
            }
        });
        //加载
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mMessagesPersenter.messages("news",false, curPage, "10","");
            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        curPage = 0;
        mListBeans.clear();
        mMessagesPersenter.messages("news",true, curPage, "10","");
    }

    /**
      * 查询信息成功
      * @param newsListBeans 消息集合对象
      */
    @Override
    public void newsSuccess(List<MessagesBean.DataBean.ResultListBean> newsListBeans) {
        mListBeans.addAll(newsListBeans);
        mNewsAdapter.notifyDataSetChanged();
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void noticesSuccess(List<MessagesBean.DataBean.ResultListBean> noticeListBeans) {

    }
}
