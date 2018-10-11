package com.e3mall.content.service.impl;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public E3Result addContent(TbContent content) throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        Date date1 = simpleDateFormat.parse(format);
        content.setCreated(date1);
        content.setUpdated(date1);
        mapper.insert(content);
        return  E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //selectByExampleWithBLOBs包含属性是text的大文本
        List<TbContent> contentList = mapper.selectByExampleWithBLOBs(example);
        return contentList;
    }
}
