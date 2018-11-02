package com.e3mall.order.controller;

import com.e3mall.cart.service.CartService;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:订单管理Controller
 * @date 2018/10/30 23:12
 */
@Controller
public class OrderController {
    @Autowired
    private CartService service;
    @Autowired
    private OrderService orderService;
    @RequestMapping("/order/order-cart")
    public String showOrder(HttpServletRequest request,Model model){
        //根据用户id取收货地址(本项目使用静态数据)
        //取支付方式(本项目使用静态数据)
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> cartList = service.getCartList(user.getId());
        model.addAttribute("cartList",cartList);
        return "order-cart";
    }
    @RequestMapping(value="/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo info,HttpServletRequest request){
        //取用户信息,拦截器中有
        TbUser user = (TbUser)request.getAttribute("user");
        //把用户信息添加到orderInfo中
        info.setUserId(user.getId());
        info.setBuyerNick(user.getUsername());
        //调用服务生成订单
        E3Result order = orderService.createOrder(info);
        //如果订单生成成功,需要删除购物车
        if (order.getStatus()==200){
            service.clearCartItem(user.getId());
        }
        //订单号传递到页面
        String  orderId = order.getData().toString();
        request.setAttribute("orderId",orderId);
        request.setAttribute("payment",info.getPayment());
        //返回逻辑视图
        return "success";
    }
}
