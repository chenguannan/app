package com.sl_group.jinyuntong_oem.safe_set.login_password.model;

/**
 * Created by 马天 on 2018/11/17.
 * description：
 */
public interface ChangeLoginPasswordModel {

    boolean checkForgetLoginPasswordParams(String tel,String password,String passwordAgain);

    void forgetLoginPassword(String cellPhone,String checkCode,String uuid,String password,ChangeLoginPasswordModel.IForgetLoginPasswordCallBack forgetLoginPasswordCallBack);

    interface IForgetLoginPasswordCallBack{
        void onSuccess(String data);
    }
}
