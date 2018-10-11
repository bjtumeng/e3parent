package com.e3mall.content.service.impl;

import com.e3mall.commom.pojo.EasyUITreeNode;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 22:30
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper mapper;

    @Override
    public List<EasyUITreeNode> getCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = mapper.selectByExample(example);
        List<EasyUITreeNode> nodeList =new ArrayList<>();
        for (TbContentCategory category :categoryList) {
            EasyUITreeNode node =new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) throws ParseException {
       TbContentCategory contentCategory=new TbContentCategory();
       contentCategory.setName(name);
       //1.正常  2.删除
       contentCategory.setStatus(1);
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        Date date1 = simpleDateFormat.parse(format);
        contentCategory.setCreated(date1);
        //默认排序是1
       contentCategory.setSortOrder(1);
       //新增加的节点肯定不是父节点
       contentCategory.setIsParent(false);
       contentCategory.setParentId(parentId);
       contentCategory.setUpdated(date1);
        mapper.insert(contentCategory);
       //判断父节点的isparent属性,如果不是true,改为true
        TbContentCategory category = mapper.selectByPrimaryKey(parentId);
        if(category.getIsParent()==false){
            category.setIsParent(true);
            //更新数据库
            mapper.updateByPrimaryKey(category);
        }
        return E3Result.ok(contentCategory);
    }
}
