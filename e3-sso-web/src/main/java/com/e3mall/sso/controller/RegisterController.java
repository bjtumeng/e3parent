package com.e3mall.sso.controller;

import com.e3mall.commom.utils.E3Result;
import com.e3mall.pojo.TbUser;
import com.e3mall.sso.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhaomeng
 * @Description:注册功能Controller
 * @date 2018/10/26 8:25
 */
@Controller
public class RegisterController {
   @Autowired
   private RegisterService service;

     @RequestMapping("/page/register")
     public String showRegister(){
          return "register";
     }
     @RequestMapping("/user/check/{param}/{type}")
     @ResponseBody
     public E3Result checkData(@PathVariable String param,@PathVariable int type){
          E3Result result = service.checkData(param, type);
          return result;
     }
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser user) throws Exception {
        E3Result result = service.register(user);
        return result;
    }
}
