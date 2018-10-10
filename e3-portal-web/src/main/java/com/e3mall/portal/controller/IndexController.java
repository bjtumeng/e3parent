package com.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页展示controller
 * @author zhaomeng
 * @Description:
 * @date 2018/10/9 20:12
 */
@Controller
public class IndexController {

     @RequestMapping("/index")
    public String showIndex(){
         return "index";
     }


}
