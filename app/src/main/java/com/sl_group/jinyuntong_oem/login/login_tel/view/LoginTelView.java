package com.sl_group.jinyuntong_oem.login.login_tel.view;

/**
 * Created by 马天 on 2018/10/16.
 * description：
 */
public interface LoginTelView {

   void skipMainActivity();
   void skipLoginActivity();
   void getSMSUUID(String uuid);
   /**
    * 登陆回调接口及回调方法
    */
   void startCount();
}
