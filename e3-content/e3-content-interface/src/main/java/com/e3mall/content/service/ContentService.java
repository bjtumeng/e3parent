package com.e3mall.content.service;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbContent;

import java.text.ParseException;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/10 22:37
 */
public interface ContentService {
    /**
     * 内容管理Service接口
     * @param content
     * @return
     */
    E3Result addContent(TbContent content) throws ParseException;
    List<TbContent> getContentListByCid(Long cid);
}
