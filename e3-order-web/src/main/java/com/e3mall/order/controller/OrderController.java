package com.e3mall.order.controller;

import com.e3mall.cart.service.CartService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/order/order-cart")
    public String showOrder(HttpServletRequest request,Model model){
        //根据用户id取收货地址(本项目使用静态数据)
        //取支付方式(本项目使用静态数据)
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> cartList = service.getCartList(user.getId());
        model.addAttribute("cartList",cartList);
        return "order-cart";
    }
}
