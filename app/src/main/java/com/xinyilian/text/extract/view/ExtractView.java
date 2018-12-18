package com.xinyilian.text.extract.view;

import com.xinyilian.text.bean.ExtractBean;

/**
 * Created by 马天 on 2018/11/24.
 * description：
 */
public interface ExtractView {
    //提现成功回调
    void extractSuccess(ExtractBean.DataBean data);
}
