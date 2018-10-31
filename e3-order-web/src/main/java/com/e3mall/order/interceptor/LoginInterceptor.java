package com.e3mall.order.interceptor;

import com.e3mall.cart.service.CartService;
import com.e3mall.commom.utils.CookieUtils;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.commom.utils.JsonUtils;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhaomeng
 * @Description:用户登录拦截器
 * @date 2018/10/31 20:41
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Autowired
    private TokenService service;
    @Autowired
    private CartService cartService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isBlank(token)){
            //如果token不存在,未登录状态,跳转到sso系统,用户登录成功后跳转到当前的url
            //只能是redirect跳转,不能是forward跳转,相同工程内可以forward跳转
            response.sendRedirect(SSO_URL+"page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }
        //如果token存在,需要调用sso服务,根据token取用户信息
        E3Result result = service.getUserInfoByToken(token);
        //如果取不到,用户信息已过期
        if (result.getStatus()!=200){
            response.sendRedirect(SSO_URL+"page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }
        //如果取到用户信息,把用户信息写到request
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user",user);
        //判断cookie中是否有购物车数据,如果有就合并到服务端
        String json = CookieUtils.getCookieValue(request, "cart", true);
        List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
        if (StringUtils.isNotBlank(json)){
            cartService.mergeCart(user.getId(),itemList);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
