package com.e3mall.item.listener;

import com.e3mall.item.pojo.Item;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaomeng
 * @Description:监听商品添加消息,生成对应静态页面
 * @date 2018/10/25 8:33
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService service;
    @Autowired
    private FreeMarkerConfig config;
    @Value("${HTML_GEN_PATH}")
    private  String HTML_GEN_PATH;
    @Override
    public void onMessage(Message message) {
        try{
            //从消息中取商品id
            TextMessage textMessage=(TextMessage)message;
            String text = textMessage.getText();
            Long itemId=Long.parseLong(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息,包含商品基本信息和详细信息
            TbItemDesc itemDesc = service.getTbItemDescById(itemId);
            TbItem tbItem = service.selectItemById(itemId);
           Item item =new Item(tbItem);
            //创建一个数据集,把商品数据封装
            Map data =new HashMap();
            data.put("item",item);
            data.put("itemDesc",itemDesc);
            //加载模板对象
            Configuration configuration = config.getConfiguration();
            Template template =configuration.getTemplate("item.ftl");
            //创建一个输出流,指定输出的文件名及目录
            Writer out=new FileWriter(new File
                    (HTML_GEN_PATH+itemId+".html"));
            //生成静态页面
            template.process(data,out);
            //关闭流
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
