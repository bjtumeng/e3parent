package com.e3mall.solrJ;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/14 22:07
 */
public class testSolrJ {
    @Test
    public void addDocument() throws Exception {
        //创建一个solrServer对象,创建一个连接,参数solr服务的url地址
        String BaseUrl="http://116.85.25.168:8080/solr";
        SolrServer solrServer=new HttpSolrServer(BaseUrl);
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document=new SolrInputDocument();
        //向文档对象中添加域,域中必须包含Id域,所有域的名称必须在schema.xml中定义
        document.addField("id","doc1");
        document.addField("item_title","测试商品");
        document.addField("item_price",1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
       solrServer.commit();
    }
    @Test
    public void queryIndex() throws Exception {
        //创建一个solrServer对象,创建一个连接,参数solr服务的url地址
        String BaseUrl="http://116.85.25.168:8080/solr";
        SolrServer solrServer=new HttpSolrServer(BaseUrl);
        //创建一个solrQuery对象
        SolrQuery solrQuery=new SolrQuery();
        //设置查询条件
//        solrQuery.setQuery("*:*");
        solrQuery.set("q","*:*");
        //执行查询,QueryResponse对象
        QueryResponse query = solrServer.query(solrQuery);
        //取文档列表,取查询结果的总记录数
        SolrDocumentList results = query.getResults();
        System.out.println("查询总记录数:"+results.getNumFound());
        //遍历文档列表,取域中的内容
        for (SolrDocument doc:results) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("item_title"));
            System.out.println(doc.get("item_sell_point"));
            System.out.println(doc.get("item_price"));
            System.out.println(doc.get("item_image"));
            System.out.println(doc.get("item_category_name"));
        }
    }
    @Test
    public void queryIndexfuza() throws Exception {
        //创建一个solrServer对象,创建一个连接,参数solr服务的url地址
        String BaseUrl="http://116.85.25.168:8080/solr";
       SolrServer solrServer=new HttpSolrServer(BaseUrl);
        solrServer.deleteByQuery("*:*");
        //提交修改
        solrServer.commit();

//        //创建一个solrQuery对象
//        SolrQuery solrQuery=new SolrQuery();
//        //设置查询条件
//        solrQuery.setQuery("手机");
//        solrQuery.setStart(0);
//        solrQuery.setRows(20);
//        solrQuery.set("df","item_title");
//        solrQuery.setHighlight(true);
//        solrQuery.addHighlightField("item_title");
//        solrQuery.setHighlightSimplePre("<em>");
//        solrQuery.setHighlightSimplePost("</em>");
//        //执行查询,QueryResponse对象
//        QueryResponse query = solrServer.query(solrQuery);
//        //取高亮结果
//        //取文档列表,取查询结果的总记录数
//        SolrDocumentList results = query.getResults();
//        System.out.println("查询总记录数:"+results.getNumFound());
//        //遍历文档列表,取域中的内容
//        for (SolrDocument doc:results) {
//            Map<String, Map<String, List<String>>> map = query.getHighlighting();
//            //根据关键字搜索出来,高亮显示标题可能为空
//            List<String> list = map.get(doc.get("id")).get("item_title");
//            String title="";
//            if (list!=null&&list.size()>0){
//                title = list.get(0);
//            }else{
//                title=(String) doc.get("item_title");
//            }
//            System.out.println(doc.get("id"));
//            System.out.println(title);
//            System.out.println(doc.get("item_sell_point"));
//            System.out.println(doc.get("item_price"));
//            System.out.println(doc.get("item_image"));
//            System.out.println(doc.get("item_category_name"));
//        }
 }
}
