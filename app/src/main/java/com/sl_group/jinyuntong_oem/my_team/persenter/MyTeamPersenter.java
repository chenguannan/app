package com.sl_group.jinyuntong_oem.my_team.persenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.sl_group.jinyuntong_oem.CompelLogin;
import com.sl_group.jinyuntong_oem.bean.MyTeamBean;
import com.sl_group.jinyuntong_oem.my_team.model.MyTeamModel;
import com.sl_group.jinyuntong_oem.my_team.model.MyTeamModelImpl;
import com.sl_group.jinyuntong_oem.my_team.view.MyTeamView;
import com.sl_group.jinyuntong_oem.utils.LogUtils;
import com.sl_group.jinyuntong_oem.utils.ToastUtils;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public class MyTeamPersenter {
    private Activity mActivity;
    private MyTeamView mMyTeamView;
    private MyTeamModelImpl mMyTeamModel;

    public MyTeamPersenter(Activity activity, MyTeamView myTeamView) {
        mActivity = activity;
        mMyTeamView = myTeamView;
        mMyTeamModel = new MyTeamModelImpl(activity);
    }
    public void myTeam(String intoType, boolean isShowProgress, int curPage, String pageSize){
        mMyTeamModel.myTeam(intoType, isShowProgress, curPage, pageSize, new MyTeamModel.IMyTeamCallBack() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("我的团队："+data);
                MyTeamBean myTeamBean = new Gson().fromJson(data,MyTeamBean.class);
                if ("000000".equals(myTeamBean.getCode())){
                    mMyTeamView.getMyTeam(myTeamBean.getData());
                    return;
                }else if ("888888".equals(myTeamBean.getCode())) {
                    new CompelLogin(mActivity).popExitLogin();
                    return;
                }
                ToastUtils.showToast(myTeamBean.getMessage());
            }
        });
    }
}
