package com.e3mall.search.service;

import com.e3mall.commom.pojo.SearchResult;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/17 23:48
 */
public interface SearchService {
    SearchResult search(String keywords,int page,int rows);
}
