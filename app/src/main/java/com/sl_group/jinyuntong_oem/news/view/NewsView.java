package com.sl_group.jinyuntong_oem.news.view;

import com.sl_group.jinyuntong_oem.bean.NewsBean;

import java.util.List;

/**
 * Created by 马天 on 2018/11/25.
 * description：
 */
public interface NewsView {
    void getNews(List<NewsBean.DataBean.ResultListBean> resultList);
}
