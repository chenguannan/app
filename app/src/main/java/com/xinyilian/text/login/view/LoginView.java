package com.xinyilian.text.login.view;

import com.xinyilian.text.bean.LoginSMSBean;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface LoginView {

   void loginSuccess();

   void compelLogin();

   void loginSMSSuccess(LoginSMSBean.DataBean uuid);
}
