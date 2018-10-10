package com.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaomeng
 * @Description:
 * @date 2018/10/5 14:56
 */
@Controller
public class PageController {
  @RequestMapping(value="/")
    public String showIndex(){
      return  "index";
  }
  @RequestMapping( "/{page}")
    public String showPage(@PathVariable String page){
      return page;
  }
}
