package com.e3mall.content.service.impl;

import com.e3mall.commom.pojo.EasyUITreeNode;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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




        return null;
    }
}
