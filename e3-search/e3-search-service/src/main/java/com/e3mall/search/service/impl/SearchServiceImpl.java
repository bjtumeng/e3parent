package com.e3mall.search.service.impl;

import com.e3mall.commom.pojo.SearchResult;
import com.e3mall.search.dao.SearchDao;
import com.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**商品搜索
 * @author zhaomeng
 * @Description:
 * @date 2018/10/17 23:52
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao dao;
    @Override
    /**
     * rows:每页显示的记录数,page:开始页
     */
    public SearchResult search(String keywords, int page, int rows) {
        //创建SolrQuery对象
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件,不需要判断是否为空,查询条件是什么就查询什么
        solrQuery.setQuery(keywords);
        if(page<=0){
            page=1;
        }
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //设置默认搜索域
        solrQuery.set("df","item_title");
        //开启高亮
        solrQuery.setHighlight(true);
        //设置高亮显示域
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");
        //调用dao执行查询
        SearchResult result =null;
        try {
            result = dao.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //总记录数
        long recordCount = result.getRecordCount();
        //总页数
        double totalPage=Math.ceil(1.0*recordCount/rows);
        result.setTotalPages((int)totalPage);
        return result;
    }
}
