package com.sl_group.jinyuntong_oem.my_team.view;

import android.annotation.SuppressLint;
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
import com.sl_group.jinyuntong_oem.adapter.MyTeamAdapter;
import com.sl_group.jinyuntong_oem.base.BaseActivity;
import com.sl_group.jinyuntong_oem.bean.MyTeamBean;
import com.sl_group.jinyuntong_oem.my_team.persenter.MyTeamPersenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 马天 on 2018/11/24.
 * description：我的团队
 */
public class MyTeamActivity extends BaseActivity implements MyTeamView {
    private ImageView mImgActionbarBack;
    private TextView mTvActionbarTitle;
    private TextView mTvMyTeamNumber;
    private TextView mTvMyTeamNormal;
    private TextView mTvMyTeamVip;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleViewMyTeam;

    //当前页码
    private int curPage = 0;
    //集合
    private List<MyTeamBean.DataBean.ResultListBean> mListBeans;
    //我的团队适配器
    private MyTeamAdapter mMyTeamAdapter;
    //我的团队persenter
    private MyTeamPersenter mMyTeamPersenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initView(View view) {
        mImgActionbarBack = findViewById(R.id.img_actionbar_back);
        mTvActionbarTitle = findViewById(R.id.tv_actionbar_title);
        mTvMyTeamNumber = findViewById(R.id.tv_my_team_number);
        mTvMyTeamNormal = findViewById(R.id.tv_my_team_normal);
        mTvMyTeamVip = findViewById(R.id.tv_my_team_vip);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecycleViewMyTeam = findViewById(R.id.recycleView_my_team);
    }

    @Override
    public void initData() {
        //设置标题
        mTvActionbarTitle.setText("我的团队");
        //初始化persenter
        mMyTeamPersenter = new MyTeamPersenter(this, this);
        //初始化集合
        mListBeans = new ArrayList<>();
        //初始化适配器
        mMyTeamAdapter = new MyTeamAdapter(mListBeans, this);
        //初始化recycleview垂直线性展示方式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置适配器
        mRecycleViewMyTeam.setAdapter(mMyTeamAdapter);
        mRecycleViewMyTeam.setLayoutManager(linearLayoutManager);
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                curPage = 0;
                mListBeans.clear();
                mMyTeamPersenter.myTeam("11", false, curPage, "10");
            }
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mMyTeamPersenter.myTeam("11", false, curPage, "10");
            }
        });
    }

    @Override
    public void setListener() {
        mImgActionbarBack.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.img_actionbar_back:
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        curPage = 0;
        mListBeans.clear();
        mMyTeamPersenter.myTeam("11", true, curPage, "10");
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void myTeamSuccess(MyTeamBean.DataBean data) {
        mTvMyTeamNumber.setText(String.valueOf(data.getDirectNum()));
        for (int i = 0; i < data.getLevelList().size(); i++) {
            MyTeamBean.DataBean.LevelListBean levelListBean = data.getLevelList().get(i);
            switch (levelListBean.getVipLevel()) {
                //普通商户
                case 0:
                    mTvMyTeamNormal.setText(levelListBean.getLevelName() + "：" + levelListBean.getNum());
                    break;
                //VIP商户
                case 1:
                    mTvMyTeamVip.setText(levelListBean.getLevelName() + "：" + levelListBean.getNum());
                    break;
            }
        }
        mListBeans.addAll(data.getResultList());
        mMyTeamAdapter.notifyDataSetChanged();
        if (mRefreshLayout.isEnableRefresh()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isEnableLoadMore()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}
