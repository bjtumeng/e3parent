package com.e3mall.order.service.impl;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.mapper.TbOrderItemMapper;
import com.e3mall.mapper.TbOrderMapper;
import com.e3mall.mapper.TbOrderShippingMapper;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbOrder;
import com.e3mall.pojo.TbOrderItem;
import com.e3mall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:订单处理服务
 * @date 2018/10/31 22:57
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private JedisClient client;
    @Value("${ORDER_ID_GEN_KEY}")
    private  String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private  String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private  String ORDER_DETAIL_ID_GEN_KEY;
    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        if (!client.exists(ORDER_ID_GEN_KEY)){
          client.set(ORDER_ID_GEN_KEY,ORDER_ID_START);
        }
        //生成订单id,使用redis的incr
        String orderId = client.incr(ORDER_ID_START).toString();
        //补全order表的属性
        orderInfo.setOrderId(orderId);
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，
        // 5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //因为OrderInfo是TbOrder的子类,所以可以插入
        //插入订单表
        orderMapper.insert(orderInfo);
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem item:orderItems) {
            //补全订单详情信息
            String orderDetailId = client.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            item.setId(orderDetailId);
            item.setOrderId(orderId);
            orderItemMapper.insert(item);
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        return E3Result.ok(orderId);
    }
}
