package com.sl_group.jinyuntong_oem.my_team.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface MyTeamModel {
    void myTeam(String intoType,boolean isShowProgress,int curPage,String pageSize,MyTeamModel.IMyTeamCallBack myTeamCallBack);
    interface IMyTeamCallBack{
        void onSuccess(String data);
    }
}
