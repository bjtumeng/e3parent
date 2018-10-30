package com.e3mall.cart.interceptor;

import com.e3mall.commom.utils.CookieUtils;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**登录处理拦截器
 * @author zhaomeng
 * @Description:拦截器也可以使用@Autowired
 * 判断是否登录,不会拦截请求
 * @date 2018/10/29 8:07
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService service;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //前处理,执行handler方法之前
        //1.从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        //假如取不到token,未登录状态,直接放行
        if (StringUtils.isBlank(token)){
            return true;
        }
        //假如取到token,需要调用sso的服务,根据token取用户信息
        E3Result result = service.getUserInfoByToken(token);
        //没有取到用户信息,登录过期,直接放行
        if (result.getStatus()!=200){
            return true;
        }
        //取到用户信息,登录状态
        TbUser data = (TbUser)result.getData();
        //把用户信息放在request中,只需要在controller中判断是否有用户信息
        request.setAttribute("data",data);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse hresponse,
                           Object handler, ModelAndView modelAndView) throws Exception {
     //执行handler方法之后,返回ModelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception e) throws Exception {
     //完成处理,返回ModelAndView之后

    }
}
