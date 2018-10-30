package com.e3mall.cart.service;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbItem;

import java.util.List;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/29 19:53
 */
public interface CartService {
    /**
     * 添加购物车
     * @return
     */
    E3Result addCart(Long userId,Long itemId,int num);

    /**
     * 用户是登录状态,合并购物车
     * @param userId
     * @param itemList
     * @return
     */
    E3Result mergeCart(Long userId, List<TbItem> itemList);

    /**
     * 用户是登录状态,获取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartList(Long userId);

    /**
     * 更新商品数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result updateCartNum(Long userId,Long itemId,int num);

    /**
     * 删除商品
     * @param userId
     * @param itemId
     * @return
     */
    E3Result deleteCartItem(Long userId,Long itemId);
}
