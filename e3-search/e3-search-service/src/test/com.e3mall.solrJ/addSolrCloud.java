package com.e3mall.solrJ;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/20 13:51
 */
public class addSolrCloud {

    @Test
    public void addSolrCloudTest() throws Exception {
        //zKHost:zookeeper的地址列表
        String zKHost="116.85.25.168:2182,116.85.25.168:2183,116.85.25.168:2184";
        //创建一个集群连接,应使用cloudSolrServer
        CloudSolrServer solrServer =new CloudSolrServer(zKHost);
        //设置一个defaultCollection属性
        solrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document=new SolrInputDocument();
        //向文档中添加域
        document.setField("id","solrCloud01");
        document.setField("item_title","测试商品01");
        document.setField("item_price",123);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
    @Test
    public void querySolrCloud() throws Exception {
        //zKHost:zookeeper的地址列表
        String zKHost="116.85.25.168:2182,116.85.25.168:2183,116.85.25.168:2184";
        //创建一个集群连接,应使用cloudSolrServer
        CloudSolrServer solrServer =new CloudSolrServer(zKHost);
        //设置一个defaultCollection属性
        solrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document=new SolrInputDocument();
        SolrQuery query=new SolrQuery();
        SolrQuery solrQuery = query.setQuery("*:*");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();
        long numFound = results.getNumFound();
        System.out.println(numFound);
        for (SolrDocument doc:results) {
            System.out.println(doc.get("item_title"));
        }
    }

}
