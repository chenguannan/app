package com.sl_group.jinyuntong_oem.my_team.view;

import com.sl_group.jinyuntong_oem.bean.MyTeamBean;

/**
 * Created by 马天 on 2018/11/25.
 * description：我的团队
 */
public interface MyTeamView {
    /**
      * 我的团队请求成功回调
      * @param data 我的团队对象
      */
    void myTeamSuccess(MyTeamBean.DataBean data);
}
