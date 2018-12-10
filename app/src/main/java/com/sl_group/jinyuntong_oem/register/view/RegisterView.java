package com.sl_group.jinyuntong_oem.register.view;

import com.sl_group.jinyuntong_oem.bean.RegisterSMSBean;

/**
 * Created by 马天 on 2018/11/10.
 * description：
 */
public interface RegisterView {

    void registSuccess();

    void registSMSSuccess(RegisterSMSBean.DataBean data);
}
