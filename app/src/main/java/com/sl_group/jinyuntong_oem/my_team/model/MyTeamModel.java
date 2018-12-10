package com.sl_group.jinyuntong_oem.my_team.model;

/**
 * Created by 马天 on 2018/11/25.
 * description：我的团队
 */
public interface MyTeamModel {
    /**
     * @param intoType       类型
     * @param isShowProgress 是否显示进度条
     * @param curPage        当前页码
     * @param pageSize       每页条数
     * @param myTeamCallBack 请求成功回调
     */
    void myTeam(String intoType, boolean isShowProgress, int curPage, String pageSize, MyTeamModel.IMyTeamCallBack myTeamCallBack);

    /**
     * 我的团队请求成功回调
     */
    interface IMyTeamCallBack {
        void onSuccess(String data);
    }
}
