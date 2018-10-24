package com.e3mall.search.dao;

import com.e3mall.commom.pojo.SearchItem;
import com.e3mall.commom.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**商品搜索DAO
 * @author zhaomeng
 * @Description:
 * @date 2018/10/17 22:46
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;
    /**
     * 根据查询条件,查询索引,页数跟dao层没有关系,跟service层有关
     */
  public SearchResult query(SolrQuery query) throws SolrServerException {
      QueryResponse queryResponse = solrServer.query(query);
      SolrDocumentList results = queryResponse.getResults();
      long numFound = results.getNumFound();
      SearchResult searchResult=new SearchResult();
      searchResult.setRecordCount(numFound);
      List<SearchItem> itemList =new ArrayList<>();
      Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
      for (SolrDocument doc:results) {
          SearchItem item=new SearchItem();
          List<String> list = highlighting.get((String) doc.get("id")).get((String) doc.get("item_title"));
          String title="";
          if (list!=null &&list.size()>0){
              title=list.get(0);
          }else{
              title=(String) doc.get("item_title");
          }
          Long item_price = (Long) doc.get("item_price");
          item.setPrice(item_price);
          item.setTitle(title);
          item.setId((String)doc.get("id"));
          item.setCategory_name((String) doc.get("item_category_name"));
          item.setImage((String) doc.get("item_image"));


          item.setSell_point((String) doc.get("item_sell_point"));
          itemList.add(item);
      }
      searchResult.setItemList(itemList);
      return searchResult;
  }
}
