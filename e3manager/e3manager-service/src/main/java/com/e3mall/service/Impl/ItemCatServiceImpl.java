package com.e3mall.service.Impl;

import com.e3mall.commom.pojo.EasyUITreeNode;
import com.e3mall.mapper.TbItemCatMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemCat;
import com.e3mall.pojo.TbItemCatExample;
import com.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:商品分类管理
 * @date 2018/10/6 10:52
 */
@Service
public class ItemCatServiceImpl  implements ItemCatService {
    @Autowired
    private TbItemCatMapper mapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据parentId查询子节点列表
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> catList = mapper.selectByExample(example);
        //把列表转换成EasyUITreeNode列表
        List<EasyUITreeNode> nodeList=new ArrayList<>();
        for (TbItemCat list:catList) {
            EasyUITreeNode node=new EasyUITreeNode();
            node.setState(list.getIsParent()?"closed":"open");
            node.setText(list.getName());
            node.setId(list.getId());
            nodeList.add(node);
        }
        return nodeList;
    }
}
