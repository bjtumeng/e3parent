package com.e3mall.service.Impl;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.pojo.EasyUIDataGridResult;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.IDUtils;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.mapper.TbItemDescMapper;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.beans.SimpleBeanInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 商品管理Service
 * @Author:zhaomeng
 * @Description:
 * @Date: Created in 20:16 2018/9/11
 * @Modified By:
 */
@Service
public class ItemServiceImpl  implements ItemService{

    @Autowired
    private TbItemMapper mapper;
    @Autowired
    private TbItemDescMapper descMapper;
    @Autowired
    private JmsTemplate template;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient client;
    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;
    @Override
    public TbItem selectItemById(Long id) {
        //首先查询缓存
        try{

            String json = client.get(REDIS_ITEM_PRE + ":" + id + ":BASE");
            if(StringUtils.isNotBlank(json)){
                return JsonUtils.jsonToPojo(json,TbItem.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //缓存中没有,则查询数据库

//        根据主键进行查询
        TbItem tbItem = mapper.selectByPrimaryKey(id);
        //把结果添加到缓存
        try{
            client.set(REDIS_ITEM_PRE+":"+id+":BASE", JsonUtils.objectToJson(tbItem));
            //设置过期时间
            client.expire(REDIS_ITEM_PRE+":"+id+":BASE",ITEM_CACHE_EXPIRE);
        }catch(Exception e){
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> itemList = mapper.selectByExample(example);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(itemList);
        //取分页信息
        PageInfo<TbItem> info=new PageInfo<>(itemList);
        result.setTotal(info.getTotal());
        return result;
    }

    @Override
    public E3Result addTbItem(TbItem item, String desc) throws Exception {
        //生成商品id
        final long id = IDUtils.genItemId();
        item.setId(id);
        //补全staus,创建时间和修改时间
        item.setStatus((byte) 1);
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        Date dateParse = simpleDateFormat.parse(format);
        item.setCreated(dateParse);
        item.setUpdated(dateParse);
        //向商品表插入数据
        mapper.insert(item);
        //创建商品详情pojo
        TbItemDesc itemDesc=new TbItemDesc();
        //向商品详情插入数据
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(dateParse);
        itemDesc.setUpdated(dateParse);
        descMapper.insert(itemDesc);
        //发送商品添加消息
        template.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(id + "");
                return textMessage;
            }
        });
        return E3Result.ok();
    }

    @Override
    public E3Result deleteTbItem(Long[] ids) throws Exception {
        for (Long id:ids) {
            mapper.deleteByPrimaryKey(id);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result instockTbItem(Long[] ids) throws Exception {
        for (Long id:ids) {
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            TbItem item=new TbItem();
            item.setStatus((byte) 0);
            mapper.updateByExampleSelective(item,example);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result reshelfTbItem(Long[] ids) throws Exception {
        for (Long id:ids) {
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            TbItem item=new TbItem();
            item.setStatus((byte) 1);
            mapper.updateByExampleSelective(item,example);
        }
        return E3Result.ok();
    }

    @Override
    public TbItemDesc getTbItemDescById(Long itemId) {
        //首先查询缓存
        try{
            String json = client.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
            if(StringUtils.isNotBlank(json)){
                return JsonUtils.jsonToPojo(json,TbItemDesc.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //使用Blob才能查到大文本
        TbItemDesc itemDesc = descMapper.selectByPrimaryKey(itemId);
        //把结果添加到缓存
        try{
            client.set(REDIS_ITEM_PRE+":"+itemId+":DESC", JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            client.expire(REDIS_ITEM_PRE+":"+itemId+":DESC",ITEM_CACHE_EXPIRE);
        }catch(Exception e){
            e.printStackTrace();
        }
        return itemDesc;
    }
}
