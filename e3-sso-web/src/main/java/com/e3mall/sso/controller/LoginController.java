package com.e3mall.sso.controller;

import com.e3mall.commom.utils.CookieUtils;
import com.e3mall.commom.utils.E3Result;
import com.e3mall.sso.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaomeng
 * @Description:用户登陆处理
 * @date 2018/10/27 21:43
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService service;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @RequestMapping("/page/login")
    public String login(){
        return "login";
    }

    /**
     * @param username
     * @param password 必须和前台表单数据相同
     * @return
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request,
                          HttpServletResponse response){
        E3Result result = service.userLogin(username, password);
        //判断是否登录成功
        if (result.getStatus()==200){
            //如果登陆成功,把token写入cookie
            String token = result.getData().toString();
            CookieUtils.setCookie(request,response,"token",token);
        }
        return result;

    }
}
