package com.e3mall.search.message;

import com.e3mall.commom.pojo.SearchItem;
import com.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**  监听商品添加消息.接收到消息后,将对应的消息同步到索引库
 * @author zhaomeng
 * @Description:
 * @date 2018/10/21 21:30
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemMapper mapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        //从消息中取商品id
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            //根据商品id查询数据库
            long itemId=Long.parseLong(text);
            //等待事务提交
            Thread.sleep(100);
            SearchItem item = mapper.getItemById(itemId);
            //创建一个文档对象
            SolrInputDocument document=new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point",item.getSell_point());
            document.addField("item_image",item.getImage());
            document.addField("item_price",item.getPrice());
            document.addField("item_category_name",item.getCategory_name());
            //把文档对象写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
