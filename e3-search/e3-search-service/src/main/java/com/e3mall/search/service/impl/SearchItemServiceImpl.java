package com.e3mall.search.service.impl;

import com.e3mall.commom.pojo.SearchItem;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**索引库维护Service
 * @author zhaomeng
 * @Description:
 * @date 2018/10/14 22:28
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper mapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public E3Result importAllItems()  {
        try {
            //查询商品列表
            List<SearchItem> searchItemList = mapper.getSearchItemList();
            // 遍历商品列表
            for (SearchItem item:searchItemList) {
                //创建文档对象
                SolrInputDocument document=new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id",item.getId());
                document.addField("item_title",item.getTitle());
                document.addField("item_sell_point",item.getSell_point());
                document.addField("item_image",item.getImage());
                document.addField("item_category_name",item.getCategory_name());
                //把文档对象写入索引库
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            //返回导入成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return  E3Result.build(500,"数据导入失败");
        }

    }
}
