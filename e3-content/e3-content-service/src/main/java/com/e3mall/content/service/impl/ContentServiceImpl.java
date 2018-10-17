package com.e3mall.content.service.impl;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/10 22:39
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper mapper;
    @Autowired
    private JedisClient  client;
    @Value("${CONTENT_LIST}")
    private  String  CONTENT_LIST;
    @Override
    public E3Result addContent(TbContent content) throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        Date date1 = simpleDateFormat.parse(format);
        content.setCreated(date1);
        content.setUpdated(date1);
        mapper.insert(content);
        //插入完数据库需要做缓存同步,删除redis中的数据
        client.hdel(CONTENT_LIST,content.getCategoryId().toString());
        return  E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        //查询缓存,如果有缓存,直接返回,如果没有缓存,则查询数据库
        //使用try,catch捕获异常,不能因为redis出错,影响正常的业务逻辑
        try{
            String json = client.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNotBlank(json)){
                List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
                return contentList;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //selectByExampleWithBLOBs包含属性是text的大文本
        List<TbContent> contentList = mapper.selectByExampleWithBLOBs(example);
        //把结果添加到缓存
        try{
            //只能使用value是hashmap的类型,假如使用String,可能出现id重复
           client.hset(CONTENT_LIST,cid+"", JsonUtils.objectToJson(contentList));
        }catch(Exception e){
            e.printStackTrace();
        }
        return contentList;
    }
}
