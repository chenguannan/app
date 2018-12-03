package com.sl_group.jinyuntong_oem.news.view;

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
import com.sl_group.jinyuntong_oem.R;
import com.sl_group.jinyuntong_oem.adapter.NewsAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.NewsBean;
import com.sl_group.jinyuntong_oem.news.persenter.NewsPersenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class NewsActivity extends BaseActivity implements NewsView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewNews;

    private List<NewsBean.DataBean.ResultListBean> mListBeans;
    private NewsAdapter mNewsAdapter;

    private int curPage = 0;

    private NewsPersenter mNewsPersenter;

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
        mTvActionbarTitle.setText("消息");

        mNewsPersenter = new NewsPersenter(this,this);

        mListBeans = new ArrayList<>();
        mNewsAdapter = new NewsAdapter(mListBeans,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewNews.setAdapter(mNewsAdapter);
        mRecycleViewNews.setLayoutManager(linearLayoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                curPage = 0;
                mListBeans.clear();
                mNewsPersenter.news(false, curPage, "10","");
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mNewsPersenter.news(false, curPage, "10","");
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
        mNewsPersenter.news(true, curPage, "10","");
    }

    @Override
    public void getNews(List<NewsBean.DataBean.ResultListBean> resultList) {
        mListBeans.addAll(resultList);
        mNewsAdapter.notifyDataSetChanged();
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}
