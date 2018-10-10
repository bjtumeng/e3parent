package com.e3mall.content.service;

import com.e3mall.commom.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 22:28
 */
public interface ContentCategoryService {
    /**
     * 内容分类管理Service
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getCatList(Long parentId);
}
