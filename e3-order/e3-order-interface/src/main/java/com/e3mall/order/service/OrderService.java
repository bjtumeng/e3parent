package com.e3mall.order.service;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.order.pojo.OrderInfo;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/31 22:54
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderInfo  创建pojo接收参数
     * @return  订单id
     */
    E3Result createOrder(OrderInfo orderInfo);
}
