package com.xinyilian.text.system_prop.view;

/**
 * Created by 马天 on 2018/11/26.
 * description：
 */
public interface SystemPropView {
    /**
      *
      * @param kefu 客服链接
      */
    void getKeFuURL(String kefu);

    /**
      *
      * @param xieyi 用户协议
      */
    void getXieYiURL(String xieyi);

    /**
      *
      * @param xinshou 新手指引
      */
    void getXinShouURL(String xinshou);

    /**
      *
      * @param yaoqing 邀请码
      */
    void getYaoQingMaURL(String yaoqing);
}
