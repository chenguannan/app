package com.sl_group.jinyuntong_oem.my_team;

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
import com.sl_group.jinyuntong_oem.my_team.view.MyTeamView;

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

    private int curPage = 0;

    private List<MyTeamBean.DataBean.ResultListBean> mListBeans;
    private MyTeamAdapter mMyTeamAdapter;
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
        mTvActionbarTitle.setText("我的团队");

        mMyTeamPersenter = new MyTeamPersenter(this,this);

        mListBeans = new ArrayList<>();
        mMyTeamAdapter = new MyTeamAdapter(mListBeans,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewMyTeam.setAdapter(mMyTeamAdapter);
        mRecycleViewMyTeam.setLayoutManager(linearLayoutManager);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                curPage = 0;
                mListBeans.clear();
                mMyTeamPersenter.myTeam("11",false,curPage,"10");
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                mMyTeamPersenter.myTeam("11",false,curPage,"10");
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
        mListBeans.clear();
        curPage=0;
        mMyTeamPersenter.myTeam("11",true,curPage,"10");
    }


    @Override
    public void getMyTeam(MyTeamBean.DataBean data) {
        mTvMyTeamNumber.setText(String.valueOf(data.getDirectNum()));
        for (int i = 0; i <data.getLevelList().size() ; i++) {
            MyTeamBean.DataBean.LevelListBean levelListBean = data.getLevelList().get(i);
            switch (levelListBean.getVipLevel()){
                case 0:
                    mTvMyTeamNormal.setText(levelListBean.getLevelName()+"："+levelListBean.getNum());
                    break;
                case 1:
                    mTvMyTeamVip.setText(levelListBean.getLevelName()+"："+levelListBean.getNum());
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
