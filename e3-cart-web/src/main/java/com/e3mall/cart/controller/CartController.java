package com.e3mall.cart.controller;

import com.e3mall.commom.jedis.JedisClient;
import com.e3mall.commom.utils.CookieUtils;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:购物车处理Controller
 * @date 2018/10/28 17:17
 */
@Controller
public class CartController {
    @Autowired
    private ItemService service;
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    /**
     * 不在请求url中增加num
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request,HttpServletResponse response){
        //从cookie中取购物车列表
        List<TbItem> list = getCartListByCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag=false;
        for (TbItem item:list) {
           if (itemId.equals(item.getId())){
               //如果存在数量相加
               num=num+item.getNum();
               item.setNum(num);
               flag=true;
               break;
           }
        }
       if (flag==false){
           //如果不存在,查商品的详细信息,得到TbItem对象
           TbItem item = service.selectItemById(itemId);
           //设置商品数量
           item.setNum(num);
           //取一张图片
           String image = item.getImage();
           if (StringUtils.isNotBlank(image)){
               String[] split = image.split(",");
               item.setImage(split[0]);
           }
           //把商品添加到商品列表
           list.add(item);
           String json = JsonUtils.objectToJson(list);
           CookieUtils.setCookie(request,response,"cart",json,COOKIE_CART_EXPIRE,true);
           //写入cookie
       }
        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     * 从cookie中取购物车列表数据
     * @param request
     * @return
     */
    private List<TbItem> getCartListByCookie(HttpServletRequest request){
        //cookieValue是json格式的
        String cookieValue = CookieUtils.getCookieValue(request, "cart", true);
       if (StringUtils.isBlank(cookieValue)){
           //为了防止返回null
           return new ArrayList<>();
       }else{
           List<TbItem> itemList = JsonUtils.jsonToList(cookieValue, TbItem.class);
           return itemList;
       }
    }

    /**
     * 展示购物车列表
     * @param request
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCatList(HttpServletRequest request){
        //从cookie中取出购物车列表
        List<TbItem> cartList = getCartListByCookie(request);
        //把列表传递到页面
        request.setAttribute("cartList",cartList);
        //返回逻辑视图
        return "cart";
    }

    /**
     * 更改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response){
        List<TbItem> cookie = getCartListByCookie(request);
        for (TbItem item:cookie) {
            if (itemId.equals(item.getId())){
                item.setNum(num);
                break;
            }
        }
        String json = JsonUtils.objectToJson(cookie);
        CookieUtils.setCookie(request,response,"cart",json,COOKIE_CART_EXPIRE,true);
        return E3Result.ok();
    }

    /**
     * 删除购物车商品,
     * 重点1.删除商品2.redirect到cart
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartById(@PathVariable Long itemId,HttpServletRequest request,
                                 HttpServletResponse response){

        List<TbItem> cookie = getCartListByCookie(request);
        for (TbItem item:cookie) {
            if (item.getId().equals(itemId)){
                //从cookie中找到商品id
                cookie.remove(item);
                //假如不break,会在下一次循环抛异常
                break;
            }
        }
        //把更新后的信息写回cookie
        String json = JsonUtils.objectToJson(cookie);
        CookieUtils.setCookie(request,response,"cart",json,COOKIE_CART_EXPIRE,true);
        return "redirect:/cart/cart.html";
    }
}
