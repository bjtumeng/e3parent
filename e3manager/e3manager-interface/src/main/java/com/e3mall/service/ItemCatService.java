package com.e3mall.service;

import com.e3mall.commom.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/6 10:48
 */
public interface ItemCatService {
    /**前台需要list数据
     * @param parentId
     * @return List<EasyUITreeNode>
     */
    public List<EasyUITreeNode> getItemCatList(long parentId);
}
