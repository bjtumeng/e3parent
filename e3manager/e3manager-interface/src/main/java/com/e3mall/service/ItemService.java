package com.e3mall.service;

import com.e3mall.commom.pojo.EasyUIDataGridResult;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;

import java.text.ParseException;

/**
 * @Author:zhaomeng
 * @Description:
 * @Date: Created in 20:12 2018/9/11
 * @Modified By:
 */
public interface ItemService {
    /**
     * 数据库中是bigInt类型,代码中是Long
     * @param id
     * @return
     */
    TbItem selectItemById(Long id);
    /**
     * 返回商品列表
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getItemList(int page,int rows);
    /**
     * 增加商品
     * @param item
     * @param desc
     * @return
     * @throws Exception
     */
    E3Result addTbItem(TbItem item,String desc) throws Exception;
    /**
     * 删除商品
     * @param ids
     * @return
     * @throws Exception
     */
    E3Result deleteTbItem(Long[] ids) throws Exception;

    /**
     * 下架商品
     * @param ids
     * @return
     * @throws Exception
     */
    E3Result instockTbItem(Long[] ids) throws Exception;
    /**
     * 上架商品
     * @param ids
     * @return
     * @throws Exception
     */
    E3Result reshelfTbItem(Long[] ids) throws Exception;

    /**
     * 根据商品id取商品的详细信息
     * @param itemId
     * @return
     */
    TbItemDesc getTbItemDescById(Long itemId);
}
