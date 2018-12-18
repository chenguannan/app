package com.xinyilian.text.gather_bill.view;

import com.xinyilian.text.bean.GatherBillBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/20.
 * description：
 */
public interface GatherBillView {
    /**
      * 收款账单查询成功
      * @param resultList 收款账单 对象
      */
    void gatherBillSuccess(List<GatherBillBean.DataBean.ResultListBean> resultList);
}
